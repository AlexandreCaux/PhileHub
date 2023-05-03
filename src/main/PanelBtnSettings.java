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

public class PanelBtnSettings extends JPanel {
	
	JTextField nameProject = new JTextField();
	JButton removeBtn = new JButton();
	JButton addPathBtn = new JButton();
	JButton removePathBtn = new JButton();
	JButton addProject = new JButton();
	JComboBox<String> pathSelection = new JComboBox<String>();
	

	public PanelBtnSettings() {
		
		this.setFocusable(true);
		this.setOpaque(true);	
		this.setLayout(null);
		this.setBounds(0,0,1280,736);
		this.setBackground(new Color(0,0,0,0));
	}
	
	public void createBtn(Panel panel, PanelNewProject panelNewP, PanelBtnNew panelBtnN) {
		

		
		addProject.setBounds(480,50,100,30);
		addProject.setFont(new Font("SansSerif", Font.PLAIN, 10));
		addProject.setText("New Project");
		addProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelNewP.setVisible(true);
				panelBtnN.addBtn();
				panel.disableMouseDetector();
				disableButtons();
			}
		});
		this.add(addProject);
		
		
		pathSelection.setBounds(700,400,480,30);
		
		
		nameProject.setBounds(900,140,150,30);
		nameProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.projectM.listProject.get(panel.projectM.indexProjectSelected).name = nameProject.getText();
			}
		});
		
		
		removeBtn.setBounds(865,210,150,40);
		removeBtn.setText("Delete this Project");
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			panel.projectM.listProject.remove(panel.projectM.indexProjectSelected);
			panel.projectM.indexProjectSelected = -1;
			removeBtn();
		    }
		});
		
		
		addPathBtn.setBounds(965,500,150,40);
		addPathBtn.setText("Add A Path");
		addPathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser choose = new JFileChooser();
				choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue=choose.showOpenDialog(addPathBtn);
				if(returnValue==JFileChooser.APPROVE_OPTION){
				   String path =choose.getSelectedFile().getAbsolutePath();
				   panel.projectM.listProject.get(panel.projectM.indexProjectSelected).addPath(path);
				   removeBtn();
				   addBtn(panel.projectM);
				}
		    }
		});
		
		removePathBtn.setBounds(765,500,150,40);
		removePathBtn.setText("Remove This Path");
		removePathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String pathToRemove = pathSelection.getSelectedItem().toString();
				panel.projectM.listProject.get(panel.projectM.indexProjectSelected).removePath(pathToRemove);
				removeBtn();
				addBtn(panel.projectM);
		    }
		});
		
	}
	
	public void addBtn(ProjectManager projectM) {
		
		this.add(addPathBtn);
		this.add(removePathBtn);
		this.add(removeBtn);
		this.add(nameProject);
		for(String path : projectM.listProject.get(projectM.indexProjectSelected).listPath) {
			pathSelection.addItem(path);
		}
		this.add(pathSelection);
	}
	
	public void removeBtn() {
		this.remove(addPathBtn);
		this.remove(removePathBtn);
		this.remove(removeBtn);
		this.remove(nameProject);
		pathSelection.removeAllItems();
		this.remove(pathSelection);
	}
	
	public void disableButtons() {
		removeBtn.setEnabled(false);
		removePathBtn.setEnabled(false);
		addPathBtn.setEnabled(false);
		nameProject.setEnabled(false);
		pathSelection.setEnabled(false);
	}
	public void enableButtons() {
		removeBtn.setEnabled(true);
		removePathBtn.setEnabled(true);
		addPathBtn.setEnabled(true);
		nameProject.setEnabled(true);
		pathSelection.setEnabled(true);
	}
	
	
	
}
