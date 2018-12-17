package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.gui.IGui;
import com.swisshof.selfcheckout.gui.IGui.DisplayedButtons;
import com.swisshof.selfcheckout.gui.IGui.InformationType;
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
		case BTN_OK:			
		case BTN_YES:
			if (owner.context.isGotoInactiveRequested() == true) {
				return owner.states.inactive;
			} else {
				return owner.states.idle;
			}
		case BTN_USER2:
			owner.context.getPrinter().printReceipt();
			
			if (owner.context.isGotoInactiveRequested() == true) {
				return owner.states.inactive;
			} else {
				return owner.states.idle;
			}

		case CARD_REMOVED:
			{
				DisplayedButtons btns;
				if (owner.context.getResourceProvider().getConfigParameterAsBoolean("printer.enabled", false))
				{
					btns = DisplayedButtons.BTN_OK_USER;
					owner.context.getGui().setBtnUser2Text(owner.context.getResourceProvider().getString("infopane.btn.print_receipt"));
				} else {
					btns = DisplayedButtons.BTN_OK;
				}
				
				owner.context.getGui().setInfoText(IGui.InformationType.INFO_SUCCESS, btns, owner.context.getResourceProvider().getString("info.transactionSuccess"));
				owner.context.getGui().enableBtnConfirm(true);
			}
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
		DisplayedButtons btns;
		if (owner.context.getResourceProvider().getConfigParameterAsBoolean("printer.enabled", false))
		{
			btns = DisplayedButtons.BTN_OK_USER;
			owner.context.getGui().setBtnUser2Text(owner.context.getResourceProvider().getString("infopane.btn.print_receipt"));
		} else {
			btns = DisplayedButtons.BTN_OK;
		}
		
		if (owner.context.getTerminal().isCardInserted() == true) {
			owner.context.getGui().setInfoText(InformationType.INFO_SUCCESS, btns, owner.context.getResourceProvider().getString("info.transactionSuccessCardInserted"));
			owner.context.getGui().enableBtnConfirm(false);
			owner.context.getGui().enableBtnUser2(false);
			

		} else {
			owner.context.getGui().setInfoText(InformationType.INFO_SUCCESS, btns, owner.context.getResourceProvider().getString("info.transactionSuccess"));
			owner.context.getGui().enableBtnConfirm(true);
			owner.context.getGui().enableBtnUser2(true);

		}
		
		int timeout = owner.context.getResourceProvider().getConfigParameterAsInt("ui.success_screen_timeout", Constants.SUCCESS_SCREEN_DEFAULT_TIMEOUT);
		startTimeout(timeout, MainStm.Events.TIMEOUT);
	}

	@Override
	public void exitAction()
	{
		// disconnect after transaction to avoid "Please Card" on the terminal
		owner.context.getTerminal().startDeactivate();
	}
	
	
}
