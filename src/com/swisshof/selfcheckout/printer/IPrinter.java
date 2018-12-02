package com.swisshof.selfcheckout.printer;

public interface IPrinter {
	public void storeReceipt(String receipt);
	public void printReceipt();
	public void clear();

}
