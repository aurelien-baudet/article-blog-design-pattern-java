package fr.sii.storage.pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sii.storage.exception.StoreException;

public class FallbackStorage implements Storage {

	private List<Storage> storages;
	
	private Map<String, Storage> map;
	
	public FallbackStorage(List<Storage> storages, Map<String, Storage> map) {
		super();
		this.storages = storages;
		this.map = map;
	}

	public FallbackStorage(Storage... storages) {
		this(Arrays.asList(storages), new HashMap<String, Storage>());
	}

	@Override
	public void store(String path, String content) throws StoreException {
		for(Storage storage : storages) {
			try {
				storage.store(path, content);
				map.put(path, storage);
				return;
			} catch(StoreException e) {
				// on passe pour tester le prochain
			}
		}
		throw new StoreException("Failed to store "+path);
	}

	@Override
	public String read(String path) throws StoreException {
		Storage storage = map.get(path);
		if(storage==null) {
			throw new StoreException("No storage previously used for file "+path);
		}
		return storage.read(path);
	}

}
