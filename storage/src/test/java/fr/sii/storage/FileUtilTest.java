package fr.sii.storage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import twitter4j.TwitterException;

import com.dropbox.core.DbxException;

public class FileUtilTest {
	@Before
	public void clean() {
		File file = new File("target/test.txt");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void store() throws IOException, DbxException, TwitterException {
		FileUtil.store("target/test.txt", "junit store test");
		List<String> lines = Files.readAllLines(Paths.get("target", "test.txt"), Charset.forName("UTF-8"));
		Assert.assertEquals("should contain 1 line", 1, lines.size());
		Assert.assertEquals("should contain 'junit store test'", "junit store test", lines.get(0));
	}
	
	@Test
	public void read() throws IOException, DbxException {
		Files.write(Paths.get("target", "test.txt"), Arrays.asList("junit read test"), Charset.forName("UTF-8"));
		String content = FileUtil.read("target/test.txt");
		Assert.assertEquals("should contain 'junit read test'", "junit read test", content);
	}
}
