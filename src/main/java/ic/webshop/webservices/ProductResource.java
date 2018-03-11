package ic.webshop.webservices;

import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import ic.webshop.domain.Product;
import ic.webshop.persistence.ProductDAO;
@Path("/producten") 
public class ProductResource implements ProductService{
	private ProductDAO productDAO = new ProductDAO();
	
	@Override
	public ArrayList<Product> getProducten() {
		return (ArrayList<Product>) productDAO.findAll();
	}

	@Override
	public Response deleteProduct(int productID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProduct(int productID, Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createProduct(Product product) {
		productDAO.saveArtikel(product);
		return Response.status(Response.Status.CREATED).build();
	}

}
