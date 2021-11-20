public class Es1_4 {

    public static boolean scan(String s) {

        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {

            case 0://Stato iniziale
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 1;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 2;
                else 
                    state = 7; //stato pozzo
                break;

            case 1://Stato "Pari"
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 1;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 2;
                else if (ch >= 65 && ch <= 75)// ch€[A-K]
                    state = 3;//Stato finale
                else if (ch == ' ')
                    state = 4;
                else
                    state = 7;//stato pozzo
                break;

            case 2://Stato "Dispari"
            if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                state = 1;
            else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                state = 2;
            else if (ch >= 76 && ch <= 90)//ch€[L-Z]
                state = 3;
            else if (ch == ' ')
                state = 5;
            else 
                state = 7;
                break;
            
            case 3: //Stato finale
            if (ch >= 97 && ch <= 122)//ch€[a-z]
                state = 3;
            else if(ch == ' ')
                state = 6; //secondo stato finale
            else 
                state = 7;
                break;

            case 4: //Stato White Space Pari
            if(ch >= 65 && ch <= 75) // ch€[A-L]
                state = 3;
            else 
                state = 7;
                break;

            case 5://Stato White Space Dispari
            if(ch >= 76 && ch <= 90)//ch€[L-Z]
                state = 3;
            else 
                state = 7;
                break;

            case 6://Secondo stato finale
            if(ch == ' ')
                state = 6;
            else if(ch >=65 && ch <= 90)//ch€[A-Z]
                state = 3;
            else 
                state = 7;
                break;

            case 7:

                break;
            }
        }
            return state == 3 || state == 6;
    }

    public static void main(String[] args) {
        System.out.println(scan("123456 Bianchi") ? "OK" : "NOPE");//OK
        System.out.println(scan("654321 Rossi") ? "OK" : "NOPE");//OK
        System.out.println(scan("654321 Bianchi") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("123456 Rossi") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("2 Bianchi") ? "OK" : "NOPE");//OK
        System.out.println(scan("2 Be Dennis") ? "OK" : "NOPE");//OK
        System.out.println(scan("654322") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("Rossi") ? "OK" : "NOPE");//NOPE

    }
}