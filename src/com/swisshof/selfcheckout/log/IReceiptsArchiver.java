package com.swisshof.selfcheckout.log;

public interface IReceiptsArchiver {

	void writeReceiptInArchive(String receiptText);
	void writeBalanceReceiptInArchive(String receiptText);
}