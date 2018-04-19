package com.swisshof.selfcheckout.printer;

public class Printer implements IPrinter {

	protected String storedReceipt = null;
	
	@Override
	public void storeReceipt(String receipt) {
		storedReceipt = receipt;
	}

	@Override
	public void clear() {
		storedReceipt = null;
	}

}
