package org.iushu.weboot.component;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javax.crypto.Cipher.DECRYPT_MODE;

/**
 * @author iuShu
 * @since 6/28/21
 */
@Component
public class AuthenticationManager {

    private static final int DEFAULT_KEY_COUNT = 64;
    private Map<Integer, KeyPair> keyPairs = new ConcurrentHashMap<>(DEFAULT_KEY_COUNT);

    @PostConstruct
    public void init() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            for (int i = 0; i < DEFAULT_KEY_COUNT; i++) {
                KeyPair keyPair = generator.generateKeyPair();
                keyPairs.put(publicKeyHashCode(keyPair.getPublic().getEncoded()), keyPair);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private int publicKeyHashCode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes).hashCode();
    }

    public String getPublicKey() {
        int index = (int) (System.currentTimeMillis() % DEFAULT_KEY_COUNT);
        KeyPair keyPair = keyPairs.get(index);
        byte[] encoded = keyPair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    public String decode(String text, String publicKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(publicKey);
            KeyPair keyPair = keyPairs.get(publicKeyHashCode(bytes));
            if (keyPair == null)
                return "";

            byte[] encoded = Base64.getDecoder().decode(text);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(DECRYPT_MODE, keyPair.getPrivate());
            byte[] decoded = cipher.doFinal(encoded);
            return new String(decoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.generateKeyPair();

        String password = "8nas82";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encrypted = cipher.doFinal(password.getBytes("UTF-8"));
        String token = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("encrypted: " + token);

        Cipher cipher0 = Cipher.getInstance("RSA");
        cipher0.init(DECRYPT_MODE, keyPair.getPrivate());
        byte[] decoded = cipher0.doFinal(encrypted);
        System.out.println(new String(decoded));

//        System.out.println(publicKey);
//        String prk = new String(Base64.decode(privateKey.getEncoded()));
//        String pbk = new String(Base64.decode(publicKey.getEncoded()));
//        System.out.println(prk);
//        System.out.println(pbk);
    }

}
