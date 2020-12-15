/*
 *
 */
package it.csi.stacore.stadocweb.util;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 * @author andreafontanelli
 *
 */
public class TracerUtil implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = -1579589130959544401L;

	protected final static Logger LOG = LoggerFactory.getLogger(Constants.APPLICATION_CODE);

	//private static Log log = LogFactory.getLog(Constants.APPLICATION_CODE + ".business");

	private static void traceObject(Object obj, Logger logger, boolean internalLog){

		Logger myLog = null;
		if (internalLog) myLog = LOG;
		else myLog = logger;
		//Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "BEGIN");
		try{
			if(obj == null){
				if(myLog.isDebugEnabled())
					Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "CURRENT OBJECT IS NULL");
				return;
			}
			if(myLog.isDebugEnabled())
				Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", obj + " not null field list");
			String prefixGet = "get";
			String prefixIs = "is";

			Class c = obj.getClass();
			Method[] publicMethods = c.getMethods();
			Class[] paramsType = null;
			String methodName = null;
			for (int i = 0; i < publicMethods.length; i++) {
				methodName = publicMethods[i].getName();
				//if(	methodName.startsWith(prefixGet) || methodName.startsWith(prefixIs))  &&
				//		!methodName.equalsIgnoreCase("getClass") ){
				if(	methodName.startsWith(prefixGet) || methodName.startsWith(prefixIs)) {
					try{
						Method method = obj.getClass().getMethod(methodName, paramsType);

						paramsType = publicMethods[i].getParameterTypes();
						Object result = null;
						try{
							result = method.invoke(obj, paramsType);
						}
						catch(Exception ex){
							Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "Exception " + ex);
						}
						if(result != null){
							if(myLog.isDebugEnabled())
								Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", methodName + "= " + result.toString());
						}
					}
					catch(NoSuchMethodException nsmEx){
						if(myLog.isDebugEnabled())
							Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "Exception: " + nsmEx);
					}
					catch(Exception ex){
						if(myLog.isDebugEnabled())
							Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "Exception: " + ex);
					}
				}
			}
		}catch(Exception ex){
			if(myLog.isDebugEnabled())
				Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "Exception " + ex);
		}
		//Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "END");
	}


	public static void traceObject(Object obj){
		traceObject(obj, null, true);
	}



	public static void traceObject(Object obj, Logger log){

		//Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "BEGIN");
		try{
			if(obj == null){
				if(log.isDebugEnabled())
					Tracer.debug(log, TracerUtil.class.getName(), "traceObject", "CURRENT OBJECT IS NULL");
				return;
			}
			if(log.isDebugEnabled())
				Tracer.debug(log, TracerUtil.class.getName(), "traceObject", obj + " not null field list");
			String prefixGet = "get";
			String prefixIs = "is";

			Class c = obj.getClass();
			Method[] publicMethods = c.getMethods();
			Class[] paramsType = null;
			String methodName = null;
			for (int i = 0; i < publicMethods.length; i++) {
				methodName = publicMethods[i].getName();
				//if(	methodName.startsWith(prefixGet) || methodName.startsWith(prefixIs))  &&
				//		!methodName.equalsIgnoreCase("getClass") ){
				if(	methodName.startsWith(prefixGet) || methodName.startsWith(prefixIs)) {
					try{
						Method method = obj.getClass().getMethod(methodName, paramsType);

						paramsType = publicMethods[i].getParameterTypes();
						Object result = null;
						try{
							result = method.invoke(obj, paramsType);
						}
						catch(Exception ex){
							Tracer.debug(log, TracerUtil.class.getName(), "traceObject", "Exception " + ex);
						}
						if(result != null){
							if(log.isDebugEnabled())
								Tracer.debug(log, TracerUtil.class.getName(), "traceObject", methodName + "= " + result.toString());
						}
					}
					catch(NoSuchMethodException nsmEx){
						if(log.isDebugEnabled())
							Tracer.debug(log, TracerUtil.class.getName(), "traceObject", "Exception: " + nsmEx);
					}
					catch(Exception ex){
						if(log.isDebugEnabled())
							Tracer.debug(log, TracerUtil.class.getName(), "traceObject", "Exception: " + ex);
					}
				}
			}
		}catch(Exception ex){
			if(log.isDebugEnabled())
				Tracer.debug(log, TracerUtil.class.getName(), "traceObject", "Exception " + ex);
		}
		//Tracer.debug(myLog, TracerUtil.class.getName(), "traceObject", "END");
	}

}
