public class Es1_5 {

    public static boolean scan(String s) {

        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {

            case 0:
                if (ch >= 65 && ch <= 75) // ch€[A-K]
                    state = 1;
                else if (ch >= 76 && ch <= 90)// ch€[L-Z]
                    state = 2;
                else
                    state = 7;
                break;

            case 1:// Corso A ( A-K)
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 3;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 4;
                else if (ch >= 97 && ch <= 122)// ch€[a-z]
                    state = 1;
                else
                    state = 7;

                break;

            case 2:// Corso B ( L-Z)
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 6;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 5;
                else if (ch >= 97 && ch <= 122)// ch€[a-z]
                    state = 2;
                else
                    state = 7;
                break;

            case 3:// Corso A Pari

                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 3;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 4;
                else
                    state = 7;
                break;

            case 4:// Corso A Dispari

                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 3;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 4;
                else
                    state = 7;
                break;

            case 5:// Corso B Dispari
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 6;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 5;
                else
                    state = 7;

                break;

            case 6:// Corso B Pari
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 6;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 5;
                else
                    state = 7;

                break;

            }
        }
        return state == 3 || state == 5;
    }

    public static void main(String[] args) {
        System.out.println(scan("Bianchi123456") ? "OK" : "NOPE");
        System.out.println(scan("Rossi654321") ? "OK" : "NOPE");
        System.out.println(scan("Bianchi654321") ? "OK" : "NOPE");
        System.out.println(scan("Rossi123456") ? "OK" : "NOPE");
        System.out.println(scan("Bianchi2") ? "OK" : "NOPE");
        System.out.println(scan("B2") ? "OK" : "NOPE");
        System.out.println(scan("654322") ? "OK" : "NOPE");
        System.out.println(scan("Rossi") ? "OK" : "NOPE");


    }
}