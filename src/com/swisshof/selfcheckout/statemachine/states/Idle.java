package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class Idle extends State<MainStm, MainStm.Events>
{

	public Idle(MainStm stateMachine) {
		super(stateMachine);

	}


	@Override
	public String toString() {
		return "IDLE";
	}

	
	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {

		
		switch (evt.getEvent()) {
			case AMOUNT_CHANGED:
				if(owner.context.getCurrentAmount() > 0.0) {
					return(owner.states.enteringAmount);
				}
				break;
			case BTN_PAY:
				if (owner.context.getCurrentAmount() > 0.0) {
					return (owner.states.transactionInProgress);
				}
				break;
	
			default:
				break;

		}
		
		return null;
	}

	@Override
	public void entryAction() {
		owner.context.getGui().enableKeyBlock(true);
		owner.context.getGui().showEntryAmountView();
	}

	@Override
	public void exitAction() {

	}
}
