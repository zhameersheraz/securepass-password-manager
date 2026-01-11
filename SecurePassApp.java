import java.util.Scanner;

public class SecurePassApp {
    private static PasswordGenerator generator = new PasswordGenerator();
    private static PasswordAnalyzer analyzer = new PasswordAnalyzer();
    private static PasswordVault vault = new PasswordVault();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   SecurePass - Password Manager");
        System.out.println("   Build 2026 | Zhameer Sheraz Tampugao");
        System.out.println("=================================\n");
        
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Generate Password");
            System.out.println("2. Check Password Strength");
            System.out.println("3. Open Vault");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: generatePassword(); break;
                case 2: checkStrength(); break;
                case 3: vaultMenu(); break;
                case 4: 
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
    
    private static void generatePassword() {
        System.out.print("Password length (8-32): ");
        int length = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Include uppercase? (y/n): ");
        boolean upper = scanner.nextLine().equalsIgnoreCase("y");
        
        System.out.print("Include digits? (y/n): ");
        boolean digits = scanner.nextLine().equalsIgnoreCase("y");
        
        System.out.print("Include special characters? (y/n): ");
        boolean special = scanner.nextLine().equalsIgnoreCase("y");
        
        try {
            String password = generator.generate(length, upper, digits, special);
            System.out.println("\n[OK] Generated password: " + password);
            
            PasswordAnalyzer.StrengthResult result = analyzer.analyze(password);
            System.out.println("Strength: " + result.strength);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void checkStrength() {
        System.out.print("Enter password to analyze: ");
        String password = scanner.nextLine();
        
        PasswordAnalyzer.StrengthResult result = analyzer.analyze(password);
        System.out.println("\n--- Analysis Result ---");
        System.out.println("Strength: " + result.strength);
        System.out.println("Score: " + result.score + "/7");
        System.out.println("\nFeedback:");
        System.out.println(result.feedback);
    }
    
    private static void vaultMenu() {
        System.out.print("Enter master password: ");
        String master = scanner.nextLine();
        
        if (!vault.unlock(master)) {
            System.out.println("[X] Incorrect master password!");
            return;
        }
        
        System.out.println("[OK] Vault unlocked");
        
        while (true) {
            System.out.println("\n===== VAULT MENU =====");
            System.out.println("1. Add Password");
            System.out.println("2. View Password");
            System.out.println("3. List All Services");
            System.out.println("4. Remove Password");
            System.out.println("5. Lock Vault");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Service name: ");
                        String service = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();
                        vault.addPassword(service, password);
                        System.out.println("[OK] Password saved");
                        break;
                    case 2:
                        System.out.print("Service name: ");
                        service = scanner.nextLine();
                        password = vault.getPassword(service);
                        if (password != null) {
                            System.out.println("Password: " + password);
                        } else {
                            System.out.println("Service not found");
                        }
                        break;
                    case 3:
                        System.out.println("\nStored services:");
                        vault.getAllServices().forEach((s, p) -> 
                            System.out.println("  - " + s));
                        break;
                    case 4:
                        System.out.print("Service name: ");
                        service = scanner.nextLine();
                        vault.removePassword(service);
                        System.out.println("[OK] Password removed");
                        break;
                    case 5:
                        vault.lock();
                        System.out.println("[OK] Vault locked");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}