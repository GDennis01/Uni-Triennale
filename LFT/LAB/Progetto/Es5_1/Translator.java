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
        switch (look.tag) {
            //Guida(<prog> => <statlist> EOF) = [assign print read while if { ]
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
                ;
                break;

            default:
                error("Error in prog()");
                break;
        }
    }

    public void statlist(int label) {// Label è un attr. ereditato. Lo piglia da prog
        switch (look.tag) {
            //Guida(<statlist> => <stat><statlistp>) = [assign print read while if { ]
            case Tag.ASSIGN, Tag.PRINT, Tag.READ, Tag.WHILE, Tag.IF, Tag.LPG:
                stat(/* */);
                statlistp(/**/);
                break;
        }
    }

    public void statlistp() {
        switch (look.tag) {
            //Guida(<statlistp> => ; <stat><statlistp>) = [;]
            case Tag.SEMICOLON:
                match(Tag.SEMICOLON);
                stat();
                statlistp();
                break;

            //Guida(<statlist> => epsilon) = [EOF }]
            case Tag.EOF, Tag.RPG:
                break;

            default:
                error("Error in statlistp()");
                break;
        }
    }

    public void stat( /* completare */ ) {
        switch (look.tag) {
            //Guida(<stat> => assign <expr> to <idlist>) = [assign]
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist(1);
                break;

            //Guida(<stat> => print(<exprlist>)) = [print]
            case Tag.PRINT:
                match(Tag.PRINT);
                match(Tag.LPT);
                exprlist();
                match(Tag.RPT);
                break;

            //Guida(<stat> => read(<idlist>) ) = [read]
            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist(0);
                match(')');
                // ... completare ...
                break;

            //Guida(<stat> => while(<brexpr>)<stat>) = [while]
            case Tag.WHILE:
                int trueLabel=code.newLabel(),falseLabel=code.newLabel();
                match(Tag.WHILE);
                match(Tag.LPT);
                bexpr();
                match(Tag.RPT);
                stat();
                break;

            //Guida(<stat> => if(<bexpr>) <stat><statp>) = [if]
            case Tag.IF:
                match(Tag.IF);
                match(Tag.LPT);
                bexpr();
                match(Tag.RPT);
                stat();
                statp();
                break;

            //Guida(<stat> => {<statlist>}) = [{]
            case Tag.LPG:
                match(Tag.LPG);
                statlist();
                match(Tag.RPG);
                break;

            default:
                error("Error in stat()");
                break;
        }
    }

    private void statp() {
        switch (look.tag) {
            //Guida(<statp> => end) = [end]
            case Tag.END:
                match(Tag.END);
                break;

            //Guida(<statp> => else <stat> end) = [end]
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
                match(Tag.END);
                break;

            default:
                error("Error in statp()");
                break;
        }
    }

    //read_assign is a boolean value that can either be 0(reading operation) or 1(assignament operation)
    private void idlist(int read_assign) {
        switch (look.tag) {
            //Guida(<idlist> => ID <idlistp>) = [if]
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);
                }
                if(read_assign == 1){//assignment operation
                    code.emit(OpCode.invokestatic,0);//invokestatic 0 is the Input/Reading operation
                }//reading operation
                    code.emit(OpCode.istore,id_addr);
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
            //Guida(<idlistp> => , ID <idlistp>) = [,]
            case Tag.COMMA:
                match(Tag.COMMA);
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);
                }
                code.emit(OpCode.iload, id_addr);// adding to the instruction list "iload ID"
                match(Tag.ID);
                if(read_assign == 1){//assignment operation
                    code.emit(OpCode.invokestatic,0);//invokestatic 0 is the Input/Reading operation
                }//reading operation
                    code.emit(OpCode.istore,id_addr);
                idlistp(read_assign);
                break;

            //Guida(<idlistp> => epsilon) = [) , end else EOF }]
            case Tag.RPT, Tag.SEMICOLON, Tag.END, Tag.ELSE, Tag.EOF, Tag.RPG:
                break;

            default:
                error("Error in idlistp()");
                break;
        }
    }

    private void bexpr() {
        switch (look.tag) {
            //Guida(<bexpr> => RELOP <expr> <expr>) = [RELOP]
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                {
                    String tmp = ((Word)look).lexeme;//relation operator(es: != < > <= >= etc)
                    if(tmp.equals(Word.eq.lexeme)){//if relop is equals to  "=" then I emit ifcmpeq
                        code.emit(OpCode.if_icmpeq/*,lnextprog*/);
                    }
                    if(tmp.equals(Word.ge.lexeme)){
                        code.emit(OpCode.if_icmpge/*,lnextprog*/);
                    }
                    if(tmp.equals(Word.gt.lexeme)){
                        code.emit(OpCode.if_icmpgt/*,lnextprog*/);
                    }
                    if(tmp.equals(Word.le.lexeme)){
                        code.emit(OpCode.if_icmple/*,lnextprog*/);
                    }
                    if(tmp.equals(Word.lt.lexeme)){
                        code.emit(OpCode.if_icmplt/*,lnextprog*/);
                    }
                    if(tmp.equals(Word.ne.lexeme)){
                        code.emit(OpCode.if_icmpne/*,lnextprog*/);
                    }
                    /*
                    if(valore ereditato diverso da 0)
                        code.emit(OpCode.ne,lnextprog);
                    */
                  
                }
                
                
                break;

            default:
                error("Error in bexpr()");
                break;
        }

    }

    private void expr( /* completare */ ) {
        switch (look.tag) {
            //Guida(<expr> => +(<exprlist>) ) = [+]
            case Tag.SUM:
                match(Tag.SUM);
                match(Tag.LPT);
                exprlist();
                match(Tag.RPT);
                code.emit(OpCode.iadd);
                break;

            //Guida(<expr> => *(<exprlist>)) = [+]
            case Tag.MUL:
                match(Tag.MUL);
                match(Tag.LPT);
                exprlist();
                match(Tag.RPT);
                code.emit(OpCode.imul);
                break;

            //Guida(<expr> => - <expr> <expr>) = [-]
            case Tag.SUB:
                match('-');
                expr();//Eventually it either emits a "ldc NUM" or a "iload ID"
                expr();
                code.emit(OpCode.isub);// isub
                break;
            //Guida(<expr> => / <expr> <expr>) = [-]
            case Tag.DIV:
                match(Tag.DIV);
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            //Guida(<expr> => NUM) = [NUM]
            case Tag.NUM:
                code.emit(OpCode.ldc, ((NumberTok) look).getLexeme());//adding to the instruction list "ldc NUM"
                match(Tag.NUM);
                break;

            //Guida(<expr> => ID) = [ID]
            case Tag.ID:
            /*Searching if the ID is already associated with a memory address */
                int id_addr = st.lookupAddress(((Word) look).lexeme);
                if (id_addr == -1) {//if it doesnt have any associated memory address(and thus id_addr is -1)
                    id_addr = count;
                    st.insert(((Word) look).lexeme, count++);// I insert the new ID with associated address memory equals to 
                                                             // count +1("count" is a variable used to count addresses)
                }
                code.emit(OpCode.iload, id_addr);//adding to the instruction list "iload ID"
                match(Tag.ID);//matching ID
                break;

            default:
                error("Error in expr()");
                break;
        }
    }

    private void exprlist() {
        switch (look.tag) {
            //Guida(<exprlist> => <expr><exprlistp>) = [+ - * / NUM ID]
            case Tag.SUM,Tag.SUB,Tag.MUL,Tag.DIV,Tag.NUM,Tag.ID:
                expr();
                code.emit(OpCode.invokestatic,1);//invokestatic is for output operation
                exprlistp();
                break;
            
            default:
                error("Error in exprlist()");
                break;
        }

    }

    private void exprlistp() {
        switch (look.tag) {
            //Guida(<exprlistp> => , <expr><exprlistp>) = [,]
            case Tag.COMMA:
                match(Tag.COMMA);
                expr();
                code.emit(OpCode.invokestatic,1);//invokestatic is for output operation
                exprlistp();
                break;

            //Guida(<exprlistp> => epsilon) = [)]
            case Tag.RPT:
                break;
            default:
                error("Error in exprlistp()");
                break;
        }

    }
}


