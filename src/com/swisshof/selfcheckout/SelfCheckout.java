package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.statemachine.MainStm;

public class SelfCheckout {


	protected SelfCheckoutContext context;
	protected MainStm mainStm;
	
	public static void main(String[] args) {
		new SelfCheckout();
		
	}

	public SelfCheckout() {
		super();
		context = new SelfCheckoutContext();
		mainStm = new MainStm();
	}

}
