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

public interface CategorieService {
	@GET  												 
	@Produces("application/json") 
	public String getCategorieen();
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteCategorie(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateCategorie(@FormParam("ID") int ID, @FormParam("Naam") String naam, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs);
	
	@POST 
	@Produces("application/json")
	public String createCategorie(@FormParam("Naam") String nm, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs);

}
