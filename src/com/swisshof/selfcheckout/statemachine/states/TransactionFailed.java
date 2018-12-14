package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.gui.IGui;
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
			case TIMEOUT:			
			case BTN_OK:
				if (owner.context.isGotoInactiveRequested() == true) {
					return owner.states.inactive;
				} else {
					return owner.states.idle;
				}
				
			case CARD_REMOVED:
				owner.context.getGui().setInfoText(IGui.InformationType.INFO_ERROR, IGui.DisplayedButtons.BTN_OK, owner.context.getResourceProvider().getString("info.transactionFailure"));
				owner.context.getGui().enableBtnConfirm(true);
				return null;
				
			case CARD_INSERTED:
				//TODO: go to TransactionInProgress state?
				break;
				
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
		if (owner.context.getTerminal().isCardInserted() == true) {
			owner.context.getGui().setInfoText(IGui.InformationType.INFO_ERROR, IGui.DisplayedButtons.BTN_OK, owner.context.getResourceProvider().getString("info.transactionFailureCardInserted"));
			owner.context.getGui().enableBtnConfirm(false);
		} else {
			owner.context.getGui().setInfoText(IGui.InformationType.INFO_ERROR, IGui.DisplayedButtons.BTN_OK, owner.context.getResourceProvider().getString("info.transactionFailure"));
			owner.context.getGui().enableBtnConfirm(true);
		}
		
		int timeout = owner.context.getResourceProvider().getConfigParameterAsInt("ui.failure_screen_timeout", Constants.FAILURE_SCREEN_DEFAULT_TIMEOUT);
		startTimeout(timeout, MainStm.Events.TIMEOUT);
	}

	@Override
	public void exitAction() {
		// disconnect after transaction to avoid "Please Card" on the terminal
		owner.context.getTerminal().deactivate();
	}
	
	
}
