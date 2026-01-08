import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionEngine {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;
    
    public static String encrypt(String data, String masterPassword) throws Exception {
        byte[] salt = generateSalt();
        SecretKey key = deriveKey(masterPassword, salt);
        
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        
        byte[] encrypted = cipher.doFinal(data.getBytes());
        
        byte[] combined = new byte[salt.length + iv.length + encrypted.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encrypted, 0, combined, salt.length + iv.length, encrypted.length);
        
        return Base64.getEncoder().encodeToString(combined);
    }
    
    public static String decrypt(String encryptedData, String masterPassword) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        
        byte[] salt = new byte[16];
        byte[] iv = new byte[16];
        byte[] encrypted = new byte[combined.length - 32];
        
        System.arraycopy(combined, 0, salt, 0, 16);
        System.arraycopy(combined, 16, iv, 0, 16);
        System.arraycopy(combined, 32, encrypted, 0, encrypted.length);
        
        SecretKey key = deriveKey(masterPassword, salt);
        
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
    
    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
    
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}