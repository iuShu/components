package org.iushu.weboot.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.bouncycastle.util.encoders.Base64;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static org.iushu.weboot.auth.RSAPemFileLoader.loadPrivateKeyFromFile;
import static org.iushu.weboot.auth.RSAPemFileLoader.loadPublicKeyFromFile;

/**
 * Generate RSA public/private PEM file
 * private: openssl genrsa -des3 -out [private].pem 2048  (-des3 add password)
 *          [input private key]
 * public: openssl rsa -in [private].pem -outform PEM -pubout -out [public].pem
 *         [input private key]
 *
 * @author iuShu
 * @since 6/28/21
 */
public class Authentication {

    private static final String RSA_PRIVATE_PATH = "src/main/resources/cert/weboot-private.pem";
    private static final String RSA_PUBLIC_PATH = "src/main/resources/cert/weboot-public.pem";

    public static void main(String[] args) {
        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPrivateKey privateKey = (RSAPrivateKey) loadPrivateKeyFromFile(RSA_PRIVATE_PATH);
        RSAPublicKey publicKey = (RSAPublicKey) loadPublicKeyFromFile(RSA_PUBLIC_PATH);
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        String token = JWT.create()
                .withIssuer("weboot628")
                .withIssuedAt(new Date())
                .withClaim("user", 1597607369)
                .sign(algorithm);
        System.out.println(token);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("weboot628")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String header = new String(Base64.decode(decodedJWT.getHeader()));
        String payload = new String(Base64.decode(decodedJWT.getPayload()));
        System.out.println(header);
        System.out.println(payload);
        System.out.println(decodedJWT.getSignature());
    }

}
