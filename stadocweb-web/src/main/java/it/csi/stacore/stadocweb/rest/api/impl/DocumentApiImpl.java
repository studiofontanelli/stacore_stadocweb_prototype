package it.csi.stacore.stadocweb.rest.api.impl;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.stacore.stadocweb.business.service.DocumentService;
import it.csi.stacore.stadocweb.rest.api.DocumentApi;


@Component("documentApi")
public class DocumentApiImpl implements DocumentApi {


	@Autowired
	DocumentService documentService;

	@Override
	public boolean testResources(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders ) throws Exception {
		final String method = "testResources";
		try {
			String codiceFruitore = "PAGB";
			String idDocumento = "382";
			 documentService.recuperaFile(codiceFruitore, idDocumento);
			 return true;
		}
		finally {

		}

	}

}
