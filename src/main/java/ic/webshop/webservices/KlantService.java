package ic.webshop.webservices;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import ic.webshop.domain.Klant;

public interface KlantService {
		
		@GET  												 
		@Produces("application/json") 
		public String getKlanten();
		
		@GET	
		@Path("{ID}")
		@Produces("application/json")
		public String getKlant(@PathParam("ID") int ID);
		
		@DELETE
		@Path("{ID}")  													 
		public Response deleteKlant(@PathParam("ID") int klantID);
		
		@PUT
		@Path("{ID}")
		@Consumes("application/json") 
		public String updateKlant(@PathParam("ID")int ID, @FormParam("Naam") String naam, @FormParam("AdresID") int adresID);
		
		@POST 
		@Consumes("application/json")
		public String createKlant(@FormParam("Naam") String naam, @FormParam("AdresID") int adresID);
}
