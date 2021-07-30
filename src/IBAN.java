import java.math.BigInteger;

public class IBAN {
    private String iban;
    private BigInteger integerRepr;
    private String country;

    public IBAN(String iban){
        this.iban = iban;
    }

    public String getCountry() throws InvalidLengthException {
        if(country == null){
            if(getLength() > 2)
                this.country = iban.substring(0, 2);
            else
                throw new InvalidLengthException("String does not contain the country code");
        }
        return country;
    }

    public int getLength(){
        return iban.length();
    }

    public BigInteger toIntegerRepr() throws UnexpectedCharException {
        if(integerRepr == null){
            String moved = iban.substring(4).concat(iban.substring(0,4));
            StringBuilder replaced = new StringBuilder();
            for(int i = 0; i<moved.length(); i++){
                char c = moved.charAt(i);
                if(Character.isDigit(c)){
                    replaced.append(c);
                } else if((int)c >= 65 && (int)c <= 90) {
                    replaced.append((Integer.toString((int)c - 55)));
                } else {
                    throw new UnexpectedCharException("Char '" + c +"' Is not a valid IBAN char");
                }
            }
            this.integerRepr = new BigInteger(replaced.toString());
        } 
        
        return this.integerRepr;
    }

    @Override
    public String toString() {
        return iban;
    }
}

class UnexpectedCharException extends Exception{
    String message;

    UnexpectedCharException(String message) {
     this.message=message;
    }
    public String toString(){ 
     return ("UnexpectedCharException: "+ this.message);
    }
}

class InvalidLengthException extends Exception{
    String message;

    InvalidLengthException(String message) {
     this.message=message;
    }
    public String toString(){ 
     return ("InvalidLengthException: "+ this.message);
    }
}
