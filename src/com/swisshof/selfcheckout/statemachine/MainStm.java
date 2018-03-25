package com.swisshof.selfcheckout.statemachine;

import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.StatemachineBase;
import com.swisshof.selfcheckout.statemachine.states.EnteringAmount;
import com.swisshof.selfcheckout.statemachine.states.Idle;
import com.swisshof.selfcheckout.statemachine.states.TransactionFailed;
import com.swisshof.selfcheckout.statemachine.states.TransactionInProgress;
import com.swisshof.selfcheckout.statemachine.states.TransactionSucessful;

public class MainStm extends StatemachineBase
{
	public enum Events implements Event<Events> {
		AMOUNT_CHANGED,
		BTN_PAY,
		BTN_CONFIRM,
		TRANSACTION_SUCCESSFUL,
		TRANSACTION_UNDEFINED_ERROR,
		TRANSACTION_ABORT,
		TRANSACTION_CONNECTION_ERROR;

		@Override
		public Events getEvent() {
			return this;
		}
		
	}
	
	public class States {
		public Idle idle;
		public EnteringAmount enteringAmount;
		public TransactionInProgress transactionInProgress;
		public TransactionSucessful transactionSucessful;
		public TransactionFailed transactionFailed;
		
		
		public States(MainStm mainStm)  {
			idle = new Idle(mainStm);
			enteringAmount = new EnteringAmount(mainStm);
			transactionInProgress = new TransactionInProgress(mainStm);
			transactionSucessful = new TransactionSucessful(mainStm);
			transactionFailed = new TransactionFailed(mainStm);
		}		
	}

	public States states = new States(this);
	public SelfCheckoutContext context = null;
	
	public MainStm(SelfCheckoutContext context) {
		super();
		states = new States(this);
		this.context = context;

	}
	
	public void init()
	{
		setInitialState(states.idle);
	}
	
	

}
