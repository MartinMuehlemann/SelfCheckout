package com.swisshof.selfcheckout.statemachine.generic;

import java.util.logging.Logger;

public class StatemachineBase {
	@SuppressWarnings("rawtypes")
	protected State currentState = null;
	
	protected static Logger logger = Logger.getLogger("STM");
	
	protected StatemachineBase() {
		currentState = null;
	}
	
	@SuppressWarnings("rawtypes")
	protected void setInitialState(State initialState) {
		currentState = initialState;
		currentState.entryAction();
	}
	
	public void setNewState(State newState) {
		currentState.exitAction();
		logger.info(currentState.toString() + "=>" + newState.toString());
		currentState = newState;
		currentState.entryAction();
	}
	
	protected void processEvent(Event evt) {
		currentState = currentState.processEvent(evt);
	}
	
}
