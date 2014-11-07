package fr.sii.storage.util;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyUtil {

	public static Proxy getProxy() {
		if(System.getProperty("http.proxyHost")!=null) {
			return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.valueOf(System.getProperty("http.proxyPort"))));
		} else {
			return Proxy.NO_PROXY;
		}
	}
	
}
