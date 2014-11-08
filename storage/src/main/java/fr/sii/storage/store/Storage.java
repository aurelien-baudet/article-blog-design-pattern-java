package fr.sii.storage.store;

import fr.sii.storage.exception.StoreException;

public interface Storage {
	public void store(String path, String content) throws StoreException;
	
	public String read(String path) throws StoreException;
}
