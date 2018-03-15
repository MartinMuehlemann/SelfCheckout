package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class TransactionSucessful extends State<MainStm, MainStm.Events> {

	public TransactionSucessful(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "TRANSACTION_SUCCESSFUL";
	}

	
	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {

		return null;
	}

	@Override
	public void entryAction() {

	}

	@Override
	public void exitAction() {

	}
	
	
}
