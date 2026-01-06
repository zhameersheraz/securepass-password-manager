import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    
    private SecureRandom random;
    
    public PasswordGenerator() {
        this.random = new SecureRandom();
    }
    
    public String generate(int length, boolean useUpper, boolean useDigits, boolean useSpecial) {
        if (length < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        
        StringBuilder charset = new StringBuilder(LOWERCASE);
        if (useUpper) charset.append(UPPERCASE);
        if (useDigits) charset.append(DIGITS);
        if (useSpecial) charset.append(SPECIAL);
        
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length());
            password.append(charset.charAt(index));
        }
        
        return password.toString();
    }
    
    public String generateStrong(int length) {
        return generate(length, true, true, true);
    }

  
    public static void main(String[] args) {
        PasswordGenerator gen = new PasswordGenerator();
        
        System.out.println("Testing Password Generator");
        System.out.println("8-char password: " + gen.generate(8, false, false, false));
        System.out.println("12-char with numbers: " + gen.generate(12, true, true, false));
        System.out.println("16-char strong: " + gen.generateStrong(16));
    }
}
