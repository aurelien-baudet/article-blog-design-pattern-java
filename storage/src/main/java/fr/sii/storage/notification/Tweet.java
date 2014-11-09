package fr.sii.storage.notification;

import fr.sii.storage.exception.NotificationException;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class Tweet implements Notification {

	private Twitter twitter;

	public Tweet(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        TwitterFactory twitterFactory = new TwitterFactory();
        twitter = twitterFactory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
	}
	
	@Override
	public void send(String message) throws NotificationException {
        StatusUpdate statusUpdate = new StatusUpdate(message);
        try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			throw new NotificationException("failed to tweet", e);
		}
	}

}
