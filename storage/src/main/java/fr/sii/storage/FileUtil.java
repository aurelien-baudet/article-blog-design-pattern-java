package fr.sii.storage;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	public static void write(String path, String content) throws IOException {
		File file = new File(path);
		file.createNewFile();
		// try-with-resource (Java 7) : ferme automatiquement le stream
		try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
			stream.write(content.getBytes());
			stream.flush();
		}
	}
	
	public static String read(String path) throws FileNotFoundException, IOException {
		File file = new File(path);
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			StringBuilder sb = new StringBuilder();
			while(line!=null) {
				sb.append(line);
				line = reader.readLine();
			}
			return sb.toString();
		}
	}
}
