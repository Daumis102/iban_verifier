import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;


class IBANVerifier {
    public void verifyFile(File inFile, File outFile) throws IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader(inFile));

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String line = reader.readLine();
        while(line != null){
            IBAN iban = new IBAN(line);

            if(isValid(iban)){
                writer.write(iban.toString() + ";true");
                writer.newLine();
            } else {
                writer.write(iban.toString() + ";false");
                writer.newLine();
            }
            line = reader.readLine();
        }
        reader.close();
        writer.close();
    }

    public boolean isValid(IBAN iban) {
        if(!isLengthValid(iban)){
            return false;
        }
            
        try{
            BigInteger mod = iban.toIntegerRepr().mod(new BigInteger("97"));
            return mod.equals(new BigInteger("1"));
        } catch (UnexpectedCharException ex){
            System.out.println(ex);
            return false;
        }
    }

    private boolean isLengthValid(IBAN iban) {
        final HashMap<String , Integer> lengths = new HashMap<String , Integer>() {{
            put("AL", 28);
            put("AD", 24);
            put("AT", 20);
            put("AZ", 28);
            put("BH", 22);
            put("BY", 28);
            put("BE", 16);
            put("BA", 20);
            put("BR", 29);
            put("BG", 22);
            put("CR", 22);
            put("HR", 21);
            put("CY", 28);
            put("CZ", 24);
            put("DK", 18);
            put("DO", 28);
            put("EG", 29);
            put("SV", 28);
            put("EE", 20);
            put("FO", 18);
            put("FI", 18);
            put("FR", 27);
            put("GE", 22);
            put("DE", 22);
            put("GI", 23);
            put("GR", 27);
            put("GL", 18);
            put("GT", 28);
            put("VA", 22);
            put("HU", 28);
            put("IS", 26);
            put("IQ", 23);
            put("IE", 22);
            put("IL", 23);
            put("IT", 27);
            put("JO", 30);
            put("KZ", 20);
            put("XK", 20);
            put("KW", 30);
            put("LV", 21);
            put("LB", 28);
            put("LY", 25);
            put("LI", 21);
            put("LT", 20); 
            put("LU", 20); 
            put("MT", 31); 
            put("MR", 27); 
            put("MU", 30); 
            put("MD", 24); 
            put("MC", 27); 
            put("ME", 22); 
            put("NL", 18); 
            put("MK", 19); 
            put("NO", 15); 
            put("PK", 24); 
            put("PS", 29); 
            put("PL", 28); 
            put("PT", 25); 
            put("QA", 29); 
            put("RO", 24); 
            put("LC", 32); 
            put("SM", 27); 
            put("ST", 25); 
            put("SA", 24); 
            put("RS", 22); 
            put("SC", 31); 
            put("SK", 24); 
            put("SI", 19); 
            put("ES", 24); 
            put("SD", 18); 
            put("SE", 24); 
            put("CH", 21); 
            put("TL", 23); 
            put("TN", 24); 
            put("TR", 26); 
            put("UA", 29); 
            put("AE", 23); 
            put("GB", 22); 
            put("VG", 24); 
        }};

        try {
            if(lengths.containsKey(iban.getCountry()))
                return iban.getLength() == lengths.get(iban.getCountry());
            else
                return false;
        } catch (InvalidLengthException e) {
            return false;
        }
    }
}

