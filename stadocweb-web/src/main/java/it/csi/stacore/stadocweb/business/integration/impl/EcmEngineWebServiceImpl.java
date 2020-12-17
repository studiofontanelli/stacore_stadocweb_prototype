package it.csi.stacore.stadocweb.business.integration.impl;

import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.csi.stacore.stadocweb.business.integration.CommonIntegrationImpl;
import it.csi.stacore.stadocweb.business.integration.EcmEngineWebService;
import it.csi.stacore.stadocweb.business.integration.exception.IntegrationException;
import it.csi.stacore.stadocweb.util.Constants;
import it.csi.stacore.stadocweb.util.Environment;
import it.csi.stacore.stadocweb.util.Tracer;
import it.csi.stacore.stadocweb.util.XmlSerializer;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.FileFormatInfo;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Mimetype;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.NodeResponse;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.SearchParams;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;



@Repository()
public class EcmEngineWebServiceImpl extends CommonIntegrationImpl implements EcmEngineWebService {

	private EcmEngineWebServiceDelegate service;

	@Autowired
	private Environment environment;

	@PostConstruct
	private void init() throws Exception {
		final String method = "init";
		Tracer.debug(LOG, getClass().getName(), method, "BEGIN");
		try {
			String endpoint = environment.getIndexEndpoint();
			Tracer.info(LOG, getClass().getName(), method, "endpoint= " + endpoint);
			if(StringUtils.isBlank(endpoint)) {
				throw new Exception("flaianagla endpoint not found");
			}
			service = new EcmEngineWebServiceDelegateServiceLocator().getEcmEngineManagement(new URL(endpoint));
		}
		catch(Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new Exception("Impossibile chiamare flaianag");
		}
		finally {
			Tracer.debug(LOG, getClass().getName(), method, "END");
		}
	}

	@Override
	public boolean testResource() throws IntegrationException {
		final String method = "testResource";
		try {


			return  service.testResources();
		} catch (Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new IntegrationException(e, e.getMessage());
		}
	}

	@Override
	public String getMimeType(Mimetype mimeType) throws IntegrationException {
		final String method = "getMimeType";
		try {
			Mimetype[] mime = service.getMimetype(mimeType);
			if(mime!=null && mime.length>0){
				return mime[0].getMimetype();
			}
			else{
				return Constants.MIMETYPE_DEFAULT;
			}
		} catch (Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new IntegrationException(e, e.getMessage());
		}
	}

	@Override
	public String luceneSearchNoMetadata(SearchParams params, OperationContext operationContext) throws IntegrationException {
		final String method = "luceneSearchNoMetadata";
		String uid = null;
		try {
			String luceneQuery = "PATH:\""+params.getXPathQuery()+"\"";
			Tracer.info(LOG,  getClass().getName(), method, "lucene query= " +luceneQuery);
			//params.setLuceneQuery("PATH:\""+params.getXPathQuery()+"\"");
			params.setLuceneQuery(luceneQuery);
			NodeResponse response = service.luceneSearchNoMetadata(params, operationContext);
			if(response != null &&  response.getTotalResults() > 0 ){
				uid = response.getNodeArray()[0].getUid();
				Tracer.info(LOG,  getClass().getName(), method, "Folder  " + uid + " exists");
			}

		} catch (Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new IntegrationException(e, e.getMessage());
		}
		return uid;
	}

	@Override
	public Node createContent(Node node, Content content, OperationContext operationContext) throws IntegrationException {
		final String method = "createContent";
		Node result = null;
		try {
			result = service.createContent(node, content, operationContext);
		} catch (Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new IntegrationException(e, e.getMessage());
		}
		return result;
	}

	@Override
	public byte[] retrieveContentData(Node node, Content content, OperationContext operationContext) throws IntegrationException {
		final String method = "retrieveContentData";
		try {

			//FileFormatInfo [] ffi =  service.getFileFormatInfo(node, content, operationContext);
			//Tracer.info(LOG, getClass().getName(), method, "ffi\n " + XmlSerializer.objectToXml(ffi));

			return service.retrieveContentData(node, content, operationContext);
		} catch (Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new IntegrationException(e, e.getMessage());
		}

	}

}
