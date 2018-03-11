package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class TransactionInProgress extends State<MainStm> {

	public TransactionInProgress(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "TRANSACTION_IN_PROGRESS";
	}

	
	@Override
	public State<MainStm> processEvent(Event evt) {

		return null;
	}

	@Override
	public void entryAction() {
		owner.context.getTerminal().startPayment();
	}

	@Override
	public void exitAction() {

	}
	
	
}
