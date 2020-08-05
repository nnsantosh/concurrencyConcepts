package com.test;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockUseCases {

	private static ReentrantLock lock = new ReentrantLock();

	private static void accessResource() {
		try {
			lock.lock();
			// Access the resource
			System.out.println("accessing the resource...");
		} catch (Exception e) {
			// Catch exceptions
		} finally {
			// Good practice to use unlock in finally block so that even in case
			// of any exceptions lock will be released
			// for other threads to be used
			lock.unlock();
		}

	}

	private static void accessResourceIfLockAcquired() {
		boolean acquired = lock.tryLock();
		if (acquired) {
			try {
				// access the resource
				System.out.println("accessing the resource only if lock acquired...");
			} catch (Exception e) {
				// Catch any exception thrown
			} finally {
				// unlock at the end
				lock.unlock();
			}

		} else {
			// Else Do something else
		}
		
	}
	
	private static void accessResourceLockCalledMultipleTimes() {
		lock.lock();
		lock.lock();
		int numberOfTimesLockCalled = lock.getHoldCount();
		System.out.println("numberOfTimesLockCalled: "+numberOfTimesLockCalled);
		lock.unlock();
		lock.unlock();
		
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(() -> accessResource());
		t1.start();
		Thread t2 = new Thread(() -> accessResource());
		t2.start();
		Thread t3 = new Thread(() -> accessResource());
		t3.start();
		Thread t4 = new Thread(() -> accessResource());
		t4.start();
		
		Thread t11 = new Thread(() -> accessResourceIfLockAcquired());
		t11.start();
		
		Thread t22 = new Thread(() -> accessResourceLockCalledMultipleTimes());
		t22.start();
	}

}
