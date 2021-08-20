package com.example.cicd.core.util;

import static dev.paseto.jpaseto.Pasetos.parserBuilder;
import static dev.paseto.jpaseto.Version.V2;
import static dev.paseto.jpaseto.lang.Keys.keyPairFor;
import static java.security.KeyFactory.getInstance;
import static java.time.Instant.now;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static org.apache.commons.collections4.MapUtils.isEmpty;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Map.Entry;

import dev.paseto.jpaseto.Claims;
import dev.paseto.jpaseto.PasetoParser;
import dev.paseto.jpaseto.PasetoV2PublicBuilder;
import dev.paseto.jpaseto.Pasetos;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PasetoUtils {
	
	private static final String PASETO_ALGORITHM = System.getenv("PASETO_ALGORITHM");
	private static final String PASETO_ISSUER = System.getenv("PASETO_ISSUER");
	private static final String PASETO_AUDIENCE = System.getenv("PASETO_AUDIENCE");
	private static final String PASETO_TOKEN_ID = System.getenv("PASETO_TOKEN_ID");
	private static final String PASETO_KEY_ID = System.getenv("PASETO_KEY_ID");
	private static final String PASETO_PRIVATE_KEY = System.getenv("PASETO_PRIVATE_KEY");
	private static final String PASETO_PUBLIC_KEY = System.getenv("PASETO_PUBLIC_KEY");
	
	private static PrivateKey privateKey;
	private static PublicKey publicKey;
	private static PasetoParser parser;
	
	private PasetoUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	static {
		try {
			KeyFactory keyFactory = getInstance(PASETO_ALGORITHM);
			
			byte[] privateKeyBytes = getDecoder().decode(PASETO_PRIVATE_KEY);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			
			byte[] publicKeyBytes = getDecoder().decode(PASETO_PUBLIC_KEY);
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			publicKey = keyFactory.generatePublic(publicKeySpec);
			
			parser = parserBuilder()
						.setPublicKey(publicKey)
						.requireIssuer(PASETO_ISSUER)
						.requireAudience(PASETO_AUDIENCE)
						.requireTokenId(PASETO_TOKEN_ID)
						.requireKeyId(PASETO_KEY_ID)
						.build();
		} catch (NoSuchAlgorithmException e) {
			log.error("can't find algorithm >>> [{}]", PASETO_ALGORITHM + e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * generate token
	 * 
	 * @param subject user name, user id, account id
	 * @return token
	 */
	public static String compact(String subject) {
		return compact(subject, null);
	}
	
	/**
	 * generate token
	 * 
	 * @param subject user name, user id, account id
	 * @param claims Roles or anything
	 * @return token
	 */
	public static String compact(String subject, Map<String, Object> claims) {
		Instant now = now();
		PasetoV2PublicBuilder builder = Pasetos.V2.PUBLIC.builder()
															.setPrivateKey(privateKey)
															.setIssuer(PASETO_ISSUER)
															.setSubject(subject)
															.setAudience(PASETO_AUDIENCE)
															.setIssuedAt(now)
															.setExpiration(now.plus(15, ChronoUnit.MINUTES))
															.setTokenId(PASETO_TOKEN_ID)
															.setKeyId(PASETO_KEY_ID);
		if (isEmpty(claims)) { return builder.compact();  }
		for (Entry<String, Object> entry : claims.entrySet()) { builder.claim(entry.getKey(), entry.getValue()); }
		return builder.compact();
	}
	
	/**
	 * validate token
	 * 
	 * @param token token
	 * @return true if valid, else return false
	 */
	public static boolean valid(String token) {
		try {
			parser.parse(token);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * retrieve claims
	 * 
	 * @param token token
	 * @return claims object
	 */
	public static Claims getClaims(String token) {
		return parser.parse(token).getClaims();
	}
	
	/**
	 * for develop use, write key to local path
	 * 
	 * @param filePath text path
	 * @throws IOException
	 */
	public static void writerKeyToFile(String filePath) throws IOException {
		KeyPair keyPair = keyPairFor(V2);
		try (FileOutputStream fos = new FileOutputStream(filePath + "PRIVATE_KEY.txt")) {
			byte[] encoded = keyPair.getPrivate().getEncoded();
			fos.write(getEncoder().encode(encoded));
		}
		try (FileOutputStream fos = new FileOutputStream(filePath + "PUBLIC_KEY.txt")) {
			byte[] encoded = keyPair.getPublic().getEncoded();
			fos.write(getEncoder().encode(encoded));
		}
	}
	
}
