import java.util.Arrays;
import java.util.List;

public class PasswordAnalyzer {
    private static final List<String> COMMON_PASSWORDS = Arrays.asList(
        "password", "123456", "123456789", "12345678", "12345",
        "qwerty", "abc123", "password1", "admin", "letmein"
    );
    
    public StrengthResult analyze(String password) {
        int score = 0;
        StringBuilder feedback = new StringBuilder();
        
        if (password.length() < 8) {
            feedback.append("[X] Too short (minimum 8 characters)\n");
        } else if (password.length() >= 12) {
            score += 2;
            feedback.append("[OK] Good length\n");
        } else {
            score += 1;
            feedback.append("[!] Acceptable length\n");
        }
        
        if (password.matches(".*[a-z].*")) {
            score += 1;
        } else {
            feedback.append("[X] Add lowercase letters\n");
        }
        
        if (password.matches(".*[A-Z].*")) {
            score += 1;
        } else {
            feedback.append("[X] Add uppercase letters\n");
        }
        
        if (password.matches(".*\\d.*")) {
            score += 1;
        } else {
            feedback.append("[X] Add numbers\n");
        }
        
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*")) {
            score += 2;
        } else {
            feedback.append("[X] Add special characters\n");
        }
        
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            score = 0;
            feedback.insert(0, "[ALERT] CRITICAL: This is a commonly used password!\n");
        }
        
        String strength;
        if (score >= 6) strength = "STRONG";
        else if (score >= 4) strength = "MEDIUM";
        else strength = "WEAK";
        
        return new StrengthResult(strength, score, feedback.toString());
    }
    
    public static class StrengthResult {
        public final String strength;
        public final int score;
        public final String feedback;
        
        public StrengthResult(String strength, int score, String feedback) {
            this.strength = strength;
            this.score = score;
            this.feedback = feedback;
        }
    }
}