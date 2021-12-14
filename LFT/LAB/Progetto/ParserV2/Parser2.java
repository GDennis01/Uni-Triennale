import java.io.*;

public class Parser2 {
    private Lexer2_3 lex;
    private BufferedReader pbr;
    private Token look;

    public Parser2(Lexer2_3 l, BufferedReader br) {
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
            // Insiemi guida della produzione <prog> => <statlist> EOF
            case Tag.ASSIGN:
                statlist();
                match(Tag.EOF);
                break;

            case Tag.PRINT:
                statlist();
                match(Tag.EOF);
                break;

            case Tag.READ:
                statlist();
                match(Tag.EOF);
                break;

            case Tag.WHILE:
                statlist();
                match(Tag.EOF);
                break;

            case Tag.IF:
                statlist();
                match(Tag.EOF);
                break;

            case Tag.LPG:
                statlist();
                match(Tag.EOF);
                break;

            default:
                error("Error in prog()");
                break;
        }

    }

    private void statlist() {
        switch (look.tag) {
            // Insiemi guida della produzione <statlist> => <stat> <statlistp>
            case Tag.ASSIGN:
                stat();
                statlistp();
                break;

            case Tag.PRINT:
                stat();
                statlistp();
                break;

            case Tag.READ:
                stat();
                statlistp();
                break;

            case Tag.WHILE:
                stat();
                statlistp();
                break;
            case Tag.IF:
                stat();
                statlistp();
                break;
            case Tag.RGT:
                stat();
                statlistp();
                break;

            default:
                error("Error in statlist()");
                break;
        }
    }

    private void statlistp() {
        switch (look.tag) {
            // Insieme guida della produzione <statlistp> => ; <stat> <statlistp>
            case Tag.SEMICOLON:
                match(Tag.SEMICOLON);
                stat();
                statlistp();
                break;
            // Insiemi guida della produzione <statlistp> => Epsilon
            case Tag.EOF:
                break;

            case Tag.RGT:
                break;

            default:
                error("Error in statlistp()");
                break;
        }
    }

    private void stat() {
        switch (look.tag) {
            // Insieme guida della produzione <stat> => assign <expr> to <idlist>
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist();
                break;
            // Insieme guida della produzione <stat> => print(<exprlist>)
            case Tag.PRINT:
                match(Tag.PRINT);
                match(Tag.LPT);
                exprlist();
                match(Tag.RPT);
                break;
            // Insieme guida della produzione <stat> => read(<idlist>)
            case Tag.READ:
                match(Tag.READ);
                match(Tag.LPT);
                idlist();
                match(Tag.RPT);
                break;
            // Insieme guida della produzione <stat> => while(<bexpr>)<stat>
            case Tag.WHILE:
                match(Tag.WHILE);
                match(Tag.LPT);
                bexpr();
                match(Tag.RPT);
                stat();
                break;

            /*
             * Le produzioni <stat> => if(<bexpr>)<stat>end
             * <stat> => if(<bexpr>)<stat> else <stat> end
             * non garantiscono una grammatica LL(1) dal momento che i loro insiemi guida
             * non sono disgiunti
             * Ho quindi modificato la produzione in:
             * <stat> => if(<bexpr>)<stat><statp>
             * <statp> => <statp> => end | else <stat> end
             * fattorizzando la parte comune ( "if(<bexpr>)<stat> <statp>")
             */
            // Insieme guida della produzione <stat> => if(<bexpr>)<stat><statp>
            case Tag.IF:
                match(Tag.IF);
                match(Tag.LPT);
                bexpr();
                match(Tag.RPT);
                stat();
                statp();
                break;
            // Insieme guida della produzione <stat> => {<statlist>}
            case Tag.LPG:
                match(Tag.LPG);
                statlist();
                match(Tag.RGT);
                break;

            default:
                error("Error in stat()");
                break;
        }
    }

    private void statp() {
        switch (look.tag) {
            // Insieme guida della produzione <statp> => end
            case Tag.END:
                match(Tag.END);
                break;
            // Insieme guida della produzione <statp> => else <stat> end
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

    private void idlist() {
        switch (look.tag) {
            // Insieme guida della produzione <idlist> => ID <idlistp>
            case Tag.ID:
                match(Tag.ID);
                idlistp();
                break;

            default:
                error("Error in idlist()");
                break;
        }

    }

    private void idlistp() {
        switch (look.tag) {
            // Insieme guida della produzione <idlistp> => , ID <idlistp>
            case Tag.COMMA:
                match(Tag.COMMA);
                match(Tag.ID);
                idlistp();
                break;

            // Insiemi guida della produzione <idlistp> => Epsilon
            case Tag.RPT:
                break;
            case Tag.SEMICOLON:
                break;
            case Tag.END:
                break;
            case Tag.ELSE:
                break;
            case Tag.EOF:
                break;
            case Tag.RGT:
                break;

            default:
                error("Error in idlistp()");
                break;
        }

    }

    private void bexpr() {
        switch (look.tag) {
            // Insieme guida della produzione <bexpr> => RELOP <expr> <expr>

            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;

            default:
                error("Error in bexpr()");
                break;
        }

    }

    private void expr() {
        switch (look.tag) {
            // Insieme guida della produzione <expr> => +(<exprlist>)
            case Tag.SUM:
                match(Tag.SUM);
                match(Tag.LPT);
                exprlist();
                match(Tag.RPT);
                break;
            // Insieme guida della produzione <expr> => -<expr><expr>
            case Tag.SUB:
                match(Tag.SUB);
                expr();
                expr();
                break;
            // Insieme guida della produzione <expr> => *(<exprlist>)
            case Tag.MUL:
                match(Tag.MUL);
                match(Tag.LPT);
                exprlist();
                match(Tag.RPT);
                break;
            // Insieme guida della produzione <expr> => /<expr><expr>
            case Tag.DIV:
                match(Tag.DIV);
                expr();
                expr();
                break;
            // Insieme guida della produzione <expr> => NUM
            case Tag.NUM:
                match(Tag.NUM);
                break;
            // Insieme guida della produzione <expr> => ID
            case Tag.ID:
                match(Tag.ID);
                break;

            default:
                error("Error in expr()");
                break;
        }

    }

    private void exprlist() {
        switch (look.tag) {
            // Insiemi guida della produzione <exprlist> => <expr><exprlistp>
            case Tag.SUM:
                expr();
                exprlistp();
                break;
            case Tag.SUB:
                expr();
                exprlistp();
                break;
            case Tag.MUL:
                expr();
                exprlistp();
                break;
            case Tag.DIV:
                expr();
                exprlistp();
                break;
            case Tag.NUM:
                expr();
                exprlistp();
                break;
            case Tag.ID:
                expr();
                exprlistp();
                break;
            default:
                error("Error in exprlist()");
                break;
        }

    }

    private void exprlistp() {
        switch (look.tag) {
            //Insieme guida della produzione <exprlistp> => , <expr><exprlistp>
            case Tag.COMMA:
                match(Tag.COMMA);
                expr();
                exprlistp();
                break;

            //Insieme guida della produzione <exprlistp> => Epsilon
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
            Parser2 parser = new Parser2(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}