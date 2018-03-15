package com.swisshof.selfcheckout.statemachine.states;

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
			case AMOUNT_CHANGED:
				if (owner.context.getCurrentAmount() == 0.0) {
					return (owner.states.idle);
				}
				break;
			case BTN_PAY:
				if (owner.context.getCurrentAmount() > 0.0) {
					return (owner.states.transactionInProgress);
				}
				break;
	
			default:
				break;

		}

		return null;
	}

	@Override
	public void entryAction() {
		super.entryAction();
		
		owner.context.getGui().enableBtnPay(owner.context.getCurrentAmount() > 0.0);
	}

	@Override
	public void exitAction() {
		// TODO Auto-generated method stub
		super.exitAction();
	}
	
	
	
}
