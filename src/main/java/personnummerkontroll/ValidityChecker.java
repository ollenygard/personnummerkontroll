package personnummerkontroll;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidityChecker {

    public ValidityResult validate(String personnummer) {
        if (!validateFormat(personnummer)) {
            return new ValidityResult(personnummer, false, "Invalid format");
        }

        String personnummerClean = personnummer.replace("-","").replace("+", "");
        if(!isOrgnummer(personnummerClean)){
            if (!validateDate(personnummerClean)){
                return new ValidityResult(personnummer, false, "Invalid date");
            }
        }

        if (!validateLhun(personnummerClean)){
            return new ValidityResult(personnummer, false, "Lhun check failed");
        }

        return new ValidityResult(personnummer, true);
    }

    private boolean validateLhun(String personnummerClean) {
        int kontrollsiffra = Integer.parseInt(personnummerClean.substring(personnummerClean.length() -1));
        String nummer = personnummerClean.substring(0, personnummerClean.length() - 1);
        if (personnummerClean.length() == 12) {
            nummer = personnummerClean.substring(2, personnummerClean.length() - 1);
        }

        return Lhun.validateLhun(nummer, kontrollsiffra);
    }

    private boolean validateFormat(String personnummer) {

        if(personnummer.contains("-") && personnummer.indexOf("-") != personnummer.length() - 5){
            return false;
        }

        if(personnummer.contains("+") && personnummer.indexOf("+") != personnummer.length() - 5){
            return false;
        }

        String personnummerClean = personnummer.replace("-","").replace("+", "");
        // Check if number
        try {
            Long.parseLong(personnummerClean);
        } catch (NumberFormatException exception) {
            return false;
        }

        // Check length. Should be 10 or 12
        if (!(personnummerClean.length() == 10 || personnummerClean.length() == 12)){
            return false;
        }

        // Format valid
        return true;
    }


    private boolean validateDate(String personnummerClean) {
        String fodelsedatumStr = personnummerClean.substring(0,6);
        if (personnummerClean.length() == 12){
            fodelsedatumStr = personnummerClean.substring(2,8);
        }
        if(isSamordningsnummer(fodelsedatumStr)){
            fodelsedatumStr = Integer.toString(Integer.parseInt(fodelsedatumStr) - 60);
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
            formatter.setLenient(false);
            formatter.parse(fodelsedatumStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private boolean isSamordningsnummer(String fodelsedatumStr) {
        int fodelsedag = Integer.parseInt(fodelsedatumStr.substring(4,6));
        return fodelsedag >= 61 && fodelsedag <= 91;
    }

    private boolean isOrgnummer(String personnummerClean) {

        if (personnummerClean.length() == 12 && !personnummerClean.startsWith("16")) {
            return false;
        }

        // Mittenparet måste vara minst 20.
        int mittenPar = Integer.parseInt(personnummerClean.substring(2,4));
        if(mittenPar <= 19){
            return false;
        }

        return true;
    }

    public static class ValidityResult {
        private String number;
        private boolean valid;
        private String error;

        public String getNumber() {
            return number;
        }

        public ValidityResult(String number, boolean valid) {
            this.number = number;
            this.valid = valid;
        }

        public ValidityResult(String number, boolean valid, String error) {
            this.number = number;
            this.valid = valid;
            this.error = error;

        }
        public boolean isValid() {
            return valid;
        }

        public String getError() {
            return error;
        }
    }

}
