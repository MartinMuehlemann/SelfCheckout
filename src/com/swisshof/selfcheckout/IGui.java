package com.swisshof.selfcheckout;

import com.swisshof.selfcheckout.gui.InfoPane.DisplayedButtons;
import com.swisshof.selfcheckout.gui.InfoPane.InformationType;

public interface IGui {
	public void enableBtnPay(boolean enable);
	public void setInfoText(String statusText);
	public void enableKeyBlock(boolean enable);
	
	public void showEntryAmountView();
	public void showInfoView();
	public void setInfoText(InformationType type, DisplayedButtons buttons, String infoText);
	public void showInfoView(InformationType type, DisplayedButtons buttons, String infoText);
	public void showSystemInactiveView();
	
	public void enableBtnConfirm(boolean enable);

	
}
