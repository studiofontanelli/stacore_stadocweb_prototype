package it.csi.stacore.stadocweb.business.integration.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.stacore.stadoc.dto.stadoc.RequestRecuperaRiferimentoDocumentoFisico;
import it.csi.stacore.stadoc.dto.stadoc.ResponseRecuperaRiferimentoDocumentoFisico;
import it.csi.stacore.stadoc.exception.stadoc.RecuperaRiferimentoDocumentoFisicoException;
import it.csi.stacore.stadoc.interfacecsi.stadoc.StadocSrv;
import it.csi.stacore.stadocweb.business.integration.CommonIntegrationImpl;
import it.csi.stacore.stadocweb.business.integration.StadocIntegration;
import it.csi.stacore.stadocweb.business.integration.exception.IntegrationException;
import it.csi.stacore.stadocweb.util.Tracer;



@Repository()
public class StadocIntegrationImpl extends CommonIntegrationImpl implements StadocIntegration {

	@Resource
    @Qualifier("stadocSrv")
    private StadocSrv stadocSrv;



	@PostConstruct
	private void init() throws Exception {
		final String method = "init";
		Tracer.debug(LOG, getClass().getName(), method, "BEGIN");
		try {

		}
		finally {
			Tracer.debug(LOG, getClass().getName(), method, "END");
		}
	}

	@Override
	public boolean testResource() throws IntegrationException {
		final String method = "testResource";
		try {
			return  stadocSrv.testResources();
		} catch (Exception e) {
			Tracer.error(LOG, getClass().getName(), method, "Exception " + e);
			throw new IntegrationException(e, e.getMessage());
		}
	}

	public ResponseRecuperaRiferimentoDocumentoFisico recuperaRiferimentoDocumentoFisico(RequestRecuperaRiferimentoDocumentoFisico request) throws IntegrationException {
		String method = "recuperaRiferimentoDocumentoFisico";
		try {
			return stadocSrv.recuperaRiferimentoDocumentoFisico(request);

		} catch (RecuperaRiferimentoDocumentoFisicoException e) {
			Tracer.error(LOG,  getClass().getName(), method, "RecuperaRiferimentoDocumentoFisicoException " + e);
			throw new IntegrationException(e.getMessage());
		} catch (it.csi.csi.wrapper.SystemException e) {
			Tracer.error(LOG,  getClass().getName(), method, "SystemException " + e);
			throw new IntegrationException(e.getMessage());
		} catch (UnrecoverableException e) {
			Tracer.error(LOG,  getClass().getName(), method, "UnrecoverableException " + e);
			throw new IntegrationException(e.getMessage());
		} catch (CSIException e) {
			Tracer.error(LOG,  getClass().getName(), method, "CSIException " + e);
			throw new IntegrationException(e.getMessage());
		}

	}



}
