package com.swisshof.selfcheckout.statemachine.generic;

public class State<OwnerStm extends StatemachineBase> 
{
	protected OwnerStm owner;

	public State(OwnerStm owner) {
		super();
		this.owner = owner;	
	}
	
	public void entryAction()
	{
		
	}
	
	public void exitAction()
	{
		
	}
	
}
