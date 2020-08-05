package com.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockSimulator {

	
	public static void main(String[] args){
		
		Lock lockA = new ReentrantLock();
		Lock lockB = new ReentrantLock();
		
		
		Thread t1 = new Thread(() -> {
			System.out.println("Started Thread t1");
			//Acquired lock lockA
			lockA.lock();
			System.out.println("Thread t1 acquired lockA");
			System.out.println("Thread t1 doing something...");
			//Waiting for lock lockB to be release by thread t2
			lockB.lock();
			System.out.println("Thread t1 acquired lockB");
			lockA.unlock();
			lockB.unlock();
			System.out.println("Thread t1 released all locks");
		});
		Thread t2 = new Thread(() -> {
			System.out.println("Started Thread t2");
			//Acquired lock lockB
			lockB.lock();
			System.out.println("Thread t2 acquired lockB");
			System.out.println("Thread t2 doing something...");
			//Waiting for lock lockA to be released by thread t1
			lockA.lock();
			System.out.println("Thread t2 acquired lockA");
			lockA.unlock();
			lockB.unlock();
			System.out.println("Thread t2 released all locks");
		});
		
		//Start both threads
		t1.start();
		t2.start();
		
		//Notice that the program never terminates and there is a deadlock
		
	}
}
