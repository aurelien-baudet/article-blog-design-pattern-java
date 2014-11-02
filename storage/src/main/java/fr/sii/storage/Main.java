package fr.sii.storage;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		FileUtil.store("target/test.txt", "hello world");
		System.out.println(FileUtil.read("target/test.txt"));
	}
}
