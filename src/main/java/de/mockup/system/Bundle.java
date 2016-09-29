package de.mockup.system;

import java.util.HashMap;
import java.util.Map;

/**
 * Offers Services to the UI
 */
public class Bundle {

	private static Map<Class<?>, Object> serviceRegistry = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <S> S getService(Class<S> clazz) {
		return (S) serviceRegistry.get(clazz);
	}

	public static <X> void registerService(Class<X> service, X serviceImpl) {
		serviceRegistry.put(service, serviceImpl);
	}

}
