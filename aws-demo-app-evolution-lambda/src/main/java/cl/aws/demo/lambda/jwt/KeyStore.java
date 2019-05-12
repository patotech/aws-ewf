package cl.aws.demo.lambda.jwt;

import java.util.List;

public class KeyStore {

	private List<SigningKey> keys;

	public List<SigningKey> getKeys() {
		return keys;
	}

	public void setKeys(List<SigningKey> keys) {
		this.keys = keys;
	}
}
