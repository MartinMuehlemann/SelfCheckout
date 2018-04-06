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
	
	@SuppressWarnings("rawtypes")
	public void processEvent(Event evt) {
		@SuppressWarnings("unchecked")
		State newState = currentState.processEvent(evt);
		if (newState != null)
		{
			// ==> execute state transition
			
			// stop timeout timer if there is one running
			currentState.stopTimeoutTimer();
			
			// execute exit action
			currentState.exitAction();
			
			logger.info(currentState.toString() + "=>" + newState.toString());
			
			// change state
			currentState = newState;
			
			// execute entry action
			currentState.entryAction();

		}
		
	}
	
}
