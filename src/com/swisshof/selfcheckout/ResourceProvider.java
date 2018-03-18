package com.swisshof.selfcheckout;

import java.awt.Font;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

public class ResourceProvider implements IResourceProvider {
	
	private final static String RESOURCE_BASE_PACKAGE = "res/";
	private static Logger logger = Logger.getLogger("RES");

	
	protected ImageIcon imgSwisshofLogo = null;
	protected Font font;
	
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
	public Font getFont() {
		if (font == null) {
			try {
				InputStream is = getClass().getClassLoader().getResourceAsStream("res/FTB.ttf");
				font = Font.createFont(Font.TRUETYPE_FONT, is);
			} catch  (Exception e) {
				logger.severe("Exception while loading font: " + e.getMessage());
				font = new Font("Arial", Font.BOLD, 12);
			}
		}
		return font;
	}
	

	
	
}
