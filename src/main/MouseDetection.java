package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MouseDetection extends JPanel implements MouseListener {
	
	Panel panel;
	PanelSettings panelS;
	
	public MouseDetection(Panel panel) {
		this.panel = panel;
	}
	
	public void getPanelS(PanelSettings panelS) {
		this.panelS = panelS;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if( x > 100 && x< 580 && y> 100 && y< 620) {
			panel.projectM.selectProject(y,panelS);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
