package fr.sii.storage;

import fr.sii.storage.exception.StoreException;
import fr.sii.storage.store.DropBoxStorage;
import fr.sii.storage.store.FileStorage;
import fr.sii.storage.store.MultiStorage;
import fr.sii.storage.store.Storage;

public class Main {

	public static void main(String[] args) throws StoreException {
		Storage storage = new MultiStorage(new FileStorage(), new DropBoxStorage("uWvt2xN2BiAAAAAAAAAABJi8rBWLOrRd2Xetfic7KUlzaYkhM6VJ0KOWldnHCljy"));
		storage.store("target/test.txt", "hello world");
		System.out.println(storage.read("target/test.txt"));
	}
}
