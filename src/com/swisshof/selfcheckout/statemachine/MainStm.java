package com.swisshof.selfcheckout.statemachine;

import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.StatemachineBase;
import com.swisshof.selfcheckout.statemachine.states.EnteringAmount;
import com.swisshof.selfcheckout.statemachine.states.Idle;
import com.swisshof.selfcheckout.statemachine.states.Inactive;
import com.swisshof.selfcheckout.statemachine.states.InvalidAmount;
import com.swisshof.selfcheckout.statemachine.states.ServiceMode;
import com.swisshof.selfcheckout.statemachine.states.TransactionFailed;
import com.swisshof.selfcheckout.statemachine.states.TransactionInProgress;
import com.swisshof.selfcheckout.statemachine.states.TransactionSucessful;

public class MainStm extends StatemachineBase
{
	public enum Events implements Event<Events> {
		TIMEOUT,
		AMOUNT_CHANGED,
		GOTO_INACTIVE,
		GOTO_SERVICEMODE,
		WAKEUP,
		BTN_PAY,
		BTN_OK,
		BTN_ABORT,
		BTN_YES,
		BTN_NO,
		BTN_EXIT,
		
		
		CARD_INSERTED,
		CARD_REMOVED,
		
		TRANSACTION_SUCCESSFUL,
		TRANSACTION_UNDEFINED_ERROR,
		TRANSACTION_ABORT,
		TRANSACTION_CONNECTION_ERROR,
		
		
		TRANSACTION_ABORT_UI;


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
		public InvalidAmount invalidAmount;
		public Inactive inactive;
		public ServiceMode serviceMode;
		
		public States(MainStm mainStm)  {
			idle = new Idle(mainStm);
			enteringAmount = new EnteringAmount(mainStm);
			transactionInProgress = new TransactionInProgress(mainStm);
			transactionSucessful = new TransactionSucessful(mainStm);
			transactionFailed = new TransactionFailed(mainStm);
			invalidAmount= new InvalidAmount(mainStm);
			inactive = new Inactive(mainStm);
			serviceMode = new ServiceMode(mainStm);
			
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
