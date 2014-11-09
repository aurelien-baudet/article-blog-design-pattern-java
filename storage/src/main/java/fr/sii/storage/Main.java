package fr.sii.storage;

import fr.sii.storage.exception.StoreException;
import fr.sii.storage.store.Storage;

public class Main {

	public static void main(String[] args) throws StoreException {
		Storage storage = FactorySelector.getFactory("prod").create();
		storage.store("target/test.txt", "hello world");
		System.out.println(storage.read("target/test.txt"));
	}
}
