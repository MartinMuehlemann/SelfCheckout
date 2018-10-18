package com.swisshof.selfcheckout.printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.swisshof.selfcheckout.SelfCheckoutContext;

public class PrinterEpos implements IPrinter {

	protected Logger logger = LogManager.getLogger("PRINTER");
	private static String EPOS = "http://www.epson-pos.com/schemas/2011/03/epos-print";
	private static String SOAP = "http://schemas.xmlsoap.org/soap/envelope/";
	private static int TIMEOUT = 10000;
	
	protected String storedReceipt = null;
	protected SelfCheckoutContext context;

	public PrinterEpos(SelfCheckoutContext context) {
		super();
		this.context = context;
	}

	@Override
	public void storeReceipt(String receipt) {
		storedReceipt = receipt;
	}
	
	@Override
	public void printReceipt()
	{
		print(storedReceipt);
	}

	@Override
	public void clear() {
		storedReceipt = null;
	}

	private String getPrinterUrl()
	{
		String url  = "http://";
		url += context.getResourceProvider().getConfigParameterAsString("printer.ip");
		url += "/cgi-bin/epos/service.cgi?devid=local_printer&timeout=";
		url += String.valueOf(TIMEOUT);
		return url;
	}
	
	private void print(String receipt) {
		URL url;
		try {
			url = new URL(getPrinterUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error(e);
			//TODO
			return;
		}
		HttpURLConnection conn;
		try {
			
			// open connection to printer
			conn = (HttpURLConnection)url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction", "\"\"");
			
		
			conn.connect();
		
			String req = 
					"<s:Envelope xmlns:s='http://schemas.xmlsoap.org/soap/envelope/'>" +
						"<s:Body>" +
							"<epos-print xmlns='http://www.epson-pos.com/schemas/2011/03/epos-print'>" +
								"<text lang='en' smooth='true'>" + receipt + "</text>" +

								"<feed unit='24' />" +
								//"<image width='8' height='48'>8PDw8A8PDw/w8PDwDw8PD/Dw8PAPDw8P8PDw8A8PDw/w8PDwDw8PD/Dw8PAPDw8P</image>" + 
								"<cut />" +
							"</epos-print>" +
						"</s:Body>" +
					"</s:Envelope>";
			OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			w.write(req);
			w.close();
			
			StreamSource source = new StreamSource(conn.getInputStream());
			DOMResult result = new DOMResult();
			TransformerFactory f2 = TransformerFactory.newInstance();
			Transformer t = f2.newTransformer();
			t.transform(source, result);
			
			// Parse response document (DOM)
			Document doc = (Document)result.getNode();
			Element el = (Element)doc.getElementsByTagNameNS(EPOS, "response").item(0);

			
			System.out.println("response: " + el.getAttribute("success"));
			
			// TODO: check the result
			//JOptionPane.showMessageDialog(null, el.getAttribute("success"));

			
		} catch (IOException e) {
			//TODO
			logger.error(e);
			return;

		}catch(TransformerConfigurationException e) {
			//TODO
			logger.error(e);
			return;
		}catch(TransformerException e) {
		
			//TODO
			logger.error(e);
			return;
		}
	}
}
