package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class TransactionSucessful extends State<MainStm> {

	public TransactionSucessful(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "TRANSACTION_SUCCESSFUL";
	}

	
	@Override
	public State<MainStm> processEvent(Event evt) {

		return null;
	}

	@Override
	public void entryAction() {

	}

	@Override
	public void exitAction() {

	}
	
	
}
