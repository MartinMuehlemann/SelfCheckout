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
	
	/* (non-Javadoc)
	 * @see com.swisshof.selfcheckout.log.IReceiptsArchiver#writeReceiptInArchive(java.lang.String)
	 */
	@Override
	public void writeReceiptInArchive(String receiptText) {
		PrintWriter pw = null;
		try {
			Path path = Paths.get(context.getArchiveDestination().toString(), buildReceiptFileName());
			File dir = path.getParent().toFile();
			
			// create directory
			if (dir.exists() == false) {
				dir.mkdir();
			}
			
			pw = new PrintWriter(path.toFile());
			pw.print(receiptText);
		}catch (IOException ioe) {
			logger.error("Write receipt failed:" + ioe.getMessage());
		}finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	protected String buildReceiptFileName()
	{
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis()));
		return timestamp + "_Receipt.txt";
	}
	
}
