import java.io.*;

public class Parser {
    private Lexer2_3 lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer2_3 l, BufferedReader br) {
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

    public void start() {
        switch (look.tag) {
        case Tag.LPT:
            //match(Tag.LPT);//it doesnt have to match
            expr();
            match(Tag.EOF);
            break;

        case Tag.NUM:
            //match(Tag.NUM);
            expr();
            match(Tag.EOF);
            break;

        default:
            error("Error in start()");
            break;
        }

    }

    private void expr() {

        switch (look.tag) {
        case Tag.LPT:
            //match(Tag.LPT);
            term();
            exprp();
            break;

        case Tag.NUM:
            //match(Tag.NUM);
            term();
            exprp();
            break;

        default:
            error("Error in expr()");
            break;
        }

        // ... completare ...
    }
    /*


    Insieme GUida(EXPR->"+<term><exprp>")
    Insieme Guida(Expr->"-<term><exprp>")
    Insieme Guida(Expr->"epsilon")
    */
    private void exprp() {
        switch (look.tag) {
        case Tag.SUM://insieme guida prima prod
            match( Tag.SUM);
            term();
            exprp();
            break;

        case Tag.SUB://insieme guida seconda prod
            match( Tag.SUB);
            term();
            exprp();
            break;

        case Tag.RPT://insieme guida epsilon(terza prod)
            break;

        case Tag.EOF://insieme guida epsilon(terza prod)
            break;

        default:
            error("Error in Exprp()");
            break;

        }
    }

    private void term() {
        switch (look.tag) {
        case Tag.LPT:
            //match('(');
            fact();
            termp();
            break;

        case Tag.NUM:
            //match(Tag.NUM);
            fact();
            termp();
            break;

        default:
            error("Error in term()");
            break;
        }
    }

    private void termp() {
        switch (look.tag) {
        case Tag.MUL://Insieme guida prima prod
            match(Tag.MUL);
            fact();
            termp();
            break;

        case Tag.DIV://Insieme guida seconda prod
            match(Tag.DIV);
            fact();
            termp();
            break;

        case Tag.RPT://Insieme guida epsilon(terza prod)
            break;
        
        case Tag.SUM://Insieme guida epsilon(terza prod)
            break;
            
        case Tag.SUB://Insieme guida epsilon(terza prod)
            break;

        case Tag.EOF://Insieme guida epsilon(terza prod)
            break;

        default:
            error("Error in termp()");
            break;
        //No default action due to epsilon production

        }
    }

    private void fact() {
        switch (look.tag) {

        case Tag.LPT://Insieme guida prima prod
            match(Tag.LPT);
            expr();
            match(Tag.RPT);
            break;

        case Tag.NUM://Insieme guida seconda prod
            match(Tag.NUM);
            break;

        default:
            error("Error in fact()");
            break;
        }
    }

    public static void main(String[] args) {
        Lexer2_3 lex = new Lexer2_3();
        String path = "input.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}