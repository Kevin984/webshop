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

public interface BestellingsregelService {
	@GET  												 
	@Produces("application/json") 
	public String getBestellingsregels();
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteBestellingsregel(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateBestellingsregel(@FormParam("ID") int ID);
	
	@POST 
	@Produces("application/json")
	public String createBestellingsregel(@FormParam("Naam") String nm);
}
