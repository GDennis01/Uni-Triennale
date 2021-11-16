public class Es1_2 {
    
    public static boolean scan(String s) {
    
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
                if (Character.isDigit(ch))
                    state = 3;
                else if (ch == '_')
                    state = 1;
                else if (Character.isLetter(ch))
                    state = 2;
                break;
            case 1:
                if (ch == '_')
                    state = 1;
                else if (Character.isDigit(ch) || Character.isLetter(ch))
                    state = 2;
                else
                    state = -1;
                break;
            case 2:
                if (ch == '_' || Character.isLetter(ch) || Character.isDigit(ch))
                    state = 2;
                else
                    state = -1;
                break;
        }
    }

        return state == 2;
}
    public static void main(String[] args) {
        System.out.println(scan("____") ? "OK" : "NOPE");
        System.out.println(scan("____1") ? "OK" : "NOPE");
        System.out.println(scan("__A") ? "OK" : "NOPE");
        System.out.println(scan("A__1") ? "OK" : "NOPE");
        System.out.println(scan("99") ? "OK" : "NOPE");

    }
}