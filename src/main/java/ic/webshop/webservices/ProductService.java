package ic.webshop.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ic.webshop.domain.Product;

public interface ProductService {
	@GET  												 
	@Produces("application/json") 
	public String getProducten();
	
	@DELETE
	@Path("{id}")  													
	public Response deleteProduct(@PathParam("id") int productID); 
	
	@PUT
	@Path("{id}")
	@Consumes("application/json") 
	public Response updateProduct(@PathParam("id") int productID, 
								   Product product);
	
	@POST 
	@Produces("application/json")
	public Response createProduct(Product product);

}
