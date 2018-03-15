package com.swisshof.selfcheckout.terminal;

import java.util.logging.Logger;

import com.six.timapi.ActivateResponse;
import com.six.timapi.BalanceInquiryResponse;
import com.six.timapi.BalanceResponse;
import com.six.timapi.CardData;
import com.six.timapi.Counters;
import com.six.timapi.DeactivateResponse;
import com.six.timapi.DefaultTerminalListener;
import com.six.timapi.HardwareInformationResponse;
import com.six.timapi.PrintData;
import com.six.timapi.ReceiptRequestResponse;
import com.six.timapi.ReconciliationResponse;
import com.six.timapi.ShowDialogResponse;
import com.six.timapi.ShowSignatureCaptureResponse;
import com.six.timapi.SystemInformationResponse;
import com.six.timapi.Terminal;
import com.six.timapi.TimEvent;
import com.six.timapi.TimException;
import com.six.timapi.TransactionResponse;
import com.six.timapi.constants.UpdateStatus;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class TerminalListener extends DefaultTerminalListener {
	private static Logger logger = Logger.getLogger("EFT");
	protected SelfCheckoutContext context;
	
	
	public TerminalListener(SelfCheckoutContext context)
	{
		this.context = context;
	}
	
	@Override
	public void activateCompleted(TimEvent arg0, ActivateResponse arg1) {
		logger.info("Terminal: activateCompleted");
		super.activateCompleted(arg0, arg1);
	}

	@Override
	public void activateServiceMenuCompleted(TimEvent arg0) {
		logger.info("Terminal: activateServiceMenuCompleted");
		super.activateServiceMenuCompleted(arg0);
	}

	@Override
	public void applicationInformationCompleted(TimEvent arg0) {
		logger.info("Terminal: applicationInformationCompleted");
		super.applicationInformationCompleted(arg0);
	}

	@Override
	public void balanceCompleted(TimEvent arg0, BalanceResponse arg1) {
		logger.info("Terminal: balanceCompleted");
		super.balanceCompleted(arg0, arg1);
	}

	@Override
	public void balanceInquiryCompleted(TimEvent arg0, BalanceInquiryResponse arg1) {
		logger.info("Terminal: balanceInquiryCompleted");
		super.balanceInquiryCompleted(arg0, arg1);
	}

	@Override
	public void changeSettingsCompleted(TimEvent arg0) {
		logger.info("Terminal: changeSettingsCompleted");
		super.changeSettingsCompleted(arg0);
	}

	@Override
	public void closeDialogModeCompleted(TimEvent arg0) {
		logger.info("Terminal: closeDialogModeCompleted");
		super.closeDialogModeCompleted(arg0);
	}

	@Override
	public void closeMaintenanceWindowCompleted(TimEvent arg0) {
		logger.info("Terminal: closeMaintenanceWindowCompleted");
		super.closeMaintenanceWindowCompleted(arg0);
	}

	@Override
	public void closeReaderCompleted(TimEvent arg0) {
		logger.info("Terminal: closeReaderCompleted");
		super.closeReaderCompleted(arg0);
	}

	@Override
	public void commitCompleted(TimEvent arg0) {
		logger.info("Terminal: commitCompleted");
		super.commitCompleted(arg0);
	}

	@Override
	public void connectCompleted(TimEvent arg0) {
		logger.info("Terminal: connectCompleted");
		super.connectCompleted(arg0);
	}

	@Override
	public void counterRequestCompleted(TimEvent arg0, Counters arg1) {
		logger.info("Terminal: counterRequestCompleted");
		super.counterRequestCompleted(arg0, arg1);
	}

	@Override
	public void dccRatesCompleted(TimEvent arg0, PrintData arg1) {
		logger.info("Terminal: dccRatesCompleted");
		super.dccRatesCompleted(arg0, arg1);
	}

	@Override
	public void deactivateCompleted(TimEvent arg0, DeactivateResponse arg1) {
		logger.info("Terminal: deactivateCompleted");
		super.deactivateCompleted(arg0, arg1);
	}

	@Override
	public void disconnected(Terminal arg0, TimException arg1) {
		logger.info("Terminal: disconnected");
		super.disconnected(arg0, arg1);
	}

	@Override
	public void ejectCardCompleted(TimEvent arg0) {
		logger.info("Terminal: ejectCardCompleted");
		super.ejectCardCompleted(arg0);
	}

	@Override
	public void hardwareInformationCompleted(TimEvent arg0, HardwareInformationResponse arg1) {
		logger.info("Terminal: hardwareInformationCompleted");
		super.hardwareInformationCompleted(arg0, arg1);
	}

	@Override
	public void initTransactionCompleted(TimEvent arg0, CardData arg1) {
		logger.info("Terminal: initTransactionCompleted");
		super.initTransactionCompleted(arg0, arg1);
	}

	@Override
	public void loginCompleted(TimEvent arg0) {
		logger.info("Terminal: loginCompleted");
		super.loginCompleted(arg0);
	}

	@Override
	public void logoutCompleted(TimEvent arg0) {
		logger.info("Terminal: logoutCompleted");
		super.logoutCompleted(arg0);
	}

	@Override
	public void openDialogModeCompleted(TimEvent arg0) {
		logger.info("Terminal: openDialogModeCompleted");
		super.openDialogModeCompleted(arg0);
	}

	@Override
	public void openMaintenanceWindowCompleted(TimEvent arg0) {
		logger.info("Terminal: openMaintenanceWindowCompleted");
		super.openMaintenanceWindowCompleted(arg0);
	}

	@Override
	public void openReaderCompleted(TimEvent arg0) {
		logger.info("Terminal: openReaderCompleted");
		super.openReaderCompleted(arg0);
	}

	@Override
	public void printReceipts(Terminal arg0, PrintData arg1) {
		logger.info("Terminal: printReceipts");
		super.printReceipts(arg0, arg1);
	}

	@Override
	protected void processPrintReceipts(Terminal arg0, PrintData arg1) {
		logger.info("Terminal: processPrintReceipts");
		super.processPrintReceipts(arg0, arg1);
	}

	@Override
	public void queryLoyaltyCompleted(TimEvent arg0, CardData arg1) {
		logger.info("Terminal: queryLoyaltyCompleted");
		super.queryLoyaltyCompleted(arg0, arg1);
	}

	@Override
	public void rebootCompleted(TimEvent arg0) {
		logger.info("Terminal: rebootCompleted");
		super.rebootCompleted(arg0);
	}

	@Override
	public void receiptRequestCompleted(TimEvent arg0, ReceiptRequestResponse arg1) {
		logger.info("Terminal: receiptRequestCompleted");
		super.receiptRequestCompleted(arg0, arg1);
	}

	@Override
	public void reconciliationCompleted(TimEvent arg0, ReconciliationResponse arg1) {
		logger.info("Terminal: reconciliationCompleted");
		super.reconciliationCompleted(arg0, arg1);
	}

	@Override
	public void reconfigCompleted(TimEvent arg0, PrintData arg1) {
		logger.info("Terminal: reconfigCompleted");
		super.reconfigCompleted(arg0, arg1);
	}

	@Override
	public void requestAliasCompleted(TimEvent arg0, String arg1) {
		logger.info("Terminal: requestAliasCompleted");
		super.requestAliasCompleted(arg0, arg1);
	}

	@Override
	public void requestCompleted(TimEvent arg0, Object arg1) {
		logger.info("Terminal: requestCompleted");
		super.requestCompleted(arg0, arg1);
	}

	@Override
	public void rollbackCompleted(TimEvent arg0, PrintData arg1) {
		logger.info("Terminal: rollbackCompleted");
		super.rollbackCompleted(arg0, arg1);
	}

	@Override
	public void showDialogCompleted(TimEvent arg0, ShowDialogResponse arg1) {
		logger.info("Terminal: showDialogCompleted");
		super.showDialogCompleted(arg0, arg1);
	}

	@Override
	public void showSignatureCaptureCompleted(TimEvent arg0, ShowSignatureCaptureResponse arg1) {
		logger.info("Terminal: showSignatureCaptureCompleted");
		super.showSignatureCaptureCompleted(arg0, arg1);
	}

	@Override
	public void softwareUpdateCompleted(TimEvent arg0, UpdateStatus arg1) {
		logger.info("Terminal: softwareUpdateCompleted");
		super.softwareUpdateCompleted(arg0, arg1);
	}

	@Override
	public void systemInformationCompleted(TimEvent arg0, SystemInformationResponse arg1) {
		logger.info("Terminal: systemInformationCompleted");
		super.systemInformationCompleted(arg0, arg1);
	}

	@Override
	public void terminalStatusChanged(Terminal terminal) {
		
		logger.info("Terminal: terminalStatusChanged: " + terminal.getTerminalStatus().toString());
		super.terminalStatusChanged(terminal);
	}

	@Override
	public void transactionCompleted(TimEvent arg0, TransactionResponse arg1) {
		logger.info("Terminal: transactionCompleted");
		super.transactionCompleted(arg0, arg1);
	}
	
}