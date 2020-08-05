package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaPhoreUsage {
	
	private static Semaphore semaphore = new Semaphore(3);
	
	public static void main(String[] args) throws InterruptedException{
		
		ExecutorService service = Executors.newFixedThreadPool(50);
		IntStream.range(0, 1000).forEach(i -> service.execute(new Task(semaphore)));
		service.shutdown();
		service.awaitTermination(1, TimeUnit.MINUTES);
	}
	
	
	//So the below task can be executed by only 3 threads at a time because of the semaphore.
	static class Task implements Runnable{

		public Task(Semaphore semaphore) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				//Do some processing logic here
				System.out.println("task is being run: "+Thread.currentThread());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				semaphore.release();
			}
			
		}
		
	}
}
