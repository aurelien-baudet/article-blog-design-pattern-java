package fr.sii.storage.pattern;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import fr.sii.storage.exception.StoreException;

public class FileStorage implements Storage {

	@Override
	public void store(String path, String content) throws StoreException {
		File file = new File(path);
		try {
			file.createNewFile();
			// try-with-resource (Java 7) : ferme automatiquement le stream
			try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
				stream.write(content.getBytes());
				stream.flush();
			}
		} catch(IOException e) {
			throw new StoreException("could not store "+path+" using file storage", e);
		}
	}

	@Override
	public String read(String path) throws StoreException {
		File file = new File(path);
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			StringBuilder sb = new StringBuilder();
			while(line!=null) {
				sb.append(line);
				line = reader.readLine();
			}
			return sb.toString();
		} catch(IOException e) {
			throw new StoreException("could not read "+path+" using file storage", e);
		}
	}

}
