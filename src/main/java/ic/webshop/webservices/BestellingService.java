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

public interface BestellingService {
	@GET  												 
	@Produces("application/json") 
	public String getBestellingen();
	
	@GET	
	@Path("{ID}")
	@Produces("application/json")
	public String getBestelling(@PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteBestelling(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateBestelling(@PathParam("ID")int ID, @FormParam("AdresID") int adresID, @FormParam("AccountID") int accountID);
	
	@POST 
	@Produces("application/json")
	public String createBestelling( @FormParam("AdresID") int adresID, @FormParam("AccountID") int accountIDm);
}
