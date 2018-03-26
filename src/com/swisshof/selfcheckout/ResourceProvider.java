package com.swisshof.selfcheckout;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

public class ResourceProvider implements IResourceProvider {
	
	private final static String RESOURCE_BASE_PACKAGE = "res/";

	
	private static Logger logger = Logger.getLogger("RES");

	
	protected ImageIcon imgSwisshofLogo = null;
	protected Font font[] = new Font[FontName.values().length];
	
	/* (non-Javadoc)
	 * @see com.swisshof.selfcheckout.IResourceProvider#getSwisshofLogo()
	 */
	@Override
	public ImageIcon getSwisshofLogo() {
		if (imgSwisshofLogo == null) {
			try {
				URL url = getClass().getClassLoader().getResource(RESOURCE_BASE_PACKAGE + "Logo_154x100.jpg");
				imgSwisshofLogo = new ImageIcon(url);
			}catch (Exception e) {
				logger.severe("Exception while loading logo: " + e.getMessage());
				// TODO create defaukt icon
			}
		}
		return imgSwisshofLogo;
	}
	
	/* (non-Javadoc)
	 * @see com.swisshof.selfcheckout.IResourceProvider#getFont()
	 */
	@Override
	public Font getFont(FontName name) {
		try {

			if (font[name.ordinal()] == null) {
				font[name.ordinal()] = loadFont(getFontPath(name));
			}
			return font[name.ordinal()];

		} catch  (Exception e) {
			logger.severe("Exception while loading font: " + e.getMessage());
			font[name.ordinal()] = new Font("Arial", Font.BOLD, 12);
		}

		return font[name.ordinal()];
	}
	

	protected String getFontPath(FontName name) throws Exception {
		switch (name) {
			case FRUTIGER_BOLD:
				return RESOURCE_BASE_PACKAGE + "FTB.ttf";
			case FRUTIGER_CONDENSED:
				return RESOURCE_BASE_PACKAGE + "FTLC.ttf";
			default:
				throw new Exception("Unknwon font");
		
		}
	}

	protected Font loadFont(String path) throws FontFormatException, IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(path);
		return Font.createFont(Font.TRUETYPE_FONT, is);
	}
	
}
