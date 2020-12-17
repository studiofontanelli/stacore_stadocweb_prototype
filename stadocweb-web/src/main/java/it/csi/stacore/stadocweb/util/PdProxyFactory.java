package it.csi.stacore.stadocweb.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;



public class PdProxyFactory {

	private static final String LOGGER_PREFIX = Constants.APPLICATION_CODE + ".util";
	protected final static Logger LOG = LoggerFactory.getLogger(LOGGER_PREFIX);

	/**
	 * Costruttore vuoto
	 *
	 */
	public PdProxyFactory() {

	}

	public Object getObject(Resource pdConfigXml) throws Exception {

		String methodName = "getObject";
		Tracer.debug(LOG, getClass().getName(), methodName, "BEGIN");
		Tracer.debug(LOG, getClass().getName(), methodName, "obj= " + this);
		try {
			final InfoPortaDelegata info = PDConfigReader.read(pdConfigXml.getInputStream());

			Tracer.debug(LOG, getClass().getName(), methodName, "PortaDelegata loaded correctly for " + info.getInterfacciaPubblica());

			Object pdInterface = Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] { info.getInterfacciaPubblica() }, new InvocationHandler() {

				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					String methodName = "invoke";
					Tracer.debug(LOG, getClass().getName(), methodName, "invocazione " + method.getName());
					Object result;
					try {
						result = method.invoke(PDProxy.newInstance(info), args);
						Tracer.info(LOG, getClass().getName(), info.getInterfacciaPubblica().getName(), "Proxy all'interfaccia " + info.getInterfacciaPubblica().getName() + " creato con successo");
					} catch (InvocationTargetException e) {
						Tracer.error(LOG, getClass().getName(), info.getInterfacciaPubblica().getName(), "Proxy error interfaccia " + info.getInterfacciaPubblica().getName() + ": " + e);
						throw e.getTargetException();
					}

					return result;
				}
			});
			return pdInterface;
		} finally {
			Tracer.debug(LOG, getClass().getName(), methodName, "END");
		}
	}
}
