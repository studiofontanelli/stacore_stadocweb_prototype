package it.csi.stacore.stadocweb.util;

import java.io.InputStream;
import java.io.Reader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlSerializer {
	
private static XStream xstream = new XStream(new DomDriver());
	
	public XmlSerializer(){
		
	}
	public static String objectToXml(Object obj){
		return xstream.toXML(obj);
	}
	public static Object xmlToObject(String xml){
		return xstream.fromXML(xml);
	}
	public static Object xmlToObject(InputStream is){
		return xstream.fromXML(is);
	}
	public static Object xmlToObject(Reader reader){
		return xstream.fromXML(reader);
	}

}
