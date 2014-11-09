package fr.sii.storage.listener;

import org.apache.log4j.Logger;

import fr.sii.storage.exception.NotificationException;
import fr.sii.storage.notification.Notification;

public class NotificationListener implements StorageListener {
	private static Logger logger = Logger.getLogger(NotificationListener.class);

	private Notification storeNotification;
	
	private Notification readNotification;
	
	public NotificationListener(Notification storeNotification, Notification readNotification) {
		super();
		this.storeNotification = storeNotification;
		this.readNotification = readNotification;
	}

	public NotificationListener(Notification storeNotification) {
		this(storeNotification, null);
	}
	
	@Override
	public void stored(String path, String content) {
		if(storeNotification!=null) {
			try {
				storeNotification.send("file "+path+" has been stored");
			} catch (NotificationException e) {
				logger.error("file "+path+" has been stored but notification has failed", e);
			}
		}
	}

	@Override
	public void read(String path, String content) {
		if(readNotification!=null) {
			try {
				readNotification.send("file "+path+" has been read");
			} catch (NotificationException e) {
				logger.error("file "+path+" has been read but notification has failed", e);
			}
		}
	}

}
