public class Es1_6 {
    //TODO: FIXING ES1.6
    public static boolean scan(String s) {

        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 1;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 2;
                else
                    state = 6;
                break;
            case 1:// Even State
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 4;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 3;
                else
                    state = 8;
                break;

            case 2:// Odd state
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 5;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 6;
                else
                    state = 8;
                break;

            case 3:// Even Odd state
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 4;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 6;
                else if (ch >= 65 && ch <= 75)// ch€[A-K]
                    state = 7;
                else
                    state = 8;
                break;

            case 4:// Even Even state
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 5;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 3;
                else if (ch >= 65 && ch <= 75)// ch€[A-K]
                    state = 7;
                else
                    state = 8;
                break;

            case 5:// Odd Even state
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 4;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 6;
                else if (ch >= 76 && ch <= 90)// ch€[L-Z]
                    state = 7;
                else
                    state = 8;
                break;

            case 6:// Odd Odd state
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 5;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 3;
                else if (ch >= 76 && ch <= 90)// ch€[L-Z]
                    state = 7;
                else
                    state = 8;
                break;

            case 7:
                if (ch >= 97 && ch <= 122)// ch€[a-z]
                    state = 7;
                else
                    state = 8;
                break;

            default:

                break;

            }
        }
        return state == 7;
    }

    public static void main(String[] args) {
        System.out.println(scan("123456Rossi") ? "OK" : "NOPE");// OK
        System.out.println(scan("654321Bianchi") ? "OK" : "NOPE");// OK
        System.out.println(scan("221B") ? "OK" : "NOPE");// OK
        System.out.println(scan("111R") ? "OK" : "NOPE");// OK
        System.out.println(scan("123456Bianchi") ? "OK" : "NOPE");// NOPE
        System.out.println(scan("654321Rossi") ? "OK" : "NOPE");// NOPE
        System.out.println(scan("2Bianchi") ? "OK" : "NOPE");// NOPE
        System.out.println(scan("5") ? "OK" : "NOPE");// NOPE
    }
}