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
	public String getProducten() {
JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Product a : productDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", a.getID());
			job.add("Naam", a.getNaam());
		//	job.add("Categorie", a.getBlobPlaatje());
			job.add("Maat", a.getOmschrijving());
			job.add("Kleur", a.getPrijs());

			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	
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
	@Produces("application/json")
	public Response createProduct(@FormParam("ID")int ID, @FormParam("Naam") String naam, @FormParam("Afbeelding") String afbeelding, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs) {
		Product product = new Product(ID, afbeelding, naam, omschrijving, prijs);
		productDAO.saveArtikel(product);
		return Response.status(Response.Status.CREATED).build();
	}

}
