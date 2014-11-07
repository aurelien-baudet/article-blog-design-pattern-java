package fr.sii.storage;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.http.StandardHttpRequestor;

public class FileUtil {
	private static DbxClient client;

	private static Proxy proxy;

	public static void initProxy() {
		if(System.getProperty("http.proxyHost")!=null) {
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.valueOf(System.getProperty("http.proxyPort"))));
		} else {
			proxy = Proxy.NO_PROXY;
		}
	}
	
	public static void initDropbox(String accessToken) throws IOException, DbxException {
		// initialisation dropbox
		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString(), new StandardHttpRequestor(proxy));

		client = new DbxClient(config, accessToken);
	}
	
	public static void store(String path, String content) throws IOException, DbxException {
		File file = new File(path);
		file.createNewFile();
		// try-with-resource (Java 7) : ferme automatiquement le stream
		try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
			stream.write(content.getBytes());
			stream.flush();
		} catch(Throwable e) {
			// on passe  l'erreur pour stocker avec DropBox
		}
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
			client.uploadFile("/"+path, DbxWriteMode.force(), content.length(), inputStream);
		}
	}
	
	public static String read(String path) throws FileNotFoundException, IOException, DbxException {
		File file = new File(path);
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			StringBuilder sb = new StringBuilder();
			while(line!=null) {
				sb.append(line);
				line = reader.readLine();
			}
			return sb.toString();
		} catch(Throwable e) {
			// on passe l'erreur pour tenter de récupérer le fichier via DropBox
		}
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			client.getFile("/"+path, null, outputStream);
			return outputStream.toString();
		}
	}
}
