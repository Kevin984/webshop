package ic.webshop.webservices;

import java.util.ArrayList;

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
import ic.webshop.domain.Klant;
import ic.webshop.persistence.AdresDAO;
import ic.webshop.persistence.KlantDAO;

@Path("/klanten")
public class KlantResource implements KlantService{
	private KlantDAO kDAO = new KlantDAO();
	private AdresDAO aDAO = new AdresDAO();
	
	@Override
	public String getKlanten() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Klant k : kDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", k.getID());
			job.add("Naam", k.getNaam());
			job.add("AdresID", k.getAdres().getID());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getKlant(@PathParam("ID")int ID) {
		Klant k = kDAO.findKlantByPK(ID);
		if(k == null){
			throw new WebApplicationException("Klant bestaat niet!");
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", k.getID());
		job.add("Naam", k.getNaam());
		job.add("AdresID", k.getAdres().getID());
		return job.build().toString();
	}

	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteKlant(@PathParam("ID")int ID) {
		Klant found = null;
		found = kDAO.findKlantByPK(ID);
		
		if(found != null) {
			kDAO.deleteKlant(found);
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
	public String updateKlant(@PathParam("ID")int ID, @FormParam("Naam") String naam, @FormParam("AdresID") int adresID) {
		Klant found = null;
		found = kDAO.findKlantByPK(ID);
		if(found != null) {
			found.setNaam(naam);
			Adres adres = aDAO.findAdresByPK(adresID);
			found.setAdres(adres);
		kDAO.updateKlant(found);
		return klantToJson(found).build().toString();
		}
		throw new WebApplicationException("Aanbieding not found!");
	}

	@Override
	@POST
	@Produces("application/json")
	public String createKlant( @FormParam("Naam") String naam, @FormParam("AdresID") int adresID) {
		Adres adres = aDAO.findAdresByPK(adresID);
		Klant klant = new Klant(naam, null, adres);
		kDAO.saveKlant(klant);
		return klantToJson(klant).build().toString();
	}
	
	private JsonObjectBuilder klantToJson(Klant k ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", k.getID());
		job.add("Naam", k.getNaam());
		job.add("AdresID", k.getAdres().getID());
		return job;
	}

}
