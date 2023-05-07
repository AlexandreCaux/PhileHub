package main;

public class PathOfProject {

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
	
	public String getIp() {
		return ip;
	}
	
}
