import java.io.*;
import java.util.*;
// this is a random comment
public class Lexer {

    private final String identifier_RE = "[a-zA-Z[_[_]*[a-zA-Z0-9]]][a-zA-Z0-9_]*"; // espressione regolare per gli identificatori
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

        case '/': // implementazione commenti
            readch(br);
            if (peek == '/') {
                while(peek != '\n')
                    readch(br);
                peek = ' ';
                return lexical_scan(br);
            } else if (peek == '*') {
                readch(br);
                while (peek != (char) -1) {
                    readch(br);
                    while (peek == '*') {
                        readch(br);
                        if (peek == '/') {
                            peek = ' ';
                            return lexical_scan(br);
                        }
                    }
                }
                System.err.println("ERROR");
                return null;
            }
            peek = ' ';
            return Token.div;

        case ';':
            peek = ' ';
            return Token.semicolon;

        case ',':
            peek = ' ';
            return Token.comma;

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
            if (peek == '>') {
                peek = ' ';
                return Word.ne;
            } else if (peek == '=') {
                peek = ' ';
                return Word.le;
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
            if (peek == '=') {
                peek = ' ';
                return Word.eq;
            } else {
                System.err.println("Erroneous character" + " after = : " + peek);
                return null;
            }

        case (char) -1:
            return new Token(Tag.EOF);

        default:
            if (Character.isLetter(peek) || peek == '_') { // gestisco identificatori e parole chiave
                String identifier = "";
                while(Character.isLetterOrDigit(peek) || peek == '_'){
                    identifier+=peek;
                    readch(br);
                }

                switch (identifier){

                    case "assign":
                        return Word.assign;

                    case "to":
                        return Word.to;

                    case "if":
                        return Word.iftok;

                    case "else":
                        return Word.elsetok;

                    case "while":
                        return Word.whiletok;

                    case "begin":
                        return Word.begin;

                    case "end":
                        return Word.end;

                    case "print":
                        return Word.print;

                    case "read":
                        return Word.read;

                    case "or":
                        return Word.or;

                    case "and":
                        return Word.and;

                    default:
                        if(identifier.matches(identifier_RE)){
                            return new Word(Tag.ID, identifier);
                        }
                        System.err.println("Syntax error in: " + identifier);
                        return null;
                        
                }


            } else if (Character.isDigit(peek)) {

                String number = "";
                while(Character.isDigit(peek)){
                    number+=peek;
                    readch(br);
                }
                return new Word(Tag.NUM, number);

            } else {
                System.err.println("Erroneous character: " + peek);
                return null;
            }
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "C:\\Users\\occhi\\Github\\university\\LFT_lab\\prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
