import java.io.File;
import java.io.IOException;
import java.util.Scanner;
// creating rest: https://happycoding.io/tutorials/java-server/rest-api

class Main {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        main: while(true){
            switch(promptMode()){
            case("1"):
                runSingleMode();
                break;
            case("2"):
                runFileMode();
                break;
            case("3"):
                break main;
            default:
                System.out.println("Wrong input.");
                promptMode();
                break;
            }
        }
        scanner.close();
        
        
    }

    private static void runSingleMode(){
        String line = getNextUserInput("Enter IBAN to validate:").replaceAll("\\s+","");
        IBANVerifier verifier = new IBANVerifier();
        IBAN iban = new IBAN(line);

        if(verifier.isValid(iban))
            System.out.println("IBAN is valid");
        else
            System.out.println("IBAN is invalid");
    }

    private static void runFileMode(){
        File inFile = new File(getNextUserInput("Enter the absolute path to the file for IBAN verification"));
        if(!inFile.exists()){
            System.out.println("File " + inFile.getAbsolutePath() + "does not exist.");
            return;
        }

        File outFile = new File(inFile.getAbsoluteFile().getParent(), inFile.getName().split("\\.")[0] + ".out"); // need getAbsoluteFile as getParent() on File might return null if inFile does not have one.

        if(outFile.exists()){
            String input = getNextUserInput("Output file already exists. Overwrite Y/n?").toLowerCase();
            if(!input.equals("y")){
                System.out.println("Aborting..");
                return;
            }
        }

        try {
            outFile.createNewFile();
            IBANVerifier verifier = new IBANVerifier();
            verifier.verifyFile(inFile, outFile);
            System.out.println("Wrote results to " + outFile.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Something went wrong with IO:" + ex);
        }
    }

    private static String promptMode(){
        
        String message = "Select a mode: \nPress 1 for a single IBAN, \nPress 2 for a file\nPress 3 to exit:";
        return getNextUserInput(message);
        
    }

    private static String getNextUserInput(String message){
        System.out.println(message);
        return scanner.nextLine();  
    }
}

