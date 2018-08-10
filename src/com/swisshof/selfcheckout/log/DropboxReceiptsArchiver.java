package com.swisshof.selfcheckout.log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.*;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class DropboxReceiptsArchiver implements IReceiptsArchiver {
	protected Logger logger = LogManager.getLogger(getClass().getName());

	protected SelfCheckoutContext context = null;
	
	protected String dropBoxAccessToken = "";
	
	public DropboxReceiptsArchiver(SelfCheckoutContext context, String dropBoxAccessToken) {
		this.context = context;
		this.dropBoxAccessToken = dropBoxAccessToken;
	}
	
	@Override
	public void writeReceiptInArchive(String receiptText) {
		Date timeStamp = new Date(System.currentTimeMillis());
		String path = getReceiptPath(timeStamp) + "/" + buildReceiptFileName(timeStamp);			
		writeReceipt(receiptText, path);
	}
	
	@Override
	public void writeBalanceReceiptInArchive(String receiptText) {
		Date timeStamp = new Date(System.currentTimeMillis());
		String path = getReceiptPath(timeStamp) + "/" + buildDailyClosingFileName(timeStamp);
		writeReceipt(receiptText, path);
	}
	
	protected void writeReceipt(String receiptText, String path) {
		

        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config, dropBoxAccessToken);
		
		InputStream in = new ByteArrayInputStream (receiptText.getBytes(StandardCharsets.UTF_8));

		try {
            client.files().uploadBuilder(path.toString()).uploadAndFinish(in);
        } catch (UploadErrorException e) {
			logger.error("Write receipt failed:" + e.getMessage());
			e.printStackTrace();
		} catch (DbxException e) {
			logger.error("Write receipt failed:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Write receipt failed:" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				in.close();
			}catch(IOException e) {
			}
		}
		
	}
	
	protected String getReceiptPath(Date timeStamp) {
		return context.getResourceProvider().getConfigParameterAsString("log.archive_destination") + "/" +
				 new SimpleDateFormat("yyyy").format(timeStamp) + "/" +
				 new SimpleDateFormat("MM").format(timeStamp) + "/" +
				 new SimpleDateFormat("yyyy_MM_dd").format(timeStamp);
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
