
public class NumberTok extends Token {
    public String lexeme = "";

    public NumberTok(int tag, String s) {
        super(tag);
        lexeme = s;
    }
    @Override
    public String getLexeme(){
        return lexeme;
    }
    public String toString() {
        return "<" + tag + ", " + lexeme + ">";
    }

}
