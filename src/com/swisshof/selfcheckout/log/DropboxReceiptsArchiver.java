package com.swisshof.selfcheckout.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.*;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
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
		String path = context.getResourceProvider().getConfigParameterAsString("log.archive_destination") + "/" + buildReceiptFileName();		
		writeReceipt(receiptText, path);
	}
	
	@Override
	public void writeBalanceReceiptInArchive(String receiptText) {
		String path = context.getResourceProvider().getConfigParameterAsString("log.archive_destination") + "/" + buildDailyClosingFileName();
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
	
	protected String buildReceiptFileName()
	{
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis()));
		return timestamp + "_Receipt.txt";
	}
	
	protected String buildDailyClosingFileName()
	{
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis()));
		return timestamp + "_DailyClosing.txt";
	}

}
