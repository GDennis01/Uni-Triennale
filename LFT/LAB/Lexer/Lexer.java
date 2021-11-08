
import java.io.*;
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
            if (peek == '\n')
                line++;
            readch(br);
        }

        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            case '/':
                peek = ' ';
                return Token.div;

            case ';':
                peek = ' ';
                return Token.semicolon;

            case ',':
                peek = ' ';
                return Token.comma;

            // WORD

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character" + " after & : " + peek);
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character" + " after | : " + peek);
                    return null;
                }

            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else if (peek == ' ') {
                    peek = ' ';
                    return Word.lt;
                } else {
                    System.err.println("Erroneous character" + " after < : " + peek);
                    return null;
                }

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else if (peek == ' ') {
                    peek = ' ';
                    return Word.gt;
                } else {
                    System.err.println("Erroneous character" + " after > : " + peek);
                    return null;
                }

            case '=':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character" + " after = : " + peek);
                    return null;
                }
                

            case (char) -1:
                return new Token(Tag.EOF);

            default:
                
                if (Character.isLetter(peek)) {
                    String word="";
                    while (/*Character.isLetter(peek) || Character.isDefined(peek) &&*/ peek != ' ' && peek != '\t' && peek != '\n' && peek != '\r') {
                        word=word+peek;
                        readch(br);
                    }
                   if(word.matches("assign"))
                        return Word.assign;
                   if(word.matches("to"))
                        return Word.to;
                   if(word.matches("if"))
                        return Word.iftok;
                   if(word.matches("else"))
                        return Word.elsetok;
                   if(word.matches("while"))
                        return Word.whiletok;
                   if(word.matches("begin"))
                        return Word.begin;
                   if(word.matches("end"))
                        return Word.end;
                   if(word.matches("print"))
                        return Word.print;
                   if(word.matches("read"))
                        return Word.read;
                   if(word.matches("[a-zA-Z][a-zA-Z0-9]*")) 
                        return new Word(Tag.ID,word);
                    // ... gestire il caso degli identificatori e delle parole chiave //

                } else if (Character.isDigit(peek)) {
                    String word="";
                    while ( /*Character.isDigit(peek) && */peek != ' ' && peek != '\t' && peek != '\n' && peek != '\r') {
                        word=word+peek;
                        readch(br);
                        
                    }
                    if(word.matches("0||[1-9][0-9]*"))
                        return new NumberTok(Tag.NUM,word);
                    // ... gestire il caso dei numeri ... //

                } else {
                    System.err.println("Erroneous character: " + peek);
                    return null;
                }
                return null;
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "/Miriam.txt"; // il percorso del file da leggere
        File f=new File("Miriam.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {
            System.out.println("Lol");
            e.printStackTrace();
        }
    }

}
