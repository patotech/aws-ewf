package cl.aws.demo.lambda.jwt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.gson.Gson;

@Configuration
@Import( { JwtParserToolkit.class } )
public class JwtConfiguration {

	@Value("#{environment.COGNITO_REGION}")
	private String cognitoRegion;
	
	@Value("#{environment.COGNITO_POOL_ID}")
	private String cognitoPoolId;
	
	private static final String URL_PREFIX = "https://cognito-idp.";
	private static final String URL_AMAZONAWS_COM = ".amazonaws.com/";
	private static final String URL_SUFFIX = "/.well-known/jwks.json";
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init() throws Exception {
		// https://github.com/awslabs/aws-support-tools/tree/master/Cognito/decode-verify-jwt
		final String COGNITO_KEYS_URL = new StringBuilder()
				.append( URL_PREFIX )
				.append( cognitoRegion )
				.append( URL_AMAZONAWS_COM )
				.append( cognitoPoolId )
				.append( URL_SUFFIX )
				.toString();
		final String jsonKeys = readUrl( COGNITO_KEYS_URL );
		final Gson gson = new Gson();
		keyStore = gson.fromJson( jsonKeys, KeyStore.class );
	}
	
	@Bean
	public CognitoSigningKeyResolver getKeyResolver() {
		return new CognitoSigningKeyResolver( keyStore );
	}
	
	private String readUrl( String urlString ) throws Exception {
	    BufferedReader reader = null;
	    try {
	        final URL url = new URL(urlString);
	        reader = new BufferedReader( new InputStreamReader(url.openStream() ) );
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
}
