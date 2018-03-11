package com.swisshof.selfcheckout.statemachine.states;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.State;

public class EnteringAmount extends State<MainStm>
{

	public EnteringAmount(MainStm stateMachine) {
		super(stateMachine);

	}

	@Override
	public String toString() {
		return "ENTERING_AMOUNT";
	}

	@Override
	public State<MainStm> processEvent(Event evt) {
		if (evt == MainStm.Events.AMOUNT_CHANGED) {
			if(owner.context.getCurrentAmount() == 0.0) {
				return(owner.states.idle);
			}
		} else if (evt == MainStm.Events.BTN_PAY) {
			if(owner.context.getCurrentAmount() > 0.0) {
				return(owner.states.transactionInProgress);
			}	
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
