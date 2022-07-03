package personnummerkontroll;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonnummerValidityChecker {
    ValidityChecker validityChecker = new ValidityChecker();
    public static void main(String[] args)
    {
        String personnummer = "";
        if (args.length == 1) {
            personnummer = args[0];
        }
        PersonnummerValidityChecker personnummerValidityChecker = new PersonnummerValidityChecker();
        personnummerValidityChecker.run(personnummer);

    }

    private void run(String personnummer) {
        if (personnummer.isEmpty()){
            runFromTestFiles();
        } else {
            validatePersonnummer(personnummer);
        }
    }

    private void validatePersonnummer(String personnummer){
        var result = validityChecker.validate(personnummer);
        if (result.isValid()) {
            System.out.println(result.getNumber() + " is valid");
        } else {
            System.out.println(result.getNumber() + " is invalid. "  + result.getError());
        }
    }

    private void runFromTestFiles() {
        Map<String,String> paths = new HashMap<>();
        paths.put("personnummer", "personnummer.txt");
        paths.put("ogiltiga personnummer", "ogiltigaPersonnummer.txt");
        paths.put("samordningsnummer", "samordningsnummer.txt");
        paths.put("organisationsnummer", "orgnummer.txt");

        paths.forEach((key, value) -> {
            List<String> numbers = readAllLines(value);
            System.out.println("Testar " + key);
            if (!numbers.isEmpty()) {
                numbers.forEach(this::validatePersonnummer);
            }
        });
    }

    private List<String> readAllLines(String value) {
        List<String> personnummer = new ArrayList<>();
        InputStream inputStream = PersonnummerValidityChecker.class.getResourceAsStream("/"+value);
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    personnummer.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return personnummer;
    }


}
