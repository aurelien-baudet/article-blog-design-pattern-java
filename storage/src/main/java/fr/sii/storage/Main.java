package fr.sii.storage;

import fr.sii.storage.FactorySelector.Context;
import fr.sii.storage.exception.StoreException;
import fr.sii.storage.store.Storage;

public class Main {

	public static void main(String[] args) throws StoreException {
		Storage storage = FactorySelector.getFactory(Context.PUBLIC_REDUNDANCY).create();
		storage.store("target/test.txt", "hello world");
		System.out.println(storage.read("target/test.txt"));
	}
}
