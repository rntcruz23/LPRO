package room;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import users.UserThread;
public abstract class Window implements Lock{
	private boolean locked;
	private boolean roomEmpty;
	public Window() {
		setRoomEmpty(false);
		setLocked(false);
	}
	public abstract void processCommands(String input,UserThread user);
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	@Override
	public void lock() {
		while(isLocked()) Thread.yield();
		setLocked(true);
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {	
		/*Empty Body*/
	}
	@Override
	public boolean tryLock() {
		if(isLocked()) return false;
		lock();
		return true;
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}
	@Override
	public void unlock() {
		setLocked(false);
	}
	public boolean isRoomEmpty() {
		return roomEmpty;
	}
	public void setRoomEmpty(boolean roomEmpty) {
		this.roomEmpty = roomEmpty;
	}
}