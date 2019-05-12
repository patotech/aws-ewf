package cl.aws.demo.lambda.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtParserToolkit {

	@Autowired
	private CognitoSigningKeyResolver keyResolver;
	
	public Jws<Claims> parseClaims( String jwtToken ) throws JwtException {
		try {
			return Jwts.parser()
				.setSigningKeyResolver( keyResolver )
				.parseClaimsJws( jwtToken );
		} catch (JwtException e) {
			throw e;
		}
	}
}
