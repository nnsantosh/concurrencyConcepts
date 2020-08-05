# Avoiding Deadlocks

1. Deadlocks occur when a thread is waiting for a lock held by other thread and vice versa.

2. Difficult to detect due to multiple lock types and thread sources.

3. Detect at runtime using thread dumps
	kill -3 <processId>
	jstack <processId> > ./out.txt
	
4. Consistent ordering of lock acquisition helps avoid deadlock.

5. Using timeouts for lock acquisition can also help
	Example: Lock lock = new ReentrantLock();
			boolean acquired = lock.tryLock(2, TimeUnit.SECONDS);
			
# Reeentrant locks

1. Similar to using synchronized block so that only one thread can acquire the lock at a time
	Locks are explicit
	Locks allow locking/unlocking in any scopes and in any order
	Ability to tryLock and tryLock(timeout)
	
2. It is called Reentrant lock because same lock can be used multiple times
	Basically Reentrant lock allows you to call the lock method multiple times
	The number of unlock method calls should be equal to the number of lock method calls
	
3. ReentrantLock lock = new ReentrantLock(true)
	the boolean true is fairness indicator.
	So this ensures that the lock will be acquired by threads in order or the thread which has been waiting for the lock 	for the longest time will get the lock.

4. lock.getQueueLength will give the count of threads currently waiting for this lock.

5. There is also ReentrantReadWriteLock which has the following functionality:
	Use ReentrantReadWriteLock.readLock() if you want locks that can be acquired by multiple threads to access a shared 	resource. In this case the threads should not modify the resource and only read the resource.
	Use ReentrantReadWriteLock.writeLock() if you want locks that can be acquired by only one thread at a time to access a 	shared 	resource.This is ideal for modifying the shared resource.

# Semaphore: The permit machine

Semaphore is like a machine that holds fixed number of permits for the threads at a time.
So the number of threads that can execute at a time will be equal to the number of permits the Semaphore holds. 
Only after a thread has released the permit back to the Semaphore can the next thread acquire that permit from Semaphore.
This is useful to control the number of threads that can simultaneously access a resource.

# Data Race

Happens when multiple threads access a shared variable at same time without synchronization. At least one thread is writing to the variable.

# Race Condition

Multiple threads access shared variable. Value of shared variable depends on execution order of threads.





