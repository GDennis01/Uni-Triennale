
public class Token {
    public final int tag;
    public Token(int t) { tag = t;  }
    public String toString() {return "<" + tag + ","+(char)tag+">";}
	
	public String getLexeme(){//Metodo get per il lessema. Viene overrideato da quello della classe NumberTok
		return "Error";
	}
    public static final Token
	not = new Token('!'),
	lpt = new Token('('),
	rpt = new Token(')'),
	lpg = new Token('{'),
	rpg = new Token('}'),
	plus = new Token('+'),
	minus = new Token('-'),
	mult = new Token('*'),
	div = new Token('/'),
	semicolon = new Token(';'),
	comma = new Token(',');
}
