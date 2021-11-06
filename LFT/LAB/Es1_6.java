import java.util.*;

public class Es1_6 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(
                "Turno 2: Cognome A-K e penultima cifra pari\nTurno 3:Cognome L-Z e penultima cifra dispari\nInserire il tuo numeri di matricola seguito dal nome");
        String token = in.next();
        System.out.println(scan(token)?"Sei del turno T2 o T3":"Non sei del turno T2 o T3 oppure stringa riconosciuta");
        }

    public static boolean scan(String token){
        int i = 0;
        int tmpVal;
        int state = 0;
        while (state >= 0 && i < token.length()) {
            final char c = token.charAt(i++);
            switch (state) {
                case 0:// {q0}
                    if (Character.isDigit(c)) {
                        tmpVal = Character.getNumericValue(c);
                        if (tmpVal % 2 == 1)
                            state = 2;
                        else if (tmpVal % 2 == 0)
                            state = 1;
                    } else
                        state = -1;
                    break;

                case 1:// {qP}
                    if (Character.isDigit(c)) {
                        tmpVal = Character.getNumericValue(c);
                        if (tmpVal % 2 == 1)
                            state = 2;
                        else if (tmpVal % 2 == 0)
                            state = 3;
                    } else
                        state = -1;
                    break;

                case 2:// {qD}
                    if (Character.isDigit(c)) {
                        tmpVal = Character.getNumericValue(c);
                        if (tmpVal % 2 == 1)
                            state = 6;
                        else if (tmpVal % 2 == 0)
                            state = 1;
                    } else
                        state = -1;

                    break;

                case 3:// {qP,P}
                    if (Character.isDigit(c)) {
                        tmpVal = Character.getNumericValue(c);
                        if (tmpVal % 2 == 0)
                            state = 3;
                        else
                            state = 4;
                    } else if (c >= 'A' || c <= 'K')
                        state = 7;
                    else
                        state = -1;
                    break;

                case 4:// {qD,P}
                    if (Character.isDigit(c)) {
                        tmpVal = Character.getNumericValue(c);
                        if (tmpVal % 2 == 0)
                            state = 5 ;
                        else
                            state = 6 ;
                    } else if (c>='A' || c<='K')
                        state = 7;
                    else
                        state = -1;
                    break;

                case 5:// {qP,D}
                if(Character.isDigit(c)){
                    tmpVal = Character.getNumericValue(c);
                    if(tmpVal%2==0)
                        state=3;
                    else state=4;
                }else if(c>='L' || c<='Z')
                    state=7;
                else state=-1;
                    break;

                case 6:// {qD,D}
                if(Character.isDigit(c)){
                    tmpVal = Character.getNumericValue(c);
                    if(tmpVal%2==0)
                        state=5;
                    else state=6;
                }else if(c>='L' || c<='Z')
                    state=7;
                else state=-1;
                    break;

                case 7:// Final State
                if(c>='a' || c<='z')
                    state=7;
                else state=-1;
                    break;

                default:// default case
                    System.out.println("Qualcosa è andato storto durante il riconoscimento della stringa");
                    break;
            }
            
    }
    return state==7;
    }
}
