package com.dbiservices.dam.run;

import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class ListJavaFonts {

	public static void main(String[] args)
	{
		/*
		String fonts[] = 
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for ( int i = 0; i < fonts.length; i++ )
		{
			System.out.println(fonts[i]);
		}
		*/
		
		UIManager.LookAndFeelInfo looks[] = UIManager
				.getInstalledLookAndFeels();

		SortedSet<String> lafDefaultKeys = new TreeSet<>();

		for (UIManager.LookAndFeelInfo info : looks) {
			System.out.println("Including L&F "+info.getName());
			UIDefaults defaults = UIManager.getDefaults();
			Enumeration<Object> newKeys = defaults.keys();

			while (newKeys.hasMoreElements()) {
				lafDefaultKeys.add(newKeys.nextElement().toString());
			}
		}

		System.out.println();
		System.out.println("================== UIDefaults Keys ==================");

		for (String key : lafDefaultKeys) {
			System.out.println(key);
		}
	}

}
