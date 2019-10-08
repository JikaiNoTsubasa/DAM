package com.dbiservices.dam.run;

import com.dbiservices.dam.ctrl.Controller;
import com.dbiservices.dam.ui.MainFrame;

public class Run {

	public static void main(String[] args) {
		Controller ctrl = new Controller();
		ctrl.init();
		
		MainFrame frame = new MainFrame();
		ctrl.setMainFrame(frame);
		frame.setController(ctrl);
		frame.build();
		
		
	}

}
