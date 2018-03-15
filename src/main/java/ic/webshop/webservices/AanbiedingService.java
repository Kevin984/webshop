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

public interface AanbiedingService {
	@GET  												 
	@Produces("application/json") 
	public String getAanbiedingen();
	
	@GET	
	@Produces("application/json")
	public String getAanbieding(@PathParam("ID") int ID);
	
	@GET	
	@Produces("application/json")
	public String getAanbiedingByProductID(@PathParam("ID") int ID);
	
	@DELETE
	@Path("{ID}")  													
	public Response deleteAanbieding(@PathParam("ID") int productID); 
	
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateAanbieding(@PathParam("ID") int ID, @FormParam("VanDatum") Date vdatum, @FormParam("TotDatum") Date tdatum
			, @FormParam("AanbiedingsPrijs") double prijs, @FormParam("ReclameTekst") String reclametekst, @FormParam("Product_ID") int productID);
	
	@POST 
	@Produces("application/json")
	public String createAanbieding(@FormParam("VanDatum") Date vdatum, @FormParam("TotDatum") Date tdatum
			, @FormParam("AanbiedingsPrijs") double prijs, @FormParam("ReclameTekst") String reclametekst, @FormParam("Product_ID") int productID);

}
