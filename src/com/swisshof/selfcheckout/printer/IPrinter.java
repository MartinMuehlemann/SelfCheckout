package com.swisshof.selfcheckout.printer;

public interface IPrinter {
	public void storeReceipt(String receipt);
	public void clear();
}
