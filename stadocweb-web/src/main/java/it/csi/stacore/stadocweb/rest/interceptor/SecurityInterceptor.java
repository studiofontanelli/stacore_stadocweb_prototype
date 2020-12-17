package it.csi.stacore.stadocweb.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.UnauthorizedException;

import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.csi.stacore.stadocweb.util.ApplicationContextManager;
import it.csi.stacore.stadocweb.util.Constants;
import it.csi.stacore.stadocweb.util.EncryptUtil;
import it.csi.stacore.stadocweb.util.Tracer;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor {
	protected final static Logger LOG = LoggerFactory.getLogger(Constants.APPLICATION_CODE  + ".interceptor");

	@Context
	HttpServletRequest servletRequest;

	private static final String ACCESS_DENIED = "Access denied for this resource";


	private EncryptUtil getEncryptUtil() throws RuntimeException {
		return (EncryptUtil)ApplicationContextManager.getBean("encryptUtil");
	}

	public SecurityInterceptor() {


	}

	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethod) throws Failure, WebApplicationException {
		String methodName = resourceMethod.getMethod().getName();
		try {

			String idDocumento = request.getUri().getQueryParameters().getFirst("idDocumento");
			String checkDigit = request.getUri().getQueryParameters().getFirst("checkDigit");
			String codiceFruitore = request.getUri().getQueryParameters().getFirst("codiceFruitore");

			if(StringUtils.isBlank(idDocumento)) {
				Tracer.error(LOG, getClass().getName(), methodName, "idDocumento is null ");
				throw new BadRequestException("documento non valorizzato");
			}
			if(StringUtils.isBlank(checkDigit)) {
				Tracer.error(LOG, getClass().getName(), methodName, "checkDigit is null ");
				throw new BadRequestException("check digit non valorizzato");
			}
			if(StringUtils.isBlank(codiceFruitore)) {
				Tracer.error(LOG, getClass().getName(), methodName, "codiceFruitore is null ");
				throw new BadRequestException("codice fruitore non valorizzato");
			}



			String checkDigitCalcolato = getEncryptUtil().getCheckDigit(idDocumento);


			Tracer.debug(LOG, getClass().getName(), methodName, "idDocumento         = "  + idDocumento);
			Tracer.debug(LOG, getClass().getName(), methodName, "checkDigit          = "  + checkDigit);
			Tracer.debug(LOG, getClass().getName(), methodName, "checkDigitCalcolato = "  + checkDigitCalcolato);
			Tracer.debug(LOG, getClass().getName(), methodName, "codiceFruitore      = "  + codiceFruitore);

			/*
			if(!StringUtils.equals(checkDigit, checkDigitCalcolato)){
				throw new BadRequestException("check digit errato");
			}
			*/
			//Return null to continue request processing
			return null;
		}


		finally {
			Tracer.debug(LOG, getClass().getName(), methodName, "END");
		}
	}
}


