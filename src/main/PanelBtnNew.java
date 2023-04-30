package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelBtnNew extends JPanel{

	JTextField nameProject = new JTextField();
	JButton addPathBtn = new JButton();
	JButton confirmBtn = new JButton();
	JButton cancelBtn = new JButton();
	String pathSelected = "";
	
	public PanelBtnNew(PanelNewProject panelNewP) {
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.setOpaque(true);
		this.setLayout(null);
		this.setBounds(370,195,540,350);
		this.setBackground(new Color(0,0,0,0));
	}
	
	public void createBtn(Panel panel,PanelNewProject panelNewP, PanelBtnSettings panelBtnS) {
		nameProject.setBounds(200,80,200,25);
		
		
		addPathBtn.setBounds(170,135,200,25);
		addPathBtn.setText("Select A Path");
		addPathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser choose = new JFileChooser();
				choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue=choose.showOpenDialog(addPathBtn);
				if(returnValue==JFileChooser.APPROVE_OPTION){
				   panelNewP.pathSelected =choose.getSelectedFile().getAbsolutePath();
				   pathSelected = choose.getSelectedFile().getAbsolutePath();;
				}
		    }
		});
		
		confirmBtn.setText("Confirm");
		confirmBtn.setBounds(300,250,150,30);
		confirmBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameProject.getText();
				if(name != "" && pathSelected != "") {
					Project project = new Project(name);
					project.addPath(pathSelected);
					panel.projectM.addProject(project);
					panelNewP.pathSelected = "";
					pathSelected ="";
					panelNewP.setVisible(false);
					panelNewP.errorType = false;
					removeBtn();
					nameProject.setText("");
					panel.enableMouseDetector();
					panelBtnS.enableButtons();
				}
				else {
					panelNewP.errorType = true;
				}
		    }
		});
		
		cancelBtn.setText("Cancel");
		cancelBtn.setBounds(100,250,150,30);
		cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				panelNewP.pathSelected = "";
				pathSelected ="";
				panelNewP.setVisible(false);
				panelNewP.errorType = false;
				removeBtn();
				nameProject.setText("");
				panel.enableMouseDetector();
				panelBtnS.enableButtons();
		    }
		});
	}
		
	
	public void addBtn() {
		this.add(nameProject);
		this.add(addPathBtn);
		this.add(confirmBtn);
		this.add(cancelBtn);

	}
	public void removeBtn() {
		this.remove(nameProject);
		this.remove(addPathBtn);
		this.remove(confirmBtn);
		this.remove(cancelBtn);
	}
	
}
