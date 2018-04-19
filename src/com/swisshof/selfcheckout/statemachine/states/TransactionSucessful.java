package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.gui.InfoPane.DisplayedButtons;
import com.swisshof.selfcheckout.gui.InfoPane.InformationType;
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
		switch(evt.getEvent()) {
		case TIMEOUT:
			return owner.states.idle;
		
		case BTN_YES:
			return owner.states.idle;
	
		case BTN_NO:
			return owner.states.idle;

		case CARD_REMOVED:
			owner.context.getGui().setInfoText(InformationType.INFO_SUCCESS, DisplayedButtons.BTN_YES_NO, owner.context.getString("info.transactionSuccess"));
			owner.context.getGui().enableBtnConfirm(true);
			return null;
			
		case CARD_INSERTED:
			//TODO: go to TransactionInProgress state?
			break;
			
		default:
			break;

	}
		return null;
	}

	@Override
	public void entryAction() {
		if (owner.context.getTerminal().isCardInserted() == true) {
			owner.context.getGui().setInfoText(InformationType.INFO_SUCCESS, DisplayedButtons.BTN_YES_NO, owner.context.getString("info.transactionSuccessCardInserted"));
			owner.context.getGui().enableBtnConfirm(false);
		} else {
			owner.context.getGui().setInfoText(InformationType.INFO_SUCCESS, DisplayedButtons.BTN_YES_NO, owner.context.getString("info.transactionSuccess"));
			owner.context.getGui().enableBtnConfirm(true);
		}
		
		startTimeout(Constants.SCCESS_SCREEN_TIMEOUT, MainStm.Events.TIMEOUT);
	}

	@Override
	public void exitAction() {

	}
	
	
}
