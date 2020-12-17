package it.csi.stacore.stadocweb.business.integration;

import it.csi.stacore.stadoc.dto.stadoc.RequestRecuperaRiferimentoDocumentoFisico;
import it.csi.stacore.stadoc.dto.stadoc.ResponseRecuperaRiferimentoDocumentoFisico;
import it.csi.stacore.stadocweb.business.integration.exception.IntegrationException;

public interface StadocIntegration {


	public boolean testResource() throws IntegrationException;

	/*
	public ResponseSalvaDocumentoLogico salvaDocumentoLogico(RequestSalvaDocumentoLogico request) throws IntegrationException;

	public ResponseCambiaStatoRichiesta cambiaStatoRichiesta(RequestCambiaStatoRichiesta request) throws IntegrationException;

	*/

	public ResponseRecuperaRiferimentoDocumentoFisico recuperaRiferimentoDocumentoFisico(RequestRecuperaRiferimentoDocumentoFisico request) throws IntegrationException;
}
