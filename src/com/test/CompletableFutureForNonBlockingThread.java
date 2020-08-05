package com.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class CompletableFutureForNonBlockingThread {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// blockingThreadExample();
		nonBlockingThredExample();
	}

	public static void blockingThreadExample() {

		ExecutorService service = Executors.newFixedThreadPool(5);
		Future<Integer> future = service.submit(new Task());
		try {
			// Whenever future.get is done the thread is blocked
			// In order to use non blocking threads CompletableFuture can be
			// used
			Integer result = future.get();
			System.out.println("Result from the task is: " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		Future<Integer> future2 = service.submit(new Task());
		Future<Integer> future3 = service.submit(new Task());
		Future<Integer> future4 = service.submit(new Task());
		Future<Integer> future5 = service.submit(new Task());

		// Notice here that the below task will get executed from one of the
		// threads which is free from the same thread pool
		Future<Integer> future6 = service.submit(new Task());

		service.shutdown();
	}

	static class Task implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			// Do something and then return an Integer
			System.out.println("Task got called! " + Thread.currentThread());
			return new Random().nextInt();
		}

	}

	public static void nonBlockingThredExample() throws InterruptedException, ExecutionException {

		// Now let us say we have to execute some sequence of tasks in order
		// But we want multiple such sequences to be executed by multiple
		// threads
		for (int i = 0; i < 10; i++) {

			CompletableFuture<Order> futureOrder = CompletableFuture.supplyAsync((Supplier<Order>) () -> {
				return new Order();
			}).thenApply(order -> {
				order.orderAddress = "some address";
				System.out.println("thread details: "+Thread.currentThread());
				return order;
			}).thenApply(order -> {
				order.orderStatus = "processed";
				System.out.println("thread details: "+Thread.currentThread());
				return order;
			});
			//This will be non blocking
			System.out.println("futureOrder: " + futureOrder.get());
		}

		System.out.println("completed 10 loops");

		// We can also use thenApplyAsync if we want to run our tasks in
		// different thread pool
		// Internally for all other tasks there is one forkJoinPool which is
		// being used.
		ExecutorService ioBoundThreadPool = Executors.newFixedThreadPool(5);
		ExecutorService cpuBoundThredPool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
			
			CompletableFuture<Order> futureOrder = CompletableFuture.supplyAsync((Supplier<Order>) () -> {
					return new Order();
				}).thenApplyAsync(order -> {
					order.orderAddress = "some address";
					System.out.println("thread details: "+Thread.currentThread());
					return order;
				},ioBoundThreadPool).thenApplyAsync(order -> {
					order.orderStatus = "processed";
					System.out.println("thread details: "+Thread.currentThread());
					return order;
				},cpuBoundThredPool);
				System.out.println("futureOrder: " + futureOrder.get());
			}

		

	}

}
