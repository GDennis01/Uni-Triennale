import java.io.*;

public class Translator {
    private Lexer2_3 lex;
    private BufferedReader pbr;
    private Token look;

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;

    public Translator(Lexer2_3 l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error");
    }

    public void prog() {
        //TODO: MULTIPLE ASSIGNMENT
        switch (look.tag) {
            // Guida(<prog> => <statlist> EOF) = [assign print read while if { ]
            case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.IF, Tag.LPG:
                int lnext_prog = code.newLabel();// Attr. Sintetizzato di Prog
                statlist(lnext_prog);
                code.emitLabel(lnext_prog);
                match(Tag.EOF);
                try {
                    code.toJasmin();
                } catch (java.io.IOException e) {
                    System.out.println("IO error\n");
                }

                break;

            default:
                error("Error in prog()");
                break;
        }
    }

    public void statlist(int label) {// Label è un attr. ereditato. Lo piglia da prog
        switch (look.tag) {
            // Guida(<statlist> => <stat><statlistp>) = [assign print read while if { ]
            case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.IF, Tag.LPG:
                stat();
                statlistp();
                break;
        }
    }

    public void statlistp() {
        switch (look.tag) {
            // Guida(<statlistp> => ; <stat><statlistp>) = [;]
            case Tag.SEMICOLON:
                match(Tag.SEMICOLON);
                stat();
                statlistp();
                break;

            // Guida(<statlist> => epsilon) = [EOF }]
            case Tag.EOF, Tag.RPG:
                break;

            default:
                error("Error in statlistp()");
                break;
        }
    }

    public void stat( /* completare */ ) {
        switch (look.tag) {
            // Guida(<stat> => assign <expr> to <idlist>) = [assign]
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist(1);// assignment operation
                break;

            // Guida(<stat> => print(<exprlist>)) = [print]
            case Tag.PRINT:
                match(Tag.PRINT);
                match(Tag.LPT);
                exprlist(2);// print operation
                match(Tag.RPT);
                break;

            // Guida(<stat> => read(<idlist>) ) = [read]
            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist(0);// 0 is for reading operation
                match(')');
                // ... completare ...
                break;

            // Guida(<stat> => while(<brexpr>)<stat>) = [while]
            case Tag.WHILE: {
                int labelTrue = code.newLabel();
                int labelFalse = code.newLabel();
                int labelLoop = code.newLabel();

                code.emitLabel(labelLoop);// Adding Starting loop label. (ie: L1: if_icmpe true)

                match(Tag.WHILE);
                match(Tag.LPT);

                bexpr(labelTrue, labelFalse);// emit conditional operation (ie: if_icmpe true)

                match(Tag.RPT);
                //code.emit(OpCode.GOto, labelFalse); // emit "goto false" right after conditional operation(see above)
                code.emitLabel(labelTrue);// after the goto instruction, I emit the true label used by the conditional
                                          // operation
                stat();
                code.emit(OpCode.GOto, labelLoop);// after the code(stat()), I emit the label used by the loop
                code.emitLabel(labelFalse);// emit false label (ie: false: //code)
                break;
            }

            // Guida(<stat> => if(<bexpr>) <stat><statp>) = [if]
            case Tag.IF: {
                int labelTrue = code.newLabel();
                int labelFalse = code.newLabel();

                match(Tag.IF);
                match(Tag.LPT);

                bexpr(labelTrue, labelFalse);// emit conditional operation (ie: if_icmpe true)

                match(Tag.RPT);
               // code.emit(OpCode.GOto, labelFalse);// I emit goto false right after conditional operation
                code.emitLabel(labelTrue);// I emit true label
                stat();
                statp(labelTrue, labelFalse);
                break;
            }

            // Guida(<stat> => {<statlist>}) = [{]
            case Tag.LPG:
                match(Tag.LPG);
                statlist(0);
                match(Tag.RPG);
                break;

            default:
                error("Error in stat()");
                break;
        }
    }

    private void statp(int labelTrue, int labelFalse) {
        switch (look.tag) {
            // Guida(<statp> => end) = [end]
            case Tag.END:
                match(Tag.END);
                code.emitLabel(labelFalse);
                break;

            // Guida(<statp> => else <stat> end) = [end]
            case Tag.ELSE:
                int skipElseLabel = code.newLabel();
                match(Tag.ELSE);
                code.emit(OpCode.GOto, skipElseLabel);
                code.emitLabel(labelFalse);
                stat();
                match(Tag.END);
                code.emitLabel(skipElseLabel);

                break;

            default:
                error("Error in statp()");
                break;
        }
    }

    // read_assign is a boolean value that can either be 0(reading operation) or
    // 1(assignament operation)
    private void idlist(int read_assign) {
        switch (look.tag) {
            // Guida(<idlist> => ID <idlistp>) = [if]
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);
                }
                if (read_assign == 0) {// reading operation
                    code.emit(OpCode.invokestatic, 0);// invokestatic 0 is the Input/Reading operation
                } // assignment operation
                code.emit(OpCode.istore, id_addr);
                match(Tag.ID);
                idlistp(read_assign);
                break;

            default:
                error("Error in idlist()");
                break;

        }
    }

    private void idlistp(int read_assign) {
        switch (look.tag) {
            // Guida(<idlistp> => , ID <idlistp>) = [,]
            case Tag.COMMA:
                match(Tag.COMMA);
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);
                }
                match(Tag.ID);
                if (read_assign == 0) {// reading operation
                    code.emit(OpCode.invokestatic, 0);// invokestatic 0 is the Input/Reading operation
                } // assignment operation
                code.emit(OpCode.istore, id_addr);
                idlistp(read_assign);
                break;

            // Guida(<idlistp> => epsilon) = [) , end else EOF }]
            case Tag.RPT, Tag.SEMICOLON, Tag.END, Tag.ELSE, Tag.EOF, Tag.RPG:
                break;

            default:
                error("Error in idlistp()");
                break;
        }
    }
    //logic operator can be either 0 1 or 2. 0=logical OR  1=logical AND  2=no logical operator
    private void bexpr(int labelTrue, int labelFalse) {
        switch (look.tag) {
            // Guida(<bexpr> => RELOP <expr> <expr>) = [RELOP]
            case Tag.RELOP:
                String tmp = ((Word) look).lexeme;// relational operator(es: != < > <= >= etc)
                match(Tag.RELOP);
                expr();
                expr(); 

                if (tmp.equals(Word.eq.lexeme)) {// if relop is equals to "=" then I emit ifcmpeq
                    code.emit(OpCode.if_icmpeq, labelTrue);
                    
                }
                if (tmp.equals(Word.ge.lexeme)) {
                    code.emit(OpCode.if_icmpge, labelTrue);
                }
                if (tmp.equals(Word.gt.lexeme)) {
                    code.emit(OpCode.if_icmpgt, labelTrue);
                }
                if (tmp.equals(Word.le.lexeme)) {
                    code.emit(OpCode.if_icmple, labelTrue);
                }
                 
                if (tmp.equals(Word.lt.lexeme)) {
                    code.emit(OpCode.if_icmplt, labelTrue);
                }
                if (tmp.equals(Word.ne.lexeme)) {
                    code.emit(OpCode.if_icmpne, labelTrue);
                }
                code.emit(OpCode.GOto, labelFalse);
            break;
                /*
                 * if(valore ereditato diverso da 0)
                 * code.emit(OpCode.ne,lnextprog);
                 */
            //Guida(<bexpr> => <bool> ) = [AND OR NOT]
            case Tag.AND:
            case Tag.OR:
            case '!':
                bool(labelTrue,labelFalse);
                break;

            default:
                error("Error in bexpr()");
                break;
        }

    }

    private void bool(int labelTrue,int labelFalse){
        int aux_label=code.newLabel();
        switch(look.tag){
            //Guida(<bool> => AND <boolp> <boolp>) = [AND]
            case Tag.AND:
                match(Tag.AND);
                boolp(aux_label,labelFalse);
                code.emitLabel(aux_label);
                boolp(labelTrue,labelFalse);
                break;
            //Guida(<bool> => OR <boolp> <boolp>) = [OR]
            case Tag.OR:
                match(Tag.OR);
                boolp(labelTrue,aux_label);
                code.emitLabel(aux_label);
                boolp(labelTrue,labelFalse);
                break;
            //Guida(<bool> => NOT <boolp>) = [NOT]
            case '!':
                match('!');
                boolp(labelFalse,labelTrue);//to negate a conditional statement, I just need to swap the label
                break;
        }
    }

    private void boolp(int labelTrue,int labelFalse){
       
        switch (look.tag) {
            // Guida(<boolp> => RELOP <expr> <expr>) = [RELOP]
            case Tag.RELOP:
                String tmp = ((Word) look).lexeme;// relation operator(es: != < > <= >= etc)
                match(Tag.RELOP);
                expr();
                expr(); 

                if (tmp.equals(Word.eq.lexeme)) {// if relop is equals to "=" then I emit ifcmpeq
                    code.emit(OpCode.if_icmpeq, labelTrue);
                }
                if (tmp.equals(Word.ge.lexeme)) {
                    code.emit(OpCode.if_icmpge, labelTrue);
                }
                if (tmp.equals(Word.gt.lexeme)) {
                    code.emit(OpCode.if_icmpgt, labelTrue);
                }
                if (tmp.equals(Word.le.lexeme)) {
                    code.emit(OpCode.if_icmple, labelTrue);
                }//|| < x 10 && > x 20 ! > x 30
                 //||=+  &&=*  < = -  > = +   +-X10*+X20/+X30
                if (tmp.equals(Word.lt.lexeme)) {
                    code.emit(OpCode.if_icmplt, labelTrue);
                }
                if (tmp.equals(Word.ne.lexeme)) {
                    code.emit(OpCode.if_icmpne, labelTrue);
                }
                code.emit(OpCode.GOto,labelFalse);
            break;
                /*
                 * if(valore ereditato diverso da 0)
                 * code.emit(OpCode.ne,lnextprog);
                 */
            //Guida(<boolp> => <bool> ) = [AND OR NOT]
            case Tag.AND:
            case Tag.OR:
            case '!':
                bool(labelTrue,labelFalse);
                break;

            default:
                error("Error in boolp()");
                break;
        }

       
    }

    private void expr( /* completare */ ) {
        switch (look.tag) {
            // Guida(<expr> => +(<exprlist>) ) = [+]
            case Tag.SUM:
                match(Tag.SUM);
                match(Tag.LPT);
                exprlist(0);// sum operation
                match(Tag.RPT);
                break;

            // Guida(<expr> => *(<exprlist>)) = [+]
            case Tag.MUL:
                match(Tag.MUL);
                match(Tag.LPT);
                exprlist(1);// mul operation
                match(Tag.RPT);
                break;

            // Guida(<expr> => - <expr> <expr>) = [-]
            case Tag.SUB:
                match('-');
                expr();// Eventually it either emits a "ldc NUM" or a "iload ID"
                expr();
                code.emit(OpCode.isub);// isub
                break;
            // Guida(<expr> => / <expr> <expr>) = [-]
            case Tag.DIV:
                match(Tag.DIV);
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            // Guida(<expr> => NUM) = [NUM]
            case Tag.NUM:
                code.emit(OpCode.ldc, ((NumberTok) look).getLexeme());// adding to the instruction list "ldc NUM"
                match(Tag.NUM);
                break;

            // Guida(<expr> => ID) = [ID]
            case Tag.ID:
                /* Searching if the ID is already associated with a memory address */
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {// if it doesnt have any associated memory address(and thus id_addr is -1)
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);// I insert the new ID with associated address memory
                                                             // equals to
                                                             // count +1("count" is a variable used to count addresses)
                }
                code.emit(OpCode.iload, id_addr);// adding to the instruction list "iload ID"
                match(Tag.ID);// matching ID
                break;

            default:
                error("Error in expr()");
                break;
        }
    }

    // read_assign is a boolean value that can either be 0(sum operation) ,1(mul
    // operation) or 2(print operation)
    private void exprlist(int sum_mul) {
        switch (look.tag) {
            // Guida(<exprlist> => <expr><exprlistp>) = [+ - * / NUM ID]
            case Tag.SUM, Tag.SUB, Tag.MUL, Tag.DIV, Tag.NUM, Tag.ID:
                expr();
                if (sum_mul == 2)
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(sum_mul);
                // code.emit(OpCode.invokestatic, 1);// invokestatic is for output operation

                break;

            default:
                error("Error in exprlist()");
                break;
        }

    }

    private void exprlistp(int sum_mul) {
        switch (look.tag) {
            // Guida(<exprlistp> => , <expr><exprlistp>) = [,]
            case Tag.COMMA:
                match(Tag.COMMA);
                expr();
                switch (sum_mul) {
                    case 0:
                        code.emit(OpCode.iadd);
                        break;
                    case 1:
                        code.emit(OpCode.imul);
                        break;
                    case 2:
                        code.emit(OpCode.invokestatic, 1);
                        break;
                }

                exprlistp(sum_mul);
                break;

            // Guida(<exprlistp> => epsilon) = [)]
            case Tag.RPT:
                break;
            default:
                error("Error in exprlistp()");
                break;
        }

    }

    public static void main(String[] args) {
        Lexer2_3 lex = new Lexer2_3();
        String path = "input.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
