package org.iushu.weboot.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * @author iuShu
 * @since 6/28/21
 */
@Component
public class AuthenticationManager {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationManager.class);

    private static final int DEFAULT_KEY_COUNT = 2;
    private Map<Integer, KeyPair> keyPairs;
    private List<Integer> keys;

    @PostConstruct
    public void init() {
        try {
            Map<Integer, KeyPair> map = new HashMap<>(DEFAULT_KEY_COUNT);
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

            logger.debug("start generating RSA key pairs");
            long start = System.currentTimeMillis();
            for (int i = 0; i < DEFAULT_KEY_COUNT; i++) {
                KeyPair keyPair = generator.generateKeyPair();
                map.put(publicKeyHashCode(keyPair.getPublic().getEncoded()), keyPair);
            }
            logger.debug(String.format("generated RSA key pairs at %s ms", System.currentTimeMillis() - start));

            keyPairs = Collections.unmodifiableMap(map);
            keys = Collections.unmodifiableList(new ArrayList<>(keyPairs.keySet()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private int publicKeyHashCode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes).hashCode();
    }

    public String selectPublicKey() {
        int index = (int) (System.currentTimeMillis() % DEFAULT_KEY_COUNT);
        KeyPair keyPair = keyPairs.get(keys.get(index));
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

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] pbk = Base64.getEncoder().encode(publicKey.getEncoded());
        byte[] prk = Base64.getEncoder().encode(privateKey.getEncoded());
        System.out.println(new String(pbk));
        System.out.println(new String(prk));

        String pwd = "921732md";
        Cipher cipher0 = Cipher.getInstance("RSA");
        cipher0.init(ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher0.doFinal(pwd.getBytes());
        byte[] encoded = Base64.getEncoder().encode(encrypted);
        System.out.println(new String(encoded));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println(new String(decrypted));

    }

}
