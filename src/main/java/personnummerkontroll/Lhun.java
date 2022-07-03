package personnummerkontroll;

public class Lhun {
    /**
     * Århundradesiffran tas inte med i beräkningen av kontrollsiffran. Varannan siffra
     * multipliceras med 2, varannan med 1. Siffersumman av varje sådant tal summeras.
     * Den resulterande siffran subtraheras från närmaste högre tiotal, om det inte är jämnt
     * delbart med tio (i vil  är 0).
     */
    public static boolean validateLhun(String nummer, int kontrollsiffra){
        int sum = 0;
        for (int i = 0; i < nummer.length(); i++){
            char c = nummer.charAt(i);
            int x = Integer.parseInt(String.valueOf(c));
            if (i % 2 == 0) {
                sum += sumEvenPosition(x);
            } else {
                sum += x;
            }
        }
        return (10 - (sum % 10)) % 10 == kontrollsiffra;
    }

    private static int sumEvenPosition(int evenNumber) {
        int sum = 0;
        // Summera siffersumman
        int siffersumma = evenNumber * 2;
        String sifferSummaStr = Integer.toString(siffersumma);
        for (int y = 0; y < sifferSummaStr.length(); y++) {
            char integer = sifferSummaStr.charAt(y);
            int z = Integer.parseInt(String.valueOf(integer));
            sum += z;
        }
        return sum;
    }
}
