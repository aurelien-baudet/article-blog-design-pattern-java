package fr.sii.storage.store;

import java.util.Arrays;
import java.util.List;

import fr.sii.storage.exception.StoreException;
import fr.sii.storage.listener.StorageListener;

public class ObservableStorage implements Storage {

	private Storage storage;
	
	private List<StorageListener> listeners;

	public ObservableStorage(Storage storage, List<StorageListener> listeners) {
		super();
		this.storage = storage;
		this.listeners = listeners;
	}

	public ObservableStorage(Storage storage, StorageListener... listeners) {
		this(storage, Arrays.asList(listeners));
	}

	@Override
	public void store(String path, String content) throws StoreException {
		storage.store(path, content);
		for(StorageListener listener : listeners) {
			listener.stored(path, content);
		}
	}

	@Override
	public String read(String path) throws StoreException {
		String content = storage.read(path);
		for(StorageListener listener : listeners) {
			listener.read(path, content);
		}
		return content;
	}

}
