package ic.webshop.webservices;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface AccountService {
	@GET  												 
	@Produces("application/json") 
	public String getAccounts();
	
	@GET	
	@Path("{ID}")
	@Produces("application/json")
	public String getAccount(@PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteAccount(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateAccount(@PathParam("ID")int ID, @FormParam("OpenDatum") Date datum, @FormParam("IsActief") String actief, @FormParam("FactuurAdresID") int adresID, @FormParam("KlantID") int klantID);
	
	@POST 
	@Produces("application/json")
	public String createAccount( @FormParam("OpenDatum") Date datum, @FormParam("IsActief") String actief, @FormParam("FactuurAdresID") int adresID, @FormParam("KlantID") int klantID);

}
