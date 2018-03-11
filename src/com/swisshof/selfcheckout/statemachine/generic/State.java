package com.swisshof.selfcheckout.statemachine.generic;

public abstract class State<OwnerStm extends StatemachineBase> 
{
	protected OwnerStm owner;

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
	abstract public State<OwnerStm> processEvent(Event evt);
	
	
	public void entryAction()
	{
		
	}
	
	public void exitAction()
	{
		
	}
	
}
