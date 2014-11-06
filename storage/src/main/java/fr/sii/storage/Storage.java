package fr.sii.storage;

import fr.sii.storage.exception.StoreException;

public interface Storage {
	public void store(String path, String content) throws StoreException;
	
	public String read(String path) throws StoreException;
}
