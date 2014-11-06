package fr.sii.storage;

import fr.sii.storage.exception.StoreException;

public class Main {

	public static void main(String[] args) throws StoreException {
		Storage storage = new MultiStorage(new FileStorage(), new DropBoxStorage("kycwqj1fstcn3vg", "kzmiubavm3ttner"));
		storage.store("target/test.txt", "hello world");
		System.out.println(storage.read("target/test.txt"));
	}
}
