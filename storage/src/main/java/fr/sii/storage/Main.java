package fr.sii.storage;

import fr.sii.storage.exception.StoreException;
import fr.sii.storage.listener.NotificationListener;
import fr.sii.storage.notification.SendMail;
import fr.sii.storage.notification.Tweet;
import fr.sii.storage.store.DropBoxStorage;
import fr.sii.storage.store.FileStorage;
import fr.sii.storage.store.MultiStorage;
import fr.sii.storage.store.ObservableStorage;
import fr.sii.storage.store.Storage;

public class Main {

	public static void main(String[] args) throws StoreException {
		Storage storage = new MultiStorage(
								new ObservableStorage(new FileStorage(), new NotificationListener(new SendMail("aurelien.baudet@gmail.com"))),
								new ObservableStorage(new DropBoxStorage("uWvt2xN2BiAAAAAAAAAABJi8rBWLOrRd2Xetfic7KUlzaYkhM6VJ0KOWldnHCljy"), new NotificationListener(new Tweet("zYkWnYgogyrxtlDHZFm3Y5ZEA", "oInBeRdizYOcye7ZsFFHVOZS16pYbuRoUNuYS02tI5VtEdKjus", "442176423-LF3iga3VjQcLoP6tqiP4ZijfaFY54spgMmafpUL7", "HdB4HAObRYGrniaA496pNBkiCT5FVA06iV9H14HSqhOlU"))));
		storage.store("target/test.txt", "hello world");
		System.out.println(storage.read("target/test.txt"));
	}
}
