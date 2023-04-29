package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class PanelSettings extends JPanel {
	
	JTextField nameProject = new JTextField();
	JButton removeBtn = new JButton();
	JButton addPathBtn = new JButton();
	JButton addProject = new JButton();
	JComboBox<String> pathSelection = new JComboBox<String>();
	

	public PanelSettings() {
		
		this.setPreferredSize(new Dimension(400, 400)); 
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.setOpaque(true);
			
		
	}
	
	public void createInterface(Panel panel) {
	
		
		addProject.setBounds(480,50,100,30);
		addProject.setFont(new Font("SansSerif", Font.PLAIN, 10));
		addProject.setText("New Project");
		this.add(addProject);
		
		
		pathSelection.setBounds(820,400,300,30);
		
		nameProject.setBounds(900,120,150,30);
		nameProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.projectM.listProject.get(panel.projectM.indexProjectSelected).name = nameProject.getText();
			}
		});
		
		
		removeBtn.setBounds(865,180,150,25);
		removeBtn.setText("Delete this Project");
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			panel.projectM.listProject.remove(panel.projectM.indexProjectSelected);
			panel.projectM.indexProjectSelected = -1;
			removeInterface();
		    }
		});
		
		
		addPathBtn.setBounds(0,0,100,100);
		addPathBtn.setText("addPath");
		addPathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser choose = new JFileChooser();
				choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue=choose.showOpenDialog(addPathBtn);
				if(returnValue==JFileChooser.APPROVE_OPTION){
				   String path =choose.getSelectedFile().getAbsolutePath();
				   panel.projectM.listProject.get(panel.projectM.indexProjectSelected).addPath(path);
				}
		    }
		});
	}
	
	public void addInterface(ProjectManager projectM) {
		
		this.add(addPathBtn);
		this.add(removeBtn);
		this.add(nameProject);
		
		for(String path : projectM.listProject.get(projectM.indexProjectSelected).listPath) {
			pathSelection.addItem(path);
		}
		this.add(pathSelection);
	}
	
	public void removeInterface() {
		this.remove(addPathBtn);
		this.remove(removeBtn);
		this.remove(nameProject);
		pathSelection.removeAllItems();
		this.remove(pathSelection);
	}
	
	public void disableButtons() {
		removeBtn.setEnabled(false);
	}
	public void unableButtons() {
		
	}
	
	
	
}
