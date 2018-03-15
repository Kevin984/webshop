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
	@GET	
	@Path("{ID}")
	@Produces("application/json")
	public String getBestellingsregel(@PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteBestellingsregel(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateBestellingsregel(@PathParam("ID")int ID, @FormParam("Aantal") int aantal, @FormParam("BestellingID") int bestellingID, @FormParam("ProductID") int productID, @FormParam("Totaalprijs") double totaalprijs);
	
	@POST 
	@Produces("application/json")
	public String createBestellingsregel(@FormParam("Aantal") int aantal, @FormParam("BestellingID") int bestellingID, @FormParam("ProductID") int productID, @FormParam("Totaalprijs") double totaalprijs);
}
