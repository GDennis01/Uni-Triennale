
public class NumberTok extends Token {
    public String lexeme = "";

    public NumberTok(int tag, String s) {
        super(tag);
        lexeme = s;
    }
   // @Override
    public int getLexeme(){//metodo get che mi servirà per farmi restituire il lessema(in int) nel valutatore
        return Integer.parseInt(lexeme);
    }
    public String toString() {
        return "<" + tag + ", " + lexeme + ">";
    }

}
