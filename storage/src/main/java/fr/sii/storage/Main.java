package fr.sii.storage;

import java.io.IOException;

import com.dropbox.core.DbxException;

public class Main {

	public static void main(String[] args) throws IOException, DbxException {
		FileUtil.initProxy();
		FileUtil.initDropbox("uWvt2xN2BiAAAAAAAAAABJi8rBWLOrRd2Xetfic7KUlzaYkhM6VJ0KOWldnHCljy");
		FileUtil.store("target/test.txt", "hello world");
		System.out.println(FileUtil.read("target/test.txt"));
	}
}
