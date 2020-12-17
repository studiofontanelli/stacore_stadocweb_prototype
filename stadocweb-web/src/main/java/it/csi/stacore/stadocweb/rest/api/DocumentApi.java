package it.csi.stacore.stadocweb.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;

@Path("/document")
public interface DocumentApi {

	 @GET
	 @Path("/test")
	 @Produces({ "application/json" })
	 public boolean testResources(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders ) throws Exception;
}
