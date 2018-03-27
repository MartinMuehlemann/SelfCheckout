package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.gui.InfoPane.InformationType;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class TransactionInProgress extends State<MainStm, MainStm.Events> {

	public TransactionInProgress(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "TRANSACTION_IN_PROGRESS";
	}

	
	@Override
	public State<MainStm,MainStm.Events> processEvent(Event<MainStm.Events> evt) {
		
		switch(evt.getEvent()) {
			case  TRANSACTION_SUCCESSFUL:
				return owner.states.transactionSucessful;

			case TRANSACTION_ABORT:
			case TRANSACTION_CONNECTION_ERROR:
			case TRANSACTION_UNDEFINED_ERROR:
				return owner.states.transactionFailed;
				
			default:
				break;

		}
		return null;

	}

	@Override
	public void entryAction() {
		owner.context.getGui().showInfoView(InformationType.INFO_PROGRESS, owner.context.getString("info.transactionInProgress"));
		owner.context.getTerminal().startPayment();
		
	}

	@Override
	public void exitAction() {

	}
	
	
}
