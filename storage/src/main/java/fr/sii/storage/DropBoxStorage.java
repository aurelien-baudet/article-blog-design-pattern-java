package fr.sii.storage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import fr.sii.storage.exception.StoreException;

public class DropBoxStorage implements Storage {

	private DbxClient client;

	public DropBoxStorage(String dropboxAppKey, String dropboxAppSecret) throws StoreException {
		try {
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
		} catch(DbxException|IOException e) {
			throw new StoreException("Failed to initailize DropBox", e);
		}
	}

	@Override
	public void store(String path, String content) throws StoreException {
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
			client.uploadFile("/"+path, DbxWriteMode.force(), content.length(), inputStream);
		} catch(DbxException|IOException e) {
			throw new StoreException("could not store "+path+" using DropBox storage", e);
		}
	}

	@Override
	public String read(String path) throws StoreException {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			client.getFile("/"+path, null, outputStream);
			return outputStream.toString();
		} catch(DbxException|IOException e) {
			throw new StoreException("could not read "+path+" using DropBox storage", e);
		}
	}

}
