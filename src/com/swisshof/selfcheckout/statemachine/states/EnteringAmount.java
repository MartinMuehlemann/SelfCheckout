package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class EnteringAmount extends State<MainStm, MainStm.Events>
{

	public EnteringAmount(MainStm stateMachine) {
		super(stateMachine);

	}

	@Override
	public String toString() {
		return "ENTERING_AMOUNT";
	}

	@Override
	public State<MainStm, MainStm.Events> processEvent(Event<MainStm.Events> evt) {

		switch (evt.getEvent()) {
			case TIMEOUT:
				return owner.states.idle;
			
			case AMOUNT_CHANGED:
				if (owner.context.getCurrentAmount() == 0.0) {
					return (owner.states.idle);
				} else {
					// restart timeout
					startTimeout(Constants.ENTER_AMOUNT_SCREEN_TIMEOUT, MainStm.Events.TIMEOUT);
					return null;
				}

			case BTN_PAY:
				return (owner.states.transactionInProgress);
	
			case GOTO_INACTIVE:
				owner.context.setGotoInactiveRequested(true);
				return null;
				
			case WAKEUP:
				owner.context.setGotoInactiveRequested(false);
				return null;
				
			default:
				return null;

		}
	}

	@Override
	public void entryAction() {
		owner.context.getGui().enableBtnPay(true);
		
		startTimeout(Constants.ENTER_AMOUNT_SCREEN_TIMEOUT, MainStm.Events.TIMEOUT);
	}

	@Override
	public void exitAction() {
		owner.context.getGui().enableBtnPay(false);
	}
	
	
	
}
