package com.swisshof.selfcheckout.log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.*;

import com.swisshof.selfcheckout.SelfCheckoutContext;

public class ReceiptsArchiver implements IReceiptsArchiver {
	protected Logger logger = LogManager.getLogger(getClass().getName());

	protected SelfCheckoutContext context = null;

	public ReceiptsArchiver(SelfCheckoutContext context) {
		this.context = context;
	}
	
	@Override
	public void writeReceiptInArchive(String receiptText) {
		Date timeStamp = new Date(System.currentTimeMillis());
		Path path = Paths.get(getReceiptPath(timeStamp).toString(), buildReceiptFileName(timeStamp));		
		writeReceipt(receiptText, path);
	}
	
	@Override
	public void writeBalanceReceiptInArchive(String receiptText) {
		Date timeStamp = new Date(System.currentTimeMillis());
		Path path = Paths.get(getReceiptPath(timeStamp).toString(), buildDailyClosingFileName(timeStamp));
		writeReceipt(receiptText, path);
	}
	
	protected void writeReceipt(String receiptText, Path path) {
		PrintWriter pw = null;
		try {
			File dir = path.getParent().toFile();
			
			// create directory
			if (dir.exists() == false) {
				dir.mkdir();
			}
			
			pw = new PrintWriter(path.toFile(), "UTF-8");
			pw.print(receiptText);
		}catch (IOException ioe) {
			logger.error("Write receipt failed:" + ioe.getMessage());
		}finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	protected Path getReceiptPath(Date timeStamp) {
		return Paths.get(context.getResourceProvider().getConfigParameterAsString("log.archive_destination"),
				 new SimpleDateFormat("yyyy").format(timeStamp),
				 new SimpleDateFormat("MM").format(timeStamp),
				 new SimpleDateFormat("yyyy_MM_dd").format(timeStamp));
	}
	
	
	protected String buildReceiptFileName(Date timeStamp)
	{
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(timeStamp);
		return timestamp + "_Receipt.txt";
	}
	
	protected String buildDailyClosingFileName(Date timeStamp)
	{
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(timeStamp);
		return timestamp + "_DailyClosing.txt";
	}
	
}
