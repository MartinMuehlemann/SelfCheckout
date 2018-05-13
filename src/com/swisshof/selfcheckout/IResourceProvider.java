package com.swisshof.selfcheckout;

import java.awt.Font;

import javax.swing.ImageIcon;

public interface IResourceProvider {

	public enum FontIdentifier {
		FrutigerBold,
		FrutigerCondensed
	}
	
	public enum ImageIdentifier {
		SwisshofLogo,
		SwisshofLogoBig,
		Success,
		Hourglass,
		Warning,
		Failure
	}
	
	
	public ImageIcon getImage(ImageIdentifier id);

	public Font getFont(FontIdentifier id);
	
	public String getString(String key);
	
	public int getConfigParameterAsInt(String key);
	public String getConfigParameterAsString(String key);

}