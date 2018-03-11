package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class Idle extends State<MainStm>
{

	public Idle(MainStm stateMachine) {
		super(stateMachine);

	}

	@Override
	public String toString() {
		return "IDLE";
	}

	
	@Override
	public State<MainStm> processEvent(Event evt) {

		if (evt == MainStm.Events.AMOUNT_CHANGED) {
			if(owner.context.getCurrentAmount() > 0.0) {
				return(owner.states.enteringAmount);
			}
		}
		
		return null;
	}
	
}
