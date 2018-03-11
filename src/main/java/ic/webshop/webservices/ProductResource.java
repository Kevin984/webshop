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
			job.add("Omschrijving", a.getOmschrijving());
			job.add("Prijs", a.getPrijs());

			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	
	}

	@Override
	public Response deleteProduct(int productID) {
		return null;
	}

	@Override
	public Response updateProduct(int productID, Product product) {
		return null;
	}

	@Override
	@POST
	@Produces("application/json")
	public String createProduct(@FormParam("ID")int ID, @FormParam("Naam") String naam, @FormParam("Afbeelding") byte[] afbeelding, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs) {
		Product product = new Product(ID, naam, omschrijving, afbeelding, prijs);
		productDAO.saveArtikel(product);
		return artikelToJson(product).build().toString();
	}

	private JsonObjectBuilder artikelToJson(Product a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("Naam", a.getNaam());
	//	job.add("Categorie", a.getBlobPlaatje());
		job.add("Omschrijving", a.getOmschrijving());
		job.add("Prijs", a.getPrijs());
		return job;
	}
}
