package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class ServiceMode extends State<MainStm, MainStm.Events> {

	public ServiceMode(MainStm owner) {
		super(owner);

	}
	
	@Override
	public String toString() {
		return "SERVICE_MODE";
	}

	
	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {
		switch(evt.getEvent()) {
		case TIMEOUT:
		case BTN_EXIT:
				return owner.states.idle;

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
		owner.context.getGui().showServiceModeView();
	}

	@Override
	public void exitAction() {

	}
	
	
}
