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

		return this;
	}

	@Override
	public void entryAction() {
		super.entryAction();
		
		owner.getGui().enableBtnPay(owner.context.getCurrentAmount() > 0.0);
	}

	@Override
	public void exitAction() {
		// TODO Auto-generated method stub
		super.exitAction();
	}
	
	
	
}
