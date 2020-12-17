package it.csi.stacore.stadocweb.rest.api.impl;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import it.csi.stacore.stadocweb.rest.api.TestResourceApi;


@Component("testResourceApi")
public class TestResourceApiImpl implements TestResourceApi {

	@Override
	public boolean testResources(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders ) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

}
