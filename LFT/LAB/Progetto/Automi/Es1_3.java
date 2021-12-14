public class Es1_3 {

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
                else if (Character.isDigit(ch))
                    state = 4;
                else
                    state = -1;
                break;

            case 1:
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)// n pari
                    state = 1;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)// n dispari
                    state = 2;
                else if (ch >= 65 && ch <= 75)// ch€[A-K]
                    state = 3;
                else if (ch >= 76 && ch <= 90)
                    state = 4;
                else
                    state = -1;
                break;

            case 2:
                if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 0)
                    state = 1;
                else if (Character.isDigit(ch) && Integer.parseInt(Character.toString(ch)) % 2 == 1)
                    state = 2;
                else if (ch >= 76 && ch <= 90)// ch€[L-Z]
                    state = 3;
                else if (ch >= 65 && ch <= 75)
                    state = 4;
                else
                    state = -1;
                break;

            case 3:
                if (ch >= 97 && ch <= 122)
                    state = 3;
                else if (Character.isDigit(ch))
                    state = 4;
                else
                    state = -1;
                break;

            case 4:
                state = 4;
            }
        }
        return state == 3 ;
    }

    public static void main(String[] args) {
        System.out.println(scan("123456Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("654321Rossi") ? "OK" : "NOPE");
        System.out.println(scan("654321Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("123456Rossi") ? "OK" : "NOPE");
        System.out.println(scan("2Bianchi") ? "OK" : "NOPE");
        System.out.println(scan("2B") ? "OK" : "NOPE");
        System.out.println(scan("654322") ? "OK" : "NOPE");
        System.out.println(scan("Rossi") ? "OK" : "NOPE");

    }
}