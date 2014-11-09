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
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.http.StandardHttpRequestor;

public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	private static DbxClient client;
	
	private static Twitter twitter;

	private static Proxy proxy;

	private static String email;

	public static void initProxy() {
		if(System.getProperty("http.proxyHost")!=null) {
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.valueOf(System.getProperty("http.proxyPort"))));
		} else {
			proxy = Proxy.NO_PROXY;
		}
	}
	
	public static void initDropbox(String accessToken) throws IOException, DbxException {
		// initialisation dropbox
		DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString(), new StandardHttpRequestor(proxy));

		client = new DbxClient(config, accessToken);
	}
	
	public static void initTwitter(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        TwitterFactory twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	}
	
	public static void initMail(String email) {
		FileUtil.email = email;
	}
	
	public static void store(String path, String content) throws IOException, DbxException, TwitterException {
		File file = new File(path);
		file.createNewFile();
		// try-with-resource (Java 7) : ferme automatiquement le stream
		try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
			stream.write(content.getBytes());
			stream.flush();
			// prepare message
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", "localhost");
			Session session = Session.getDefaultInstance(properties);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("test@sii.fr"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("File "+path+" sucessfully stored");
			message.setText("File "+path+" sucessfully stored");
			// Send message
			Transport.send(message);
		} catch (Throwable e) {
			// on passe l'erreur pour stocker avec DropBox
			logger.error("failed to send email", e);
		}
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes())) {
			client.uploadFile("/" + path, DbxWriteMode.force(), content.length(), inputStream);
	        StatusUpdate statusUpdate = new StatusUpdate("File available on DropBox "+path);
	        twitter.updateStatus(statusUpdate);
		}
	}

	public static String read(String path) throws FileNotFoundException, IOException, DbxException {
		File file = new File(path);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			return sb.toString();
		} catch (Throwable e) {
			// on passe l'erreur pour tenter de récupérer le fichier via DropBox
			logger.info("failed to read file", e);
		}
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			client.getFile("/" + path, null, outputStream);
			return outputStream.toString();
		}
	}
}
