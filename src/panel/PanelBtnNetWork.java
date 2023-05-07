package panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Project;

public class PanelBtnNetWork extends JPanel {

	JComboBox<String> projectSelection = new JComboBox<String>();
	JTextField ipAdress = new JTextField();
	JButton cancelBtn = new JButton();
	JButton confirmBtn = new JButton();
	
	public PanelBtnNetWork() {
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.setOpaque(true);
		this.setLayout(null);
		this.setBounds(370,220,540,300);
		this.setBackground(new Color(0,0,0,0));
	}
	
	public void createBtn(Panel panel,PanelBtnSettings panelBtnS, PanelNetwork panelNet) {
		
		projectSelection.setBounds(100,80,400,30);
		
		ipAdress.setBounds(280,130,150,30);
		
		cancelBtn.setText("Cancel");
		cancelBtn.setBounds(100,200,150,30);
		cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				panelNet.setVisible(false);
				removeBtn();
				ipAdress.setText("");
				panel.enableMouseDetector();
				panelBtnS.enableButtons();
		    }
		});
		
		confirmBtn.setText("Confirm");
		confirmBtn.setBounds(300,200,150,30);
		confirmBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
				
		});
		
	}
	
	
	public void addBtn(Panel panel) {
		for(Project project : panel.projectM.getListProject()) {
			projectSelection.addItem(project.getName());
		}
		this.add(projectSelection);
		this.add(ipAdress);
		this.add(cancelBtn);
		this.add(confirmBtn);

	}
	public void removeBtn() {
		this.remove(projectSelection);
		this.remove(ipAdress);
		this.remove(cancelBtn);
		this.remove(confirmBtn);
	}
	
}
