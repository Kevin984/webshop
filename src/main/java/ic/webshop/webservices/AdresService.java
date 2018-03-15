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
	
	@GET	
	@Produces("application/json")
	public String getAdres(@PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteAdres(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateAdres(@PathParam("ID") int ID, @FormParam("Straat") String straat, @FormParam("Straatnummer") String Straatnummer);
	
	@POST 
	@Produces("application/json")
	public String createAdres(@FormParam("Straat") String straat, @FormParam("Straatnummer") String straatnummer);
}
