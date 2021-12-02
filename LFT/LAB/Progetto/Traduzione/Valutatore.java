import java.io.*;

public class Valutatore {
    private Lexer2_3 lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer2_3 l, BufferedReader br) {
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
        int expr_val;
        switch (look.tag) {
            case Tag.LPT: {
                expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
            }
                break;

            case Tag.NUM: {
                expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
            }
                break;

            default:
                error("Error in start()");
                break;
        }
    }

    private int expr() {
        int term_val=0, exprp_val=0;

        switch (look.tag) {
            case Tag.LPT:
                //term();
                term_val = term();
                //exprp();
                exprp_val = exprp(term_val);
                break;

            case Tag.NUM:
                //term();
                term_val = term();
                //exprp();
                exprp_val = exprp(term_val);
                break;

            default:
                error("Error in expr()");
                break;
        }
        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val=0;
        switch (look.tag) {
            case '+':
                match('+');//+
                term_val = term();//term() sarebbe <term> e l'assegnazione sarebbe il "+term.val" nell'azione semantica dopo <term>
                exprp_val = exprp(exprp_i + term_val);
                /*exprp_i + term_val equivale all'azione semantica exprp_1.i = exprp.i + term.val.
                Ciò perchè l'argomento di exprp è l'attributo ereditato di exprp stesso. 
                Passandogli come argomento exprp.i+term.val, gli stiamo dicendo che l'argomento(quindi l'attributo ereditato) è uguale appunto alla somma.
                L'invocazione del metodo corrisponde a <exprp1>. 
                L'assegnazione corrisponde all'azione semantica exprp.val=exprp1.val*/
                return exprp_val;

            case Tag.SUB:// insieme guida seconda prod
                match(Tag.SUB);
                term_val=term();
                exprp_val = exprp(exprp_i - term_val);
                return exprp_val;

            case Tag.RPT:// insieme guida epsilon(terza prod)
                return exprp_i;

            case Tag.EOF:// insieme guida epsilon(terza prod)
                return exprp_i;

            default:
                error("Error in Exprp()");
                return 0;

        }
    }

    private int term() {
        switch (look.tag) {
            case Tag.LPT:
                // match('(');
                fact();
                termp();
                break;

            case Tag.NUM:
                // match(Tag.NUM);
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
            case Tag.MUL:// Insieme guida prima prod
                match(Tag.MUL);
                fact();
                termp();
                break;

            case Tag.DIV:// Insieme guida seconda prod
                match(Tag.DIV);
                fact();
                termp();
                break;

            case Tag.RPT:// Insieme guida epsilon(terza prod)
                break;

            case Tag.SUM:// Insieme guida epsilon(terza prod)
                break;

            case Tag.SUB:// Insieme guida epsilon(terza prod)
                break;

            case Tag.EOF:// Insieme guida epsilon(terza prod)
                break;

            default:
                error("Error in termp()");
                break;
            // No default action due to epsilon production

        }
    }

    private void fact() {
        switch (look.tag) {

            case Tag.LPT:// Insieme guida prima prod
                match(Tag.LPT);
                expr();
                match(Tag.RPT);
                break;

            case Tag.NUM:// Insieme guida seconda prod
                match(Tag.NUM);
                break;

            default:
                error("Error in fact()");
                break;
        }
    }

    public static void main(String[] args) {
        Lexer2_32_3 lex = new Lexer2_32_3();
        String path = "...path..."; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}