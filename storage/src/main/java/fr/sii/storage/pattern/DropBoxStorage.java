package fr.sii.storage.pattern;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.http.StandardHttpRequestor;

import fr.sii.storage.exception.StoreException;
import fr.sii.storage.util.ProxyUtil;

public class DropBoxStorage implements Storage {

	private DbxClient client;

	public DropBoxStorage(String accessToken) {
		// initialisation dropbox
		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString(), new StandardHttpRequestor(ProxyUtil.getProxy()));

		client = new DbxClient(config, accessToken);
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
