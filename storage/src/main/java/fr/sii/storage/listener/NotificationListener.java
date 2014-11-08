package fr.sii.storage.listener;

import fr.sii.storage.notification.Notification;

public class NotificationListener implements StorageListener {

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
			storeNotification.send("file "+path+" has been stored");
		}
	}

	@Override
	public void read(String path, String content) {
		if(readNotification!=null) {
			readNotification.send("file "+path+" has been stored");
		}
	}

}
