public class Es1_1 {
    
}

class TreZeri {
    public static boolean scan(String s) {
    
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
                if (ch == '0')
                    state = 1;
                else if (ch == '1')
                    state = 0;
                else
                    state = -1;
                break;
            case 1:
                if (ch == '0')
                    state = 2;
                else if (ch == '1')
                    state = 0;
                else
                    state = -1;
                break;
            case 2:
                if (ch == '0')
                    state = 3;
                else if (ch == '1')
                    state = 0;
                else
                    state = -1;
                break;
            case 3:
                if (ch == '0' || ch == '1')
                    state = 3;
                else
                    state = -1;
                break;
            }
        }
        return state == 3;
    }
    public static void main(String[] args) {
        System.out.println(scan("010101") ? "OK" : "NOPE");
        System.out.println(scan("1100011001") ? "OK" : "NOPE");
        System.out.println(scan("10214") ? "OK" : "NOPE");
        System.out.println(scan("11111") ? "OK" : "NOPE");
        System.out.println(scan("00000") ? "OK" : "NOPE");

    }
}

class NonTreZeri {
    public static boolean scan(String s) {
    
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
                if (ch == '1')
                    state = 0;
                else if (ch == '0')
                    state = 1;
                else
                    state = -1;
                break;
            case 1:
                if (ch == '1')
                    state = 0;
                else if (ch == '0')
                    state = 2;
                else
                    state = -1;
                break;
            case 2:
                if (ch == '1')
                    state = 0;
                else if (ch == '0')
                    state = 3;
                else
                    state = -1;
                break;
            case 3:
                state = 3;
                break;
            }
        }
        return state == 0 || state ==1 || state == 2;
    }
    public static void main(String[] args) {
        System.out.println(scan("010101") ? "OK" : "NOPE");
        System.out.println(scan("1100011001") ? "OK" : "NOPE");
        System.out.println(scan("10214") ? "OK" : "NOPE");
        System.out.println(scan("11111") ? "OK" : "NOPE");
        System.out.println(scan("00000") ? "OK" : "NOPE");

    }
}

    

    