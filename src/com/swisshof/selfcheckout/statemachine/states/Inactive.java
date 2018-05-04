package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class Inactive extends State<MainStm, MainStm.Events>
{

	public Inactive(MainStm stateMachine) {
		super(stateMachine);

	}


	@Override
	public String toString() {
		return "INACTIVE";
	}

	
	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {

		
		switch (evt.getEvent()) {
			case WAKEUP:
				return owner.states.idle;
	
			default:
				break;

		}
		
		return null;
	}

	@Override
	public void entryAction() {
		owner.context.setGotoInactiveRequested(false);
		owner.context.getGui().showSystemInactiveView();

		owner.context.getTerminal().startBalance();
	}

	@Override
	public void exitAction() {

	}
}
