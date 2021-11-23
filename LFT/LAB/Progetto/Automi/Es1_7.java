public class Es1_7 {

    public static boolean scan(String s) {

        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
                if (Character.toLowerCase(ch) == 'd')
                    state = 1;
                else if (Character.toLowerCase(ch) != 'd')
                    state = 7;
                else
                    state = 12;
                break;

            case 1:
                if (Character.toLowerCase(ch) == 'e')
                    state = 2;
                else if (Character.toLowerCase(ch) != 'e')
                    state = 8;
                else
                    state = 12;
                break;

            case 2:
                if (Character.toLowerCase(ch) == 'n')
                    state = 3;
                else if (Character.toLowerCase(ch) != 'n')
                    state = 9;
                else
                    state = 12;
                break;

            case 3:
                if (Character.toLowerCase(ch) == 'n')
                    state = 4;
                else if (Character.toLowerCase(ch) != 'n')
                    state = 10;
                else
                    state = 12;
                break;

            case 4:
                if (Character.toLowerCase(ch) == 'i')
                    state = 5;
                else if (Character.toLowerCase(ch) != 'i')
                    state = 11;
                else
                    state = 12;
                break;

            case 5:
                if (Character.toLowerCase(ch) == 's' || Character.toLowerCase(ch) != 's')
                    state = 6;
                else
                    state = 12;
                break;

            case 6:
                break;

            case 7:
                if (Character.toLowerCase(ch) == 'e')
                    state = 8;
                else
                    state = 12;
                break;

            case 8:
                if (Character.toLowerCase(ch) == 'n')
                    state = 9;
                else
                    state = 12;
                break;

            case 9:
                if (Character.toLowerCase(ch) == 'n')
                    state = 10;
                else
                    state = 12;
                break;

            case 10:
                if (Character.toLowerCase(ch) == 'i')
                    state = 11;
                else
                    state = 12;
                break;

            case 11:
                if (Character.toLowerCase(ch) == 's')
                    state = 6;
                else
                    state = 12;

                break;

            }
        }
        return state == 6;
    }

    public static void main(String[] args) {
        System.out.println(scan("Denni%") ? "OK" : "NOPE");

    }
}