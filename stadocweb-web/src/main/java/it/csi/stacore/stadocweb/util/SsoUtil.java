package it.csi.stacore.stadocweb.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SsoUtil {

	protected final static Logger LOG = LoggerFactory.getLogger(Constants.APPLICATION_CODE);


	public String getIdDocumento(HttpServletRequest req){
		return req.getParameter("idDocumento");
	}

	public String getCodiceFruitore(HttpServletRequest req){
		return req.getParameter("codiceFruitore");
	}

	public String getCheckDigit(HttpServletRequest req){
		return req.getParameter("checkDigit");
	}

	public String getToken(HttpServletRequest req) throws Exception {
		String method = "getToken";
		String token = null;
		try{
			token = (String) req.getHeader(Constants.AUTH_ID_MARKER);
			Tracer.debug(LOG,  getClass().getName(), method, "token= " + token);
			if(token == null) throw new Exception("token non valorizzato");
		}
		catch(Exception e ){
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw e;
		}
		return token;

	}



}
