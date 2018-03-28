package com.swisshof.selfcheckout;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.swisshof.selfcheckout.IResourceProvider.ImageIdentifier;

public class ResourceProvider implements IResourceProvider {
	
	private final static String RESOURCE_BASE_PACKAGE = "res/";

	
	private static Logger logger = Logger.getLogger("RES");

	
	protected ImageIcon images[] = new ImageIcon[ImageIdentifier.values().length];
	protected Font font[] = new Font[FontIdentifier.values().length];
	
	/* (non-Javadoc)
	 * @see com.swisshof.selfcheckout.IResourceProvider#getSwisshofLogo()
	 */
	@Override
	public ImageIcon getImage(ImageIdentifier id) {

		try {
			if (images[id.ordinal()] == null) {
				URL url = getClass().getClassLoader().getResource(getImagePath(id));
				images[id.ordinal()] = new ImageIcon(url);
			}
			return images[id.ordinal()];

		} catch  (Exception e) {
			logger.severe("Exception while loading image: " + e.getMessage());
			return null; // TODO create defaukt icon
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.swisshof.selfcheckout.IResourceProvider#getFont()
	 */
	@Override
	public Font getFont(FontIdentifier id) {
		try {

			if (font[id.ordinal()] == null)
			{
				InputStream is = getClass().getClassLoader().getResourceAsStream(getFontPath(id));
				font[id.ordinal()] = Font.createFont(Font.TRUETYPE_FONT, is);
			}

			return font[id.ordinal()];

		} catch  (Exception e) {
			logger.severe("Exception while loading font: " + e.getMessage());
			font[id.ordinal()] = new Font("Arial", Font.BOLD, 12);
		}

		return font[id.ordinal()];
	}
	
	protected String getImagePath(ImageIdentifier id) throws Exception {
		switch (id) {
			case SwisshofLogo:
				return RESOURCE_BASE_PACKAGE + "Logo_154x100.jpg";
			case Success:
				return RESOURCE_BASE_PACKAGE + "success.png";
			case Hourglass:
				return RESOURCE_BASE_PACKAGE + "hourglass.png";
			case Failure:
				return RESOURCE_BASE_PACKAGE + "failure.png";
			default:
				throw new Exception("Unknwon font");
		
		}
	}
	

	protected String getFontPath(FontIdentifier id) throws Exception {
		switch (id) {
			case FrutigerBold:
				return RESOURCE_BASE_PACKAGE + "FTB.ttf";
			case FrutigerCondensed:
				return RESOURCE_BASE_PACKAGE + "FTLC.ttf";
			default:
				throw new Exception("Unknwon font");
		
		}
	}

}
