package com.swisshof.selfcheckout;

public interface IGui {
	public void enableBtnPay(boolean enable);
	public void setStatusText(String statusText);
	public void enableKeyBlock(boolean enable);
	public void resetGui();
	public void showPopup();
}
