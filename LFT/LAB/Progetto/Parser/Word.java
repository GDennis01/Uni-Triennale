public class Word extends Token {
	public String lexeme = "";

	public Word(int tag, String s) {
		super(tag);
		lexeme = s;
	}

	public String toString() {
		return "<" + tag + ", " + lexeme + ">";
	}

	public static final Word 
			number = new Word(Tag.NUM, "number"), 
			identifier = new Word(Tag.ID, "identifier"),
			assign = new Word(Tag.ASSIGN, "assign"), 
			to = new Word(Tag.TO, "to"), 
			iftok = new Word(Tag.IF, "if"),
			elsetok = new Word(Tag.ELSE, "else"), 
			whiletok = new Word(Tag.WHILE, "while"),
			begin = new Word(Tag.BEGIN, "begin"), 
			end = new Word(Tag.END, "end"), 
			print = new Word(Tag.PRINT, "print"),
			read = new Word(Tag.READ, "read"), 
			or = new Word(Tag.OR, "||"), 
			and = new Word(Tag.AND, "&&"),
			lt = new Word(Tag.RELOP, "<"), 
			gt = new Word(Tag.RELOP, ">"), 
			eq = new Word(Tag.RELOP, "=="),
			le = new Word(Tag.RELOP, "<="), 
			ne = new Word(Tag.RELOP, "<>"), 
			ge = new Word(Tag.RELOP, ">=");
}
