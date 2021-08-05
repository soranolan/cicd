package com.example.cicd.core.util;

import java.io.FileNotFoundException;
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
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;

import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.PasetoParser;
import dev.paseto.jpaseto.PasetoV2PublicBuilder;
import dev.paseto.jpaseto.Pasetos;
import dev.paseto.jpaseto.Version;
import dev.paseto.jpaseto.lang.Keys;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PasetoUtils {
	
	private static String PASETO_ALGORITHM = System.getenv("PASETO_ALGORITHM");
	private static String PASETO_ISSUER = System.getenv("PASETO_ISSUER");
	private static String PASETO_AUDIENCE = System.getenv("PASETO_AUDIENCE");
	private static String PASETO_TOKEN_ID = System.getenv("PASETO_AUDIENCE");
	private static String PASETO_KEY_ID = System.getenv("PASETO_KEY_ID");
	private static String PASETO_PRIVATE_KEY = System.getenv("PASETO_PRIVATE_KEY");
	private static String PASETO_PUBLIC_KEY = System.getenv("PASETO_PUBLIC_KEY");
	
	private static PrivateKey privateKey;
	private static PublicKey publicKey;
	private static PasetoParser parser;
	
	private PasetoUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	static {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(PASETO_ALGORITHM);
			
			byte[] privateKeyBytes = Base64.getDecoder().decode(PASETO_PRIVATE_KEY);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			
			byte[] publicKeyBytes = Base64.getDecoder().decode(PASETO_PUBLIC_KEY);
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			publicKey = keyFactory.generatePublic(publicKeySpec);
			
			parser = Pasetos.parserBuilder()
							.setPublicKey(publicKey)
							.requireIssuer(PASETO_ISSUER)
							.requireAudience(PASETO_AUDIENCE)
							.requireTokenId(PASETO_TOKEN_ID)
							.requireKeyId(PASETO_KEY_ID)
							.build();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static String compact(String subject) {
		return compact(subject, null);
	}
	
	public static String compact(String subject, Map<String, Object> claims) {
		Instant now = Instant.now();
		PasetoV2PublicBuilder builder = Pasetos.V2.PUBLIC.builder()
															.setPrivateKey(privateKey)
															.setIssuer(PASETO_ISSUER)
															.setSubject(subject)
															.setAudience(PASETO_AUDIENCE)
															.setIssuedAt(now)
															.setExpiration(now.plus(1, ChronoUnit.MINUTES))
															.setTokenId(PASETO_TOKEN_ID)
															.setKeyId(PASETO_KEY_ID);
		if (MapUtils.isEmpty(claims)) { return builder.compact();  }
		for (Entry<String, Object> entry : claims.entrySet()) { builder.claim(entry.getKey(), entry.getValue()); }
		return builder.compact();
	}
	
	public static boolean valid(String token) {
		try {
			parser.parse(token);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}
	
	public static Paseto parse(String token) {
		return parser.parse(token);
	}
	
	public static void writerKeyToFile() throws FileNotFoundException, IOException {
		String filePath = "C:\\Users\\User\\Desktop\\";
		KeyPair keyPair = Keys.keyPairFor(Version.V2);
		try (FileOutputStream fos = new FileOutputStream(filePath + "PRIVATE_KEY.txt")) {
			byte[] encoded = keyPair.getPrivate().getEncoded();
			fos.write(Base64.getEncoder().encode(encoded));
		}
		try (FileOutputStream fos = new FileOutputStream(filePath + "PUBLIC_KEY.txt")) {
			byte[] encoded = keyPair.getPublic().getEncoded();
			fos.write(Base64.getEncoder().encode(encoded));
		}
	}
	
}
