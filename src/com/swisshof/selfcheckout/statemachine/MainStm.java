package com.swisshof.selfcheckout.statemachine;

import com.swisshof.selfcheckout.IGui;
import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.statemachine.generic.Event;
import com.swisshof.selfcheckout.statemachine.generic.StatemachineBase;
import com.swisshof.selfcheckout.statemachine.states.EnteringAmount;
import com.swisshof.selfcheckout.statemachine.states.Idle;

public class MainStm extends StatemachineBase
{
	public enum Events implements Event {
		AMOUNT_CHANGED,
		BTN_PAY
	}
	
	public class States {
		public Idle idle;
		public EnteringAmount enteringAmount;
		
		public States(MainStm mainStm)  {
			idle = new Idle(mainStm);
			enteringAmount = new EnteringAmount(mainStm);
		}		
	}

	public States states = new States(this);
	public SelfCheckoutContext context = null;
	public IGui gui = null;
	
	public MainStm(SelfCheckoutContext context) {
		super();
		states = new States(this);
		this.context = context;
		setInitialState(states.idle);

	}
	
	public void setGui(IGui gui) {
		this.gui = gui;
	}
	
	public IGui getGui() {
		return gui;
	}

	public void init()
	{
		setInitialState(states.idle);
	}
	
	public void amountChanged()
	{
		processEvent(Events.AMOUNT_CHANGED);
	}


}
