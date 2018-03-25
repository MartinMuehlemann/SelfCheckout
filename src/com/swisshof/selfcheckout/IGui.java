package com.swisshof.selfcheckout;

import com.swisshof.selfcheckout.gui.InfoPane.InformationType;

public interface IGui {
	public void enableBtnPay(boolean enable);
	public void setInfoText(String statusText);
	public void enableKeyBlock(boolean enable);
	
	public void showEntryAmountView();
	public void showInfoView();
	public void setInfoText(InformationType type, String infoText);
	public void showInfoView(InformationType type, String infoText);
	
}
