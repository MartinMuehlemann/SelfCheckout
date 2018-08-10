package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.gui.IGui.DisplayedButtons;
import com.swisshof.selfcheckout.gui.IGui.InformationType;
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
				
			case BTN_ABORT:
				owner.context.getTerminal().abortTransaction();
				// stay in the state until terminal confirmed abort
				return null;
				
			case TRANSACTION_ABORT_UI:
				return owner.states.idle;				

			case TRANSACTION_ABORT:
			case TRANSACTION_CONNECTION_ERROR:
			case TRANSACTION_UNDEFINED_ERROR:
				return owner.states.transactionFailed;
			
			case GOTO_INACTIVE:
				owner.context.setGotoInactiveRequested(true);
				return null;
				
			case WAKEUP:
				owner.context.setGotoInactiveRequested(false);
				return null;
				
			default:
				break;

		}
		return null;

	}

	@Override
	public void entryAction() {
		owner.context.getGui().showInfoView(InformationType.INFO_PROGRESS, DisplayedButtons.BTN_ABORT, owner.context.getResourceProvider().getString("info.transactionInProgress"));
		owner.context.getTerminal().startPayment();
		
	}

	@Override
	public void exitAction() {

	}
	
	
}
