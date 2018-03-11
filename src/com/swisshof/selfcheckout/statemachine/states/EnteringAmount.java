package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class EnteringAmount extends State<MainStm>
{

	public EnteringAmount(MainStm stateMachine) {
		super(stateMachine);

	}

	@Override
	public String toString() {
		return "ENTERING_AMOUNT";
	}
	
	
	
}
