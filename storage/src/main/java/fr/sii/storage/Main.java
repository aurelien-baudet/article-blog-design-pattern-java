package fr.sii.storage;

import java.io.IOException;

import com.dropbox.core.DbxException;

public class Main {

	public static void main(String[] args) throws IOException, DbxException {
		FileUtil.initDropbox("kycwqj1fstcn3vg", "kzmiubavm3ttner");
		FileUtil.store("target/test.txt", "hello world");
		System.out.println(FileUtil.read("target/test.txt"));
	}
}
