package org.cofomo.discovery.error;

public class ProviderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProviderNotFoundException(String id) {
		super("Provider id not found : " + id);
	}

}
