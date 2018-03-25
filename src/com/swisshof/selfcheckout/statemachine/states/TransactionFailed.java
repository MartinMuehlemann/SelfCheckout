package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.gui.InfoPane.InformationType;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class TransactionFailed extends State<MainStm, MainStm.Events> {

	public TransactionFailed(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "TRANSACTION_FAILED";
	}

	
	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {
			switch(evt.getEvent()) {
			case   BTN_CONFIRM:
				return owner.states.idle;

			default:
				break;

		}
		return null;
	}

	@Override
	public void entryAction() {
		owner.context.getGui().setInfoText(InformationType.INFO_ERROR, owner.context.getString("info.transactionfailure"));
	}

	@Override
	public void exitAction() {

	}
	
	
}
