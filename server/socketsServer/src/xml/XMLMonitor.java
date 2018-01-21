package xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLMonitor {
	private static String filePath = "server.xml";

	private XMLMonitor() {}
	public static void saveServer(ServerXML server) throws JAXBException{
		JAXBContext jaxbContext = null;
		Marshaller jaxbMarshaller = null;
		File f = new File(filePath);
		jaxbContext = JAXBContext.newInstance(ServerXML.class);
		jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(server, f);
	}
	public static void loadServer(ServerXML xml) throws JAXBException{
		File file = new File(filePath);
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(ServerXML.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ServerXML server = (ServerXML) jaxbUnmarshaller.unmarshal(file);
		toXML(server,xml);
	}
	public static boolean fileExists() {
		File f = new File(XMLMonitor.getFilePath());
		return (f.exists() && !f.isDirectory());
	}
	public static String getFilePath() {
		return filePath;
	}
	public static void toXML(ServerXML server, ServerXML xml) {
		xml.setJdbc(server.getJdbc());
		xml.setType(server.getType());
		xml.setLink(server.getLink());
		xml.setPort(server.getPort());
		xml.setDb(server.getDb());
		xml.setUsername(server.getUsername());
		xml.setPassword(server.getPassword());
		xml.setAdmin(server.getAdmin());
		xml.setAdminp(server.getAdminp());
	}
}