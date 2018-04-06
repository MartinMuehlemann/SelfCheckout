package com.swisshof.selfcheckout.statemachine.generic;

import java.util.Timer;
import java.util.TimerTask;

public abstract class State<OwnerStm extends StatemachineBase, E extends Event<E>> 
{
	protected OwnerStm owner;
	protected Timer timeoutTimer = null;
	
	public State(OwnerStm owner) {
		super();
		this.owner = owner;	
	}
	
	/**
	 * Process event.
	 * 
	 * @param evt		Event.
	 * @return			New state if the state changes, otherwise null.
	 */
	abstract public State<OwnerStm, E> processEvent(Event<E> evt);
	
	
	/**
	 * @brief Entry Action.
	 */
	public void entryAction()
	{
		
	}
	
	/**
	 * @brief Exit Action.
	 */
	public void exitAction()
	{
		stopTimeoutTimer();
	}


	/**
	 * @brief Trigger event after timeout elapses
	 */
	protected synchronized void startTimeout(int timeoutMs, Event<E> triggerEvent)
	{
		if(timeoutTimer != null) {
			// if already a timer is running, stop it
			stopTimeoutTimer();
		}

		timeoutTimer = new Timer();
		
		final Event<E> evt = triggerEvent;
		timeoutTimer.schedule(new TimerTask() {
			
			@Override
			public synchronized void run() {
				stopTimeoutTimer();
				owner.processEvent(evt);
			}
		}, timeoutMs, timeoutMs);
	}
	
	
	public synchronized void stopTimeoutTimer() {
		if (timeoutTimer != null) {
			timeoutTimer.cancel();
			timeoutTimer = null;
		}
	}
	
}
