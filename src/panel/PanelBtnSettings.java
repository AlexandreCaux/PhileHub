package panel;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import main.FileManager;
import main.PathOfProject;
import main.ProjectManager;
import main.ProjectSynchro;

public class PanelBtnSettings extends JPanel {
	
	JTextField nameProject = new JTextField();
	JButton removeBtn = new JButton();
	JButton addPathBtn = new JButton();
	JButton removePathBtn = new JButton();
	JButton addProject = new JButton();
	JButton networkBtn = new JButton();
	JComboBox<String> pathSelection = new JComboBox<String>();
	

	public PanelBtnSettings() {
		
		this.setFocusable(true);
		this.setOpaque(true);	
		this.setLayout(null);
		this.setBounds(0,0,1280,736);
		this.setBackground(new Color(0,0,0,0));
	}
	
	public void createBtn(Panel panel, PanelNewProject panelNewP, PanelBtnNewProject panelBtnN, PanelBtnNetWork panelBtnNet, PanelNetwork panelNet,ProjectSynchro projectS) {
		

		networkBtn.setBounds(100,50,110,30);
		networkBtn.setFont(new Font("SansSerif", Font.PLAIN, 10));
		networkBtn.setText("Share A Project");
		networkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelNet.setVisible(true);
				panelBtnNet.addBtn(panel);
				panel.disableMouseDetector();
				disableButtons();
			}
		});
		this.add(networkBtn);
		
		
		
		
		
		addProject.setBounds(470,50,110,30);
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
				panel.projectM.getElementListProject(panel.projectM.getIndexSelectedProject()).changeName(nameProject.getText());
				panel.projectM.save();
			}
		});
		
		
		removeBtn.setBounds(865,210,150,40);
		removeBtn.setText("Delete this Project");
		removeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			panel.projectM.removeProject(panel.projectM.getIndexSelectedProject());
			panel.projectM.changeIndexSelection(-1);
			panel.projectM.save();
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
				   String pathString =choose.getSelectedFile().getAbsolutePath();
				   boolean alreadyExist = false;
				   for(PathOfProject path : panel.projectM.getSelectionedProject().getListPath()) {
					   if(path.getPath().equals(pathString) && path.getIP().equals("0")){
						   alreadyExist=true;
					   }
				   }
				   if(!alreadyExist) {
					   panel.projectM.getSelectionedProject().addPath(pathString, "0");
					   if(panel.projectM.getSelectionedProject().getListPath().size() != 0) {
						   FileManager fileM = new FileManager(panel.projectM.getSelectionedProject());
						   panel.projectM.save();
						   try {
							projectS.addPathProject(panel.projectM.getSelectionedProject(), new PathOfProject(pathString,"0"));
						} catch (UnknownHostException | SocketException | RemoteException | MalformedURLException
								| NotBoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						   removeBtn();
						   addBtn(panel.projectM);
						   try {
							   fileM.copy(panel.projectM.getSelectionedProject().getListPath().get(0), new PathOfProject(pathString,"0"));
						   } catch (RemoteException ex) {
							   throw new RuntimeException(ex);
						   }
					   }
				   }
				}
		    }
		});
		
		removePathBtn.setBounds(765,500,150,40);
		removePathBtn.setText("Remove This Path");
		removePathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String pathToRemove = pathSelection.getSelectedItem().toString().split(": ")[1];
				System.out.println(pathToRemove);
				panel.projectM.getSelectionedProject().removePath(pathToRemove);
				panel.projectM.save();
				try {
					projectS.addPathProject(panel.projectM.getSelectionedProject(), new PathOfProject(pathToRemove,"0"));
				} catch (UnknownHostException | SocketException | RemoteException | MalformedURLException
						| NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				removeBtn();
				addBtn(panel.projectM);
		    }
		});
		
	}
	
	public void nameProjectText(String text){
		 nameProject.setText(text);
	}
	
	public void addBtn(ProjectManager projectM) {
		
		this.add(addPathBtn);
		this.add(removePathBtn);
		this.add(removeBtn);
		this.add(nameProject);
		for(PathOfProject path : projectM.getSelectionedProject().getListPath()) {
			if(path.getIP().equals("0")) {
				pathSelection.addItem("local: " + path.getPath());
			}
			else {
				pathSelection.addItem(path.getIP() + ": " + path.getPath());
			}
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
		networkBtn.setEnabled(false);
		addProject.setEnabled(false);
	}
	public void enableButtons() {
		removeBtn.setEnabled(true);
		removePathBtn.setEnabled(true);
		addPathBtn.setEnabled(true);
		nameProject.setEnabled(true);
		pathSelection.setEnabled(true);
		networkBtn.setEnabled(true);
		addProject.setEnabled(true);
	}
	
	
	
}
