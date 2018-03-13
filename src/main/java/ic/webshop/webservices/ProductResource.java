package ic.webshop.webservices;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
		//	String blob = new String(a.getBlobPlaatje(), StandardCharsets.UTF_8);
		//	job.add("Afbeelding", blob);
		//	job.add("Categorie", a.getBlobPlaatje());
			job.add("Omschrijving", a.getOmschrijving());
			job.add("Prijs", a.getPrijs());

			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	
	}

	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteProduct(@PathParam("ID") int productID) {
		Product found = null;
		found = productDAO.findByPK(productID);
		
		if(found != null) {
			productDAO.deleteProduct(found);
			return Response.ok().build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Override
	@PUT
	@Path("{ID}")
	@Produces("application/json") 
	public String updateProduct(@PathParam("ID") int ID, @FormParam("Naam") String naam, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs) {
		Product found = null;
		found = productDAO.findByPK(ID);
		if(found != null) {
			found.setNaam(naam);
			found.setOmschrijving(omschrijving);
			found.setPrijs(prijs);
		productDAO.updateArtikel(found);
		}
		return artikelToJson(found).build().toString();
	}

	@Override
	@POST
	@Produces("application/json")
	public String createProduct(@FormParam("Naam") String naam, @FormParam("Omschrijving") String omschrijving
			, @FormParam("Prijs") double prijs) {
		Product product = new Product(naam, omschrijving, prijs);
		productDAO.saveArtikel(product);
		return artikelToJson(product).build().toString();
	}

	private JsonObjectBuilder artikelToJson(Product a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
//		job.add("ID", a.getID());
		job.add("Naam", a.getNaam());
//		String blob = new String(a.getBlobPlaatje(), StandardCharsets.UTF_8);
//		job.add("Afbeelding", blob);
	//	job.add("Categorie", a.getBlobPlaatje());
		job.add("Omschrijving", a.getOmschrijving());
		job.add("Prijs", a.getPrijs());
		return job;
	}
}
