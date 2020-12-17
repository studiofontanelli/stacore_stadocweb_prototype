package it.csi.stacore.stadocweb.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.stacore.stadoc.dto.stadoc.Context;
import it.csi.stacore.stadoc.dto.stadoc.RequestRecuperaRiferimentoDocumentoFisico;
import it.csi.stacore.stadoc.dto.stadoc.ResponseRecuperaRiferimentoDocumentoFisico;
import it.csi.stacore.stadocweb.business.integration.EcmEngineWebService;
import it.csi.stacore.stadocweb.business.integration.StadocIntegration;
import it.csi.stacore.stadocweb.business.integration.exception.IntegrationException;
import it.csi.stacore.stadocweb.business.service.DocumentService;
import it.csi.stacore.stadocweb.util.Constants;
import it.csi.stacore.stadocweb.util.Tracer;
import it.csi.stacore.stadocweb.util.XmlSerializer;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.mtom.dto.MtomOperationContext;

@Service()
public class DocumentServiceImpl implements DocumentService{

	private static final String LOGGER_PREFIX = Constants.APPLICATION_CODE + ".integration";
	protected final static Logger LOG = LoggerFactory.getLogger(LOGGER_PREFIX);

	@Autowired
	private StadocIntegration stadocIntegration;


	@Autowired
	private EcmEngineWebService ecmEngineWebService;

	@Override
	public boolean testResources() {
		try {
			return stadocIntegration.testResource();
		}
		catch(IntegrationException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		finally {

		}
	}

	@Override
	public void recuperaFile(String codiceFruitore, String idDocumento) {
		final String method = "recuperaFile";
		try {

			RequestRecuperaRiferimentoDocumentoFisico request = new RequestRecuperaRiferimentoDocumentoFisico();
			request.setIdDocumento(idDocumento);;
			request.setCodiceFruitore(codiceFruitore);
			ResponseRecuperaRiferimentoDocumentoFisico response = stadocIntegration.recuperaRiferimentoDocumentoFisico(request);


			String uuid = response.getUuid();

			if(LOG.isDebugEnabled()){
				Tracer.debug(LOG, getClass().getName(), method, "uuid= " + uuid);
			}



			Context ctx = response.getCxt();

			OperationContext operationContext = new OperationContext();
			operationContext.setUsername(ctx.getUsername());
			operationContext.setPassword(ctx.getPassword());
			operationContext.setFruitore(ctx.getFruitore());
			operationContext.setRepository(ctx.getRepository());




			Tracer.debug(LOG, getClass().getName(), method, "loading file " + XmlSerializer.objectToXml(operationContext));

			Tracer.debug(LOG, getClass().getName(), method, "loading file " + uuid);

			Node node = new Node(uuid);
			//Node node = new Node("e39ba722-38cc-11e4-b97e-7bf88caf85c0");
			Content content = new Content();
			content.setContentPropertyPrefixedName("cm:content");


			byte [] file = ecmEngineWebService.retrieveContentData(node, content, operationContext);


			Tracer.debug(LOG, getClass().getName(), method, "file loaded!!!");

			/*
			RequestCambiaStatoRichiesta requestCambioStatoRichiesta = new RequestCambiaStatoRichiesta();
			requestCambioStatoRichiesta.setCodiceFruitore(CODICE_FRUITORE_STADOC_CAMBIO_STATO_RICHIESTA);
			requestCambioStatoRichiesta.setIdDocumento(idDocumento);
			requestCambioStatoRichiesta.setIdRichiesta(responseRecuperaRiferimentoDocFisico.getIdRichiesta());
			requestCambioStatoRichiesta.setStatoRichiesta(RequestCambiaStatoRichiesta.STATO_RICHIESTA_OK);

			*/


		}
		catch(IntegrationException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		finally {

		}

	}

}
