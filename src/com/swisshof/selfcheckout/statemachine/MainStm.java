package com.swisshof.selfcheckout.statemachine;

import com.swisshof.selfcheckout.statemachine.generic.StatemachineBase;
import com.swisshof.selfcheckout.statemachine.states.EnteringAmount;
import com.swisshof.selfcheckout.statemachine.states.Idle;

public class MainStm extends StatemachineBase
{

	
	public class States {
		public Idle idle;
		public EnteringAmount enteringAmount;
		
		public States(MainStm mainStm)  {
			idle = new Idle(mainStm);
			enteringAmount = new EnteringAmount(mainStm);
		}		
	}

	public States states = new States(this);
	
	public MainStm() {
		super();
		states = new States(this);
		setInitialState(states.idle);

	}

	
}
