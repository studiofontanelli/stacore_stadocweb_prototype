package it.csi.stacore.stadocweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



public class EncryptUtil {

	private final static String DEFAULT_KEY = "Mulligan";

	public String getKey(String key){
		return DateFormat.format(DateFormat.getCurrentDate(), "dd/MM/yyyy") + key;
	}

	public String getDefaultKey(){
		return DateFormat.format(DateFormat.getCurrentDate(), "dd/MM/yyyy") + DEFAULT_KEY;
		//return DEFAULT_KEY;
	}

	public String decodeBase64(String content){
		return new String(Base64.decodeBase64(content.getBytes()));
	}
	public String encodeBase64(String content){
		return new String(Base64.encodeBase64(content.getBytes()));
	}


	public String getCheckDigit(String content, String key){
		return md5(content+getKey(key));
	}

	public String getCheckDigit(String content){
		return md5(content+getDefaultKey());
	}

	public String decodeUrlParam(String param){
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return URLDecoder.decode(param);
		}
	}

	public String encodeUrlParam(String param){
		try {
			return URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return URLEncoder.encode(param);
		}
	}

	public String md5(String content){
		return DigestUtils.md5Hex(content);
	}




}
