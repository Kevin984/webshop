package ic.webshop.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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

import ic.webshop.domain.Adres;
import ic.webshop.domain.Bestelling;
import ic.webshop.domain.Bestellingsregel;
import ic.webshop.domain.Product;
import ic.webshop.persistence.BestellingDAO;
import ic.webshop.persistence.BestellingsregelDAO;
import ic.webshop.persistence.ProductDAO;
@Path("/bestellingsregels") 
public class BestellingsregelResource implements BestellingsregelService{
private BestellingsregelDAO bDAO = new BestellingsregelDAO();
private ProductDAO productDAO = new ProductDAO();
private BestellingDAO bestellingDAO = new BestellingDAO();

	@Override
	public String getBestellingsregels() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Bestellingsregel b : bDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", b.getID());
			job.add("Aantal", b.getAantal());
			job.add("BestellingID", b.getBestelling().getID());
			job.add("ProductID", b.getProduct().getID());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteBestellingsregel(@PathParam("ID")int ID) {
		Bestellingsregel found = null;
		found = bDAO.findBestellingsregelByPK(ID);
		
		if(found != null) {
			bDAO.deleteBestellingsregel(found);
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
	public String updateBestellingsregel(@PathParam("ID")int ID, @FormParam("Aantal") int aantal, @FormParam("BestellingID") int bestellingID, @FormParam("ProductID") int productID, @FormParam("Totaalprijs") double totaalprijs) {
		Bestellingsregel found = null;
		found = bDAO.findBestellingsregelByPK(ID);
		if(found != null) {
			
			found.setAantal(aantal);
			
			Bestelling bestelling = bestellingDAO.findBestellingByPK(bestellingID);
			Product product = productDAO.findByPK(productID);
			
			if(bestelling != null) {
			found.setBestelling(bestelling);
			}
			if(product != null) {
			found.setProduct(product);
			}
			found.setTotaalPrijs(totaalprijs);
			
		bDAO.updateBestellingsregel(found);
		return bestellingsregelToJson(found).build().toString();
		}
		throw new WebApplicationException("Bestellingsregel not found!");
	}

	@Override
	@POST
	@Produces("application/json")
	public String createBestellingsregel(@FormParam("Aantal") int aantal, @FormParam("BestellingID") int bestellingID, @FormParam("ProductID") int productID) {
		
		Bestelling bestelling = bestellingDAO.findBestellingByPK(bestellingID);
		Product product = productDAO.findByPK(productID);
		double totaalprijs = productDAO.getPrijs(productID) * aantal;
		
		Bestellingsregel bestellingsregel = new Bestellingsregel(bestelling, aantal, totaalprijs, product);
		bDAO.saveBestellingsregel(bestellingsregel);
		return bestellingsregelToJson(bestellingsregel).build().toString();
	}

	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getBestellingsregel(@PathParam("ID")int ID) {
		Bestellingsregel b = bDAO.findBestellingsregelByPK(ID);
		if(b == null){
			throw new WebApplicationException("Bestellingsregel bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", b.getID());
		job.add("Aantal", b.getAantal());
		job.add("BestellingID", b.getBestelling().getID());
		job.add("ProductID", b.getProduct().getID());
		return job.build().toString();
	}
	
	private JsonObjectBuilder bestellingsregelToJson(Bestellingsregel b ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", b.getID());
		job.add("Aantal", b.getAantal());
		job.add("BestellingID", b.getBestelling().getID());
		job.add("ProductID", b.getProduct().getID());
		return job;
	}

}
