package fr.sii.storage.factory;

import fr.sii.storage.listener.NotificationListener;
import fr.sii.storage.notification.Tweet;
import fr.sii.storage.store.DropBoxStorage;
import fr.sii.storage.store.ObservableStorage;
import fr.sii.storage.store.Storage;

public class DropboxTweetFactory implements StorageFactory {

	@Override
	public Storage create() {
		return new ObservableStorage(
					new DropBoxStorage("uWvt2xN2BiAAAAAAAAAABJi8rBWLOrRd2Xetfic7KUlzaYkhM6VJ0KOWldnHCljy"), 
					new NotificationListener(new Tweet("zYkWnYgogyrxtlDHZFm3Y5ZEA", "oInBeRdizYOcye7ZsFFHVOZS16pYbuRoUNuYS02tI5VtEdKjus", "442176423-LF3iga3VjQcLoP6tqiP4ZijfaFY54spgMmafpUL7", "HdB4HAObRYGrniaA496pNBkiCT5FVA06iV9H14HSqhOlU")));
	}

}
