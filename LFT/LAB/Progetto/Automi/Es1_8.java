public class Es1_8 {

    public static boolean scan(String s) {

        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {

            case 0:
                if (ch == '+' || ch == '-')
                    state = 1;
                else if (Character.isDigit(ch))
                    state = 2;// Final state 1
                else if (ch == '.')
                    state = 3;
                else
                    state = 9;// sink state
                break;

            case 1:
                if (Character.isDigit(ch))
                    state = 2;// Final state 1
                else if (ch == '.')
                    state = 3;
                else
                    state = 9;
                break;

            case 2:
                if (Character.isDigit(ch))
                    state = 2;
                else if (ch == '.')
                    state = 3;
                else if (ch == 'e')
                    state = 5;
                else
                    state = 9;

                break;

            case 3:
                if (Character.isDigit(ch))
                    state = 4;
                else
                    state = 7;
                break;

            case 4:
                if (Character.isDigit(ch))
                    state = 4;//Final state 2
                else if (ch == 'e')
                    state = 5;
                else
                    state = 9;

                break;

            case 5:
                if (ch == '+' || ch == '-')
                    state = 6;
                else if (ch == '.')
                    state = 7;
                else if (Character.isDigit(ch))
                    state = 8;
                else
                    state = 9;
                break;

            case 6:
                if (ch == '.')
                    state = 7;
                else if (Character.isDigit(ch))
                    state = 8;
                else
                    state = 9;
                break;

            case 7:
                if(Character.isDigit(ch))
                    state = 10;//Final state 3
                else 
                    state = 9;
                break;

            case 8://Final state 4;
                if(Character.isDigit(ch))
                    state = 8;
                else if(ch == '.')
                    state = 7;
                else 
                    state = 9;
                break;

            case 9://Sink state

                break;

            case 10:
                if(Character.isDigit(ch))
                    state = 10;
                else 
                    state = 9;
                break;
            }
        }
        return state == 2 || state == 4 || state == 8 || state  == 10;
    }

    public static void main(String[] args) {
        System.out.println(scan("123") ? "OK" : "NOPE");//OK
        System.out.println(scan("123.5") ? "OK" : "NOPE");//OK
        System.out.println(scan(".567") ? "OK" : "NOPE");//OK
        System.out.println(scan("+7.5") ? "OK" : "NOPE");//OK
        System.out.println(scan("-.7") ? "OK" : "NOPE");//OK
        System.out.println(scan("67e10") ? "OK" : "NOPE");//OK
        System.out.println(scan("1e-2") ? "OK" : "NOPE");//OK 
        System.out.println(scan("-.7e2") ? "OK" : "NOPE");//
        System.out.println(scan("1e2.3") ? "OK" : "NOPE");//OK 
        System.out.println("Da qui in poi i risultato devono essere tutti NOPE");
        System.out.println(scan(".") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("e3") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("123.") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("+e6") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("1.2.3") ? "OK" : "NOPE");//NOPE
        System.out.println(scan("4e5e6") ? "OK" : "NOPE");//NOPE 
        System.out.println(scan("++3") ? "OK" : "NOPE");//NOPE

    }
}