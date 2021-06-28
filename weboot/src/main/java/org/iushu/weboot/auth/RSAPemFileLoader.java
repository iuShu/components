package org.iushu.weboot.auth;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.util.StringUtils;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author iuShu
 * @since 6/28/21
 */
public class RSAPemFileLoader {

    private static final String BEGIN = "-----BEGIN";
    private static final String END = "-----END";

    private static byte[] parsePemFile(String path) throws IOException {
        File file = new File(path);
        if (!file.isFile() || !file.exists())
            throw new FileNotFoundException(String.format("The file '%s' doesn't exist.", file.getAbsolutePath()));

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        if (line == null || !line.startsWith(BEGIN))
            throw new IllegalStateException("Wrong content format at file: " + file.getAbsolutePath());

        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            if (line.contains(END))
                break;
            buffer.append(line.trim());
        }

        reader.close();
        return Base64.decode(buffer.toString());
    }

    private static byte[] parsePemFile(File pemFile) throws IOException {
        if (!pemFile.isFile() || !pemFile.exists())
            throw new FileNotFoundException(String.format("The file '%s' doesn't exist.", pemFile.getAbsolutePath()));

        PemReader reader = new PemReader(new FileReader(pemFile));
        PemObject pemObject = reader.readPemObject();
        byte[] content = pemObject.getContent();
        reader.close();
        return content;
    }

    private static PublicKey getPublicKey(byte[] keyBytes) {
        PublicKey publicKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the public key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the public key");
        }

        return publicKey;
    }

    private static PrivateKey getPrivateKey(byte[] keyBytes) {
        PrivateKey privateKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return privateKey;
    }

    public static PublicKey loadPublicKeyFromFile(String filepath) {
        try {
            byte[] bytes = parsePemFile(new File(filepath));
            return getPublicKey(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PrivateKey loadPrivateKeyFromFile(String filepath) {
        try {
            byte[] bytes = parsePemFile(new File(filepath));
            return getPrivateKey(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
