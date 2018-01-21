package xml;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServerXML {
	private String jdbc = "jdbc";
	private String type = "postgresql";
	private String link = "dbm.fe.up.pt";
	private String port = "5432";
	private String db = "sibd17g27";
	private String username = "sibd17g27";
	private String password = "pedrorenato";
	private String admin = "pawnstars";
	private String adminp = "pawnstars";
	
	public void manageXML() throws JAXBException{
		if(XMLMonitor.fileExists())
			XMLMonitor.loadServer(this);
		XMLMonitor.saveServer(this);
	}
	
	public String getJdbc() {
		return jdbc;
	}
	@XmlElement
	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}
	public String getType() {
		return type;
	}
	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
	public String getLink() {
		return link;
	}
	@XmlElement
	public void setLink(String link) {
		this.link = link;
	}
	public String getPort() {
		return port;
	}
	@XmlElement
	public void setPort(String port) {
		this.port = port;
	}
	public String getDb() {
		return db;
	}
	@XmlElement
	public void setDb(String db) {
		this.db = db;
	}
	public String getUsername() {
		return username;
	}
	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdmin() {
		return admin;
	}
	@XmlElement
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getAdminp() {
		return adminp;
	}
	@XmlElement
	public void setAdminp(String adminp) {
		this.adminp = adminp;
	}
}