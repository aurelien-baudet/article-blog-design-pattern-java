package fr.sii.storage;

import fr.sii.storage.factory.DropboxTweetFactory;
import fr.sii.storage.factory.FileFactory;
import fr.sii.storage.factory.FileMailAndDropboxTweetFactory;
import fr.sii.storage.factory.StorageFactory;

public class FactorySelector {
	public static enum Context {
		PUBLIC,
		PRIVATE,
		PUBLIC_REDUNDANCY
	}

	public static StorageFactory getFactory(Context context) {
		switch(context) {
			case PRIVATE:
				return new FileFactory();
			case PUBLIC:
				return new DropboxTweetFactory();
			case PUBLIC_REDUNDANCY:
			default:
				return new FileMailAndDropboxTweetFactory();
		}
	}
}
