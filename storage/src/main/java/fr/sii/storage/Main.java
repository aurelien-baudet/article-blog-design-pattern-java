package fr.sii.storage;

import java.io.IOException;

import twitter4j.TwitterException;

import com.dropbox.core.DbxException;

public class Main {

	public static void main(String[] args) throws IOException, DbxException, TwitterException {
		FileUtil.initProxy();
		FileUtil.initDropbox("uWvt2xN2BiAAAAAAAAAABJi8rBWLOrRd2Xetfic7KUlzaYkhM6VJ0KOWldnHCljy");
		FileUtil.initTwitter("zYkWnYgogyrxtlDHZFm3Y5ZEA", "oInBeRdizYOcye7ZsFFHVOZS16pYbuRoUNuYS02tI5VtEdKjus", "442176423-LF3iga3VjQcLoP6tqiP4ZijfaFY54spgMmafpUL7", "HdB4HAObRYGrniaA496pNBkiCT5FVA06iV9H14HSqhOlU");
		FileUtil.initMail("client@sii.fr");
		FileUtil.store("target/test.txt", "hello world");
		System.out.println(FileUtil.read("target/test.txt"));
	}
}
