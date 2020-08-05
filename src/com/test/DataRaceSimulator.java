package com.test;

public class DataRaceSimulator {
	
	private static int x = 0;

	
	private static Runnable task1 = new Runnable(){

		@Override
		public void run() {
			x = 3;
			
		}
		
	};
	
	private static Runnable task2 = new Runnable(){

		@Override
		public void run() {
			x =7;
			
		}
		
	};
	
	public static void main(String[] args){
		
		
		Thread t1 = new Thread(task1);
		t1.start();
		
		Thread t2 = new Thread(task2);
		t2.start();
		
		System.out.println("x: "+x);
		
		
	}
}
