public class Es1_5 {
    
    public static boolean scan(String s) {
    
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);
            switch (state) {
            case 0:
              
                break;
            case 1:
                
                break;
            case 2:
                
                break;
            case 3:
              
                break;
            }
        }
        return state == 3;
    }
    public static void main(String[] args) {
        System.out.println(scan("Stringa") ? "OK" : "NOPE");


    }
}