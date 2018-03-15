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
import ic.webshop.persistence.AdresDAO;

@Path("/adressen") 
public class AdresResource implements AdresService{
private AdresDAO aDAO = new AdresDAO();

	@Override
	public String getAdressen() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Adres a : aDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", a.getID());
			job.add("Straat", a.getStraat());
			job.add("Straatnummer", a.getStraatNummer());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	
	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getAdres(int ID) {
		Adres a = aDAO.findAdresByPK(ID);
		if(a == null){
			throw new WebApplicationException("Aanbieding bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("VanDatum", a.getStraat());
		job.add("TotDatum", a.getStraatNummer());
		return job.build().toString();
	}

	
	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteAdres(int ID) {
		Adres found = null;
		found = aDAO.findAdresByPK(ID);
		
		if(found != null) {
			aDAO.deleteAdres(found);
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
	public String updateAdres(@PathParam("ID") int ID, @FormParam("Straat") String straat, @FormParam("Straatnummer") String straatnummer) {
		Adres found = null;
		found = aDAO.findAdresByPK(ID);
		if(found != null) {
			found.setStraat(straat);
			found.setStraatNummer(straatnummer);
		aDAO.updateAdres(found);
		return adresToJson(found).build().toString();
		}
		throw new WebApplicationException("Aanbieding not found!");
	}

	@Override
	@POST
	@Produces("application/json")
	public String createAdres(@FormParam("Straat") String straat, @FormParam("Straatnummer") String straatnummer) {
		Adres adres = new Adres(straat, straatnummer);
		aDAO.saveAdres(adres);
		return adresToJson(adres).build().toString();
	}

	private JsonObjectBuilder adresToJson(Adres a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("Straat", a.getStraat());
		job.add("Straatnummer", a.getStraatNummer());
		return job;
	}

}
