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
                return exprp_val;

            case Tag.NUM:
                //term();
                term_val = term();
                //exprp();
                exprp_val = exprp(term_val);
                return exprp_val;
            default:
                error("Error in expr()");
                break;
        }
        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val=0;
        switch (look.tag) {
            case Tag.SUM:
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
        int termp_i,term_val=0;
        switch (look.tag) {
            case Tag.LPT:
                // match('(');
                termp_i=fact();
                term_val=termp(termp_i);
                return term_val;

            case Tag.NUM:
                // match(Tag.NUM);
                termp_i=fact();
                term_val=termp(termp_i);
                return term_val;
                

            default:
                error("Error in term()");
                return 0;
        }
    }

    private int termp(int termp_i) {
        int fact_val,termp_val=termp_i;//nel caso in cui l'insieme guida sia quello epsilon, il valore di termp_val è quello di termp_i stesso
        switch (look.tag) {
            case Tag.MUL:// Insieme guida prima prod
                match(Tag.MUL);
                fact_val=fact();
                termp_val = termp(termp_i * fact_val);
                return termp_val;
               

            case Tag.DIV:// Insieme guida seconda prod
                match(Tag.DIV);
                fact_val=fact();
                termp_val = termp(termp_i / fact_val);
                return termp_val;

            case Tag.RPT:// Insieme guida epsilon(terza prod)
            return termp_val;

            case Tag.SUM:// Insieme guida epsilon(terza prod)
                return termp_val;

            case Tag.SUB:// Insieme guida epsilon(terza prod)
            return termp_val;

            case Tag.EOF:// Insieme guida epsilon(terza prod)
            return termp_val;

            default:
                error("Error in termp()");
                return 0;

        }
    }

    private int fact() {
        int expr_val=0,fact_val=0;
        switch (look.tag) {

            case Tag.LPT:// Insieme guida prima prod
                match(Tag.LPT);
                expr_val=expr();
                match(Tag.RPT);
                return expr_val;

            case Tag.NUM:// Insieme guida seconda prod
                //fact_val=Integer.parseInt(look.getLexeme());//Mi salvo il valore del lessema di look prima che venga fatto il match(e quindi passi al token successivo)
                fact_val=((NumberTok)look).getLexeme();
                match(Tag.NUM);
                return(fact_val);//restituisco il valore del numero matchato

            default:
                error("Error in fact()");
                return 0;
        }
    }

    public static void main(String[] args) {
        Lexer2_3 lex = new Lexer2_3();
        String path = "input.txt"; // il percorso del file da leggere
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