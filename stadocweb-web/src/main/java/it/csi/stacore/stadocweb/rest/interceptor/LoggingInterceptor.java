package it.csi.stacore.stadocweb.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.csi.stacore.stadocweb.util.Constants;
import it.csi.stacore.stadocweb.util.Tracer;

@Provider
@ServerInterceptor
public class LoggingInterceptor implements PreProcessInterceptor {
	protected final static Logger LOG = LoggerFactory.getLogger(Constants.APPLICATION_CODE  + ".interceptor");

	@Context
	HttpServletRequest servletRequest;



	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethod) throws Failure, WebApplicationException {
		String method = "preProcess";

		//SpringContextLoaderListener
		String methodName = resourceMethod.getMethod().getName();
		//Tracer.debug(LOG, getClass().getName(), methodName, "pathInfo= " + servletRequest.getPathInfo());
		Tracer.debug(LOG, getClass().getName(), method, "Attempt to invoke \"" + servletRequest.getPathInfo() + "\" from " + servletRequest.getRemoteAddr());



		return null;
	}
}


