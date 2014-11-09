package fr.sii.storage.factory;

import fr.sii.storage.store.FileStorage;
import fr.sii.storage.store.Storage;

public class FileFactory implements StorageFactory {

	@Override
	public Storage create() {
		return new FileStorage();
	}

}
