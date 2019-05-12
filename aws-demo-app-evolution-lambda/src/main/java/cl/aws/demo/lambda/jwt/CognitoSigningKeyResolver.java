package cl.aws.demo.lambda.jwt;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;

public class CognitoSigningKeyResolver implements SigningKeyResolver {
	
	private static final String RSA = "RSA";

	private final KeyStore keyStore;
	
	protected CognitoSigningKeyResolver( KeyStore keyStore ) {
		this.keyStore = keyStore;
	}
	
	@Override
	public Key resolveSigningKey(JwsHeader header, Claims claims) {
		return getKeyForId( header.getKeyId() );
	}

	@Override
	public Key resolveSigningKey(JwsHeader header, String plaintext) {
		return getKeyForId( header.getKeyId() );
	}
	
	// https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-tokens-verifying-a-jwt.html
	private Key getKeyForId( String keyId ) {
		SigningKey signingKey = null;
		for( SigningKey sKey : keyStore.getKeys() ) {
			if( keyId.equals( sKey.getKid() ) ) {
				signingKey = sKey;
				break;
			}
		}
		
		final BigInteger expInt = Encoding.base64urlDecodeUint( signingKey.getE() );
		final BigInteger mInt = Encoding.base64urlDecodeUint( signingKey.getN() );
		
		if( RSA.equals( signingKey.getKty() ) ) {
			RSAPublicKeySpec publicSpec = new RSAPublicKeySpec( mInt, expInt );
			KeyFactory factory;
			try {
				factory = KeyFactory.getInstance( RSA );
				return factory.generatePublic(publicSpec);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("JWT Algoritm not implemented in this lambda -> " + signingKey.getKty() );
			return null;
		}
	}

}
