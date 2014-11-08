package fr.sii.storage.listener;

public interface StorageListener {
	public void stored(String path, String content);
	
	public void read(String path, String content);
}
