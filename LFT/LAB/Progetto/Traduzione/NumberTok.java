
public class NumberTok extends Token {
    public String lexeme = "";

    public NumberTok(int tag, String s) {
        super(tag);
        lexeme = s;
    }
    @Override
    public String getLexeme(){//metodo get che mi servirà per farmi restituire il lessema nel valutatore
        return lexeme;
    }
    public String toString() {
        return "<" + tag + ", " + lexeme + ">";
    }

}
