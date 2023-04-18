package main;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class MouseWheelDetection extends JPanel implements MouseWheelListener {

	Panel panel;
	
	public MouseWheelDetection(Panel panel) {
		this.panel = panel;
	}
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(x > 100 && x < 600 && y > 100 && y < 620) {
			boolean isUp;
			if(e.getWheelRotation() < 0){
				isUp = true;
			}
			else {
				isUp = false;
			}
			panel.projectM.scroll(isUp);
		}
	}

}
