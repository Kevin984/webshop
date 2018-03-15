package ic.webshop.webservices;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import ic.webshop.domain.Klant;

@Path("/klanten")
public interface KlantService {
		
		@GET  												 
		@Produces("application/json") 
		public ArrayList<Klant> getKlanten();
		
		@GET	
		@Produces("application/json")
		public String getKlant(@PathParam("ID") int ID);
		

		@DELETE
		@Path("{id}")  													 
		public Response deleteKlant(@PathParam("id") int klantID);
		
		@PUT
		@Path("{id}")
		@Consumes("application/json") 
		public Response updateKlant(@PathParam("id") int klantID, Klant klant);
		
		@POST 
		@Consumes("application/json")
		public Response createKlant(Klant klant);
}
