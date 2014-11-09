package fr.sii.storage;

import fr.sii.storage.factory.FileMailAndDropboxTweetFactory;
import fr.sii.storage.factory.StorageFactory;

public class FactorySelector {

	public static StorageFactory getFactory(String context) {
		return new FileMailAndDropboxTweetFactory();
	}
}
