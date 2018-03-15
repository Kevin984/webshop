package ic.webshop.webservices;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface AdresService {
	@GET  												 
	@Produces("application/json") 
	public String getAdressen();
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteAdres(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateAdres(@FormParam("ID") int ID);
	
	@POST 
	@Produces("application/json")
	public String createAdres(@FormParam("Naam") String nm);
}
