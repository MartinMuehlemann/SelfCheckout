package com.swisshof.selfcheckout;

public interface IGui {
	public void enableBtnPay(boolean enable);
	public void setInfoText(String statusText);
	public void enableKeyBlock(boolean enable);
	
	public void showEntryAmountView();
	public void showInfoView();
	
}
