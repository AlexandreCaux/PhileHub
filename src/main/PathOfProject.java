package main;

import java.io.Serializable;

public class PathOfProject implements Serializable {

	String ip;
	String path;
	Hello serv;
	
	public PathOfProject(String path, String ip) {
		this.ip = ip;
		this.path = path;
	}
	
	public boolean isLocal() {
		return ip.equals("0");
	}
	
	public String getPath() {
		return path;
	}
	
	public String getIP() {
		return ip;
	}
	
}
