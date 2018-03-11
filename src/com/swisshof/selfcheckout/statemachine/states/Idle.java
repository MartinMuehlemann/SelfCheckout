package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class Idle extends State<MainStm>
{

	public Idle(MainStm stateMachine) {
		super(stateMachine);

	}

	@Override
	public String toString() {
		return "ENTERING_AMOUNT";
	}
	
	public void amountChanged()
	{
		owner.setNewState(owner.states.enteringAmount);
	}
}
