package com.swisshof.selfcheckout.statemachine;

import com.swisshof.selfcheckout.IGui;
import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.StatemachineBase;
import com.swisshof.selfcheckout.statemachine.states.EnteringAmount;
import com.swisshof.selfcheckout.statemachine.states.Idle;
import com.swisshof.selfcheckout.statemachine.states.TransactionFailed;
import com.swisshof.selfcheckout.statemachine.states.TransactionInProgress;
import com.swisshof.selfcheckout.statemachine.states.TransactionSucessful;
import com.swisshof.selfcheckout.terminal.TerminalController;

public class MainStm extends StatemachineBase
{
	public enum Events implements Event {
		AMOUNT_CHANGED,
		BTN_PAY
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
		setInitialState(states.idle);

	}
	
	public void init()
	{
		setInitialState(states.idle);
	}
	
	public void amountChanged()
	{
		processEvent(Events.AMOUNT_CHANGED);
	}

	public void btnPayPressed() {
		processEvent(Events.BTN_PAY);
	}

}
