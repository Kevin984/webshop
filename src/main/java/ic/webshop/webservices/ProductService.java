package ic.webshop.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ic.webshop.domain.Product;

public interface ProductService {
	@GET  												 
	@Produces("application/json") 
	public String getProducten();
	
	@GET
	@Path("{ID}")
	@Produces("application/json") 
	public String getProduct(@PathParam("ID") int ID);
	
	@GET
	@Path("/product/{ID}")
	@Produces("application/json") 
	public String getProductPrijs(@PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteProduct(@PathParam("ID") int productID); 
	
	@POST
	@Path("{ID}")
	@Produces("application/json") 
	public String updateProduct(@FormParam("ID") int ID, @FormParam("Naam") String naam, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs);
	
	@POST 
	@Produces("application/json")
	public String createProduct(@FormParam("Naam") String nm, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs);

}
