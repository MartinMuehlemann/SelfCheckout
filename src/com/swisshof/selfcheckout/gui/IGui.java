package com.swisshof.selfcheckout.gui;

public interface IGui {
	

	public enum InformationType {
		INFO_PROGRESS,
		INFO_SUCCESS,
		INFO_WARNING,
		INFO_ERROR
	}
	
	public enum DisplayedButtons {
		BTN_OK,
		BTN_ABORT,
		BTN_YES_NO,
		BTN_NONE
	}
	
	public void enableBtnPay(boolean enable);
	public void setInfoText(String statusText);
	public void enableKeyBlock(boolean enable);
	
	public void showEntryAmountView();
	public void showInfoView();
	public void setInfoText(InformationType type, DisplayedButtons buttons, String infoText);
	public void showInfoView(InformationType type, DisplayedButtons buttons, String infoText);
	public void showSystemInactiveView();
	public void showServiceModeView();
	
	public void enableBtnConfirm(boolean enable);

	public void notifyTerminalRequestDone(boolean success, String details);
}
