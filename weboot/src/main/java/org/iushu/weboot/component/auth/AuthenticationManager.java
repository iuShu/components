package org.iushu.weboot.component.auth;

import org.iushu.weboot.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

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

    private static final String TKN_DELIMITER = "&";
    private PublicKey tkPubKey;
    private PrivateKey tkPrvKey;

    @Value("${weboot.token.encrypt.private}")
    private String prvDerPath;

    @Value("${weboot.token.encrypt.public}")
    private String pubDerPath;

    @PostConstruct
    public void init() {
        try {
            byte[] prvBytes = Files.readAllBytes(Paths.get(prvDerPath));
            byte[] pubBytes = Files.readAllBytes(Paths.get(pubDerPath));
            KeySpec pubKeySpec = new X509EncodedKeySpec(pubBytes);
            KeySpec prvKeySpec = new PKCS8EncodedKeySpec(prvBytes);
            tkPubKey = KeyFactory.getInstance("RSA").generatePublic(pubKeySpec);
            tkPrvKey = KeyFactory.getInstance("RSA").generatePrivate(prvKeySpec);

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
            logger.error("init error", e);
        } catch (InvalidKeySpecException | IOException e) {
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

    public String decrypt(String text, String publicKey) {
        byte[] bytes = Base64.getDecoder().decode(publicKey);
        KeyPair keyPair = keyPairs.get(publicKeyHashCode(bytes));
        if (keyPair == null)
            return "";
        return decrypt(text, keyPair.getPrivate());

//            byte[] encoded = Base64.getDecoder().decode(text);
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(DECRYPT_MODE, keyPair.getPrivate());
//            byte[] decoded = cipher.doFinal(encoded);
//            return new String(decoded, StandardCharsets.UTF_8);
    }

    public String encryptToken(User user) {
        String raw = String.format("%s%s%s%s%s%s%s", user.getUserId(), TKN_DELIMITER,
                user.getUsername(), TKN_DELIMITER, user.getPassword(), TKN_DELIMITER, System.currentTimeMillis());
        return encrypt(raw, tkPubKey);
    }

    public User decryptToken(String token) {
        try {
            String text = decrypt(token, tkPrvKey);
            String[] info = text.split(TKN_DELIMITER);
            if (info.length != 4)
                return null;

            short userId = Short.valueOf(info[0]);
            String username = info[1];
            String password = info[2];
            long timestamp = Long.valueOf(info[3]);

            User user = new User();
            user.setUserId(userId);
            user.setUsername(username);
            user.setPassword(password);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    private String encrypt(String content, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return new BASE64Encoder().encode(encrypted).replaceAll("\n", "");
        } catch (Exception e) {
            logger.error("encrypt error", e);
            return "";
        }
    }

    private String decrypt(String text, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(DECRYPT_MODE, privateKey);
            byte[] encrypted = new BASE64Decoder().decodeBuffer(text);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("decrypt error", e);
            return "";
        }
    }

}
