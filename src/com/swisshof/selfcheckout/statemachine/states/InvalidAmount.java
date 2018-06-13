package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.gui.IGui.DisplayedButtons;
import com.swisshof.selfcheckout.gui.IGui.InformationType;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class InvalidAmount extends State<MainStm, MainStm.Events> {

	public InvalidAmount(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "INVALID_AMOUNT";
	}

	
	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {
		switch(evt.getEvent()) {
		case TIMEOUT:
		case BTN_OK:			
		case BTN_YES:
		case BTN_NO:
			if (owner.context.isGotoInactiveRequested() == true) {
				return owner.states.inactive;
			} else {
				return owner.states.idle;
			}
		
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
		owner.context.getGui().showInfoView(InformationType.INFO_WARNING, DisplayedButtons.BTN_OK, String.format(owner.context.getResourceProvider().getString("info.amount_too_low_format"), owner.context.getResourceProvider().getConfigParameterAsDouble("minimum_amount_for_card", 0.0)));
		owner.context.getGui().enableBtnConfirm(true);
		
		int timeout = owner.context.getResourceProvider().getConfigParameterAsInt("ui.invalid_amount_screen_timeout", Constants.INVALID_AMOUNT_SCREEN_DEFAULT_TIMEOUT);
		startTimeout(timeout, MainStm.Events.TIMEOUT);
	}

	@Override
	public void exitAction() {

	}
	
	
}
