package com.test;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockUseCases {

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

	private void readResource() {
		readLock.lock();
		// Read the resource
		readLock.unlock();
	}

	private void writeResource() {
		writeLock.lock();
		// update the resource
		writeLock.unlock();
	}

	public static void main(String[] args) {

		ReentrantReadWriteLockUseCases obj = new ReentrantReadWriteLockUseCases();
		Thread t1 = new Thread(() -> obj.readResource());
		t1.start();
		Thread t2 = new Thread(() -> obj.readResource());
		t2.start();
		Thread t3 = new Thread(() -> obj.writeResource());
		t3.start();
		Thread t4 = new Thread(() -> obj.writeResource());
		t4.start();

	}

}
