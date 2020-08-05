package com.test;

public class RaceConditionSimulator {

	
	private static int count = 0;
	
	private static Runnable task1 = new Runnable(){

		@Override
		public void run() {
			count++;
			System.out.println("count value in task: "+count);
		}
		
	};
	
	public static void main(String[] args){
		Thread t1 = new Thread(task1);
		t1.start();
		
		Thread t2 = new Thread(task1);
		t2.start();
		
		System.out.println("RaceConditionSimulator.main() count: "+count);
		
	}
	
	
}
