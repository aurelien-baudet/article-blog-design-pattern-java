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
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class FileUtil {
	private static DbxClient client;

	public static void initDropbox(String dropboxAppKey, String dropboxAppSecret) throws IOException, DbxException {
		DbxAppInfo appInfo = new DbxAppInfo(dropboxAppKey, dropboxAppSecret);

		// initialisation dropbox
		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
				Locale.getDefault().toString());
		DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

		// authentification et autorisation
		String authorizeUrl = webAuth.start();
		System.out.println("1. Go to: " + authorizeUrl);
		System.out.println("2. Click \"Allow\" (you might have to log in first)");
		System.out.println("3. Copy the authorization code.");
		String code = new BufferedReader(new InputStreamReader(System.in))
				.readLine().trim();

		// authentifié
		DbxAuthFinish authFinish = webAuth.finish(code);
		String accessToken = authFinish.accessToken;

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
