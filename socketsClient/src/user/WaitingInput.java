package user;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WaitingInput extends Thread implements Lock{
	private User caller;
	private boolean locked;
	public WaitingInput(User waiting) {
		caller = waiting;
		locked = false;
	}
	@Override
	public void run() {
		while(true) {
			System.out.println("Auxiliar receiver thread");
			String readFromSocket = caller.getCommandsFromServer();
			lock();
			System.out.println("New threaded command received");
			caller.processCommands(readFromSocket);
			unlock();
		}
	}
	public User getCaller() {
		return caller;
	}
	public void setCaller(User caller) {
		this.caller = caller;
	}
	@Override
	public void lock() {
		while(locked) yield();
		locked = true;
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		
	}
	@Override
	public Condition newCondition() {
		return null;
	}
	@Override
	public boolean tryLock() {
		if(locked) return false;
		lock();
		return true;
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}
	@Override
	public void unlock() {
		locked = false;
	}
}