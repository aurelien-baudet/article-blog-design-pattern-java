package fr.sii.storage.notification;

import fr.sii.storage.exception.NotificationException;


public interface Notification {
	public void send(String message) throws NotificationException;
}
