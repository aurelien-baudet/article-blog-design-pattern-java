package fr.sii.storage;

import java.util.Arrays;
import java.util.List;

import fr.sii.storage.exception.StoreException;

public class MultiStorage implements Storage {

	private List<Storage> storages;
	
	public MultiStorage(List<Storage> storages) {
		super();
		this.storages = storages;
	}

	public MultiStorage(Storage... storages) {
		this(Arrays.asList(storages));
	}

	@Override
	public void store(String path, String content) throws StoreException {
		boolean stored = false;
		for(Storage storage : storages) {
			try {
				storage.store(path, content);
				stored = true;
			} catch(StoreException e) {
				// on passe pour tester le prochain
			}
		}
		if(!stored) {
			throw new StoreException("Failed to store "+path);
		}
	}

	@Override
	public String read(String path) throws StoreException {
		for(Storage storage : storages) {
			try {
				return storage.read(path);
			} catch(StoreException e) {
				// on passe pour tester le prochain
			}
		}
		throw new StoreException("Failed to read "+path);
	}

}
