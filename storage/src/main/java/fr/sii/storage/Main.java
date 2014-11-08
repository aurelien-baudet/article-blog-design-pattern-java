package fr.sii.storage;

import fr.sii.storage.exception.StoreException;
import fr.sii.storage.listener.NotificationListener;
import fr.sii.storage.notification.SendMail;
import fr.sii.storage.notification.Twitter;
import fr.sii.storage.pattern.DropBoxStorage;
import fr.sii.storage.pattern.FileStorage;
import fr.sii.storage.pattern.MultiStorage;
import fr.sii.storage.pattern.ObservableStorage;
import fr.sii.storage.pattern.Storage;

public class Main {

	public static void main(String[] args) throws StoreException {
		Storage storage = new MultiStorage(
								new ObservableStorage(new FileStorage(), new NotificationListener(new SendMail())),
								new ObservableStorage(new DropBoxStorage("uWvt2xN2BiAAAAAAAAAABJi8rBWLOrRd2Xetfic7KUlzaYkhM6VJ0KOWldnHCljy"), new NotificationListener(new Twitter())));
		storage.store("target/test.txt", "hello world");
		System.out.println(storage.read("target/test.txt"));
	}
}
