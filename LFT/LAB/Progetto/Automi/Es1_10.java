public class Es1_10 {

    public static boolean scan(String s) {

        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
                if (ch == 'a' || ch == '*')
                    state = 0;
                else if (ch == '/')
                    state = 1;
                else
                    state = 4;
                break;

            case 1:
                if (ch == 'a')
                    state = 0;
                else if (ch == '*')
                    state = 2;
                else
                    state = 4;

                break;

            case 2:
                if (ch == '/' || ch == 'a')
                    state = 2;
                else if (ch == '*')
                    state = 3;
                else
                    state = 4;
                break;

            case 3:
                if (ch == 'a')
                    state = 2;
                else if (ch == '*')
                    state = 3;
                else if (ch == '/')
                    state = 0;
                else
                    state = 4;
                break;
            case 4:// sink state

                break;

            }
        }
        return state == 0 || state == 1;
    }

    public static void main(String[] args) {
        System.out.println(scan("aaa/****/aa") ? "OK" : "NOPE");// OK
        System.out.println(scan("aa/*a*a*/") ? "OK" : "NOPE");// OK
        System.out.println(scan("aaaaa") ? "OK" : "NOPE");// OK
        System.out.println(scan("/****/") ? "OK" : "NOPE");// OK
        System.out.println(scan("/*aaa*/") ? "OK" : "NOPE");// OK
        System.out.println(scan("/*/*/") ? "OK" : "NOPE");// OK
        System.out.println(scan("*/a") ? "OK" : "NOPE");// OK
        System.out.println(scan("a/**/***/a") ? "OK" : "NOPE");// OK
        System.out.println(scan("a/**/aa/***/a") ? "OK" : "NOPE");// OK
        System.out.println("Da qui i risultati devono essere tutti NOPE");
        System.out.println(scan("a/**//***a") ? "OK" : "NOPE");// NOPE
        System.out.println(scan("aaa/*/aa") ? "OK" : "NOPE");// NOPE
        System.out.println(scan("aa/*aa") ? "OK" : "NOPE");// NOPE

    }
}