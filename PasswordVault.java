import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PasswordVault {
    private static final String VAULT_FILE = "vault.dat";
    private Map<String, String> passwords;
    private String masterPassword;
    
    public PasswordVault() {
        this.passwords = new HashMap<>();
    }
    
    public boolean unlock(String masterPassword) {
        this.masterPassword = masterPassword;
        return loadVault();
    }
    
    public void addPassword(String service, String password) throws Exception {
        if (masterPassword == null) {
            throw new IllegalStateException("Vault is locked");
        }
        passwords.put(service, password);
        saveVault();
    }
    
    public String getPassword(String service) throws Exception {
        if (masterPassword == null) {
            throw new IllegalStateException("Vault is locked");
        }
        return passwords.get(service);
    }
    
    private void saveVault() throws Exception {
        StringBuilder data = new StringBuilder();
        for (Map.Entry<String, String> entry : passwords.entrySet()) {
            data.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        
        String encrypted = EncryptionEngine.encrypt(data.toString(), masterPassword);
        
        try (FileWriter writer = new FileWriter(VAULT_FILE)) {
            writer.write(encrypted);
        }
    }
    
    private boolean loadVault() {
        File file = new File(VAULT_FILE);
        if (!file.exists()) {
            return true;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(VAULT_FILE))) {
            String encrypted = reader.readLine();
            String decrypted = EncryptionEngine.decrypt(encrypted, masterPassword);
            
            passwords.clear();
            String[] lines = decrypted.split("\n");
            for (String line : lines) {
                if (line.isEmpty()) continue;
                String[] parts = line.split(":", 2);
                passwords.put(parts[0], parts[1]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}