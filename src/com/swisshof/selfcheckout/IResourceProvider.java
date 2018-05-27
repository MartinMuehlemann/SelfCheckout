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
	public int getConfigParameterAsInt(String key, int defaultValue);
	public double getConfigParameterAsDouble(String key, double defaultValue);
	public String getConfigParameterAsString(String key);
	public String getConfigParameterAsString(String key, String defaultValue);
	public boolean getConfigParameterAsBoolean(String key, boolean defaultValue);
	
	public boolean isConfigParameterExists(String key);
}