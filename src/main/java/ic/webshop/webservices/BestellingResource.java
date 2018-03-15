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

import ic.webshop.domain.Account;
import ic.webshop.domain.Adres;
import ic.webshop.domain.Bestelling;
import ic.webshop.persistence.AccountDAO;
import ic.webshop.persistence.AdresDAO;
import ic.webshop.persistence.BestellingDAO;
@Path("/bestellingen") 

public class BestellingResource implements BestellingService{
private BestellingDAO bDAO = new BestellingDAO();
private AdresDAO adresDAO = new AdresDAO();
private AccountDAO accountDAO = new AccountDAO();

	@Override
	public String getBestellingen() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Bestelling b : bDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", b.getID());
			job.add("AdresID", b.getAdres().getID());
			job.add("AccountID", b.getAccount().getID());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteBestelling(@PathParam("ID")int ID) {
		Bestelling found = null;
		found = bDAO.findBestellingByPK(ID);
		
		if(found != null) {
			bDAO.deleteBestelling(found);
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
	public String updateBestelling(@PathParam("ID")int ID, @FormParam("AdresID") int adresID, @FormParam("AccountID") int accountID) {
		Bestelling found = null;
		found = bDAO.findBestellingByPK(ID);
		if(found != null) {
			
			Adres adres = adresDAO.findAdresByPK(adresID);
			Account account = accountDAO.findAccountByPK(accountID);
			
			if(adres != null) {
			found.setAdres(adres);
			}
			if(account != null) {
			found.setAccount(account);
			}
			
		bDAO.updateBestelling(found);
		return bestellingToJson(found).build().toString();
		}
		throw new WebApplicationException("Bestelling not found!");
	}

	@Override
	@POST
	@Produces("application/json")
	public String createBestelling(@FormParam("AdresID") int adresID, @FormParam("AccountID") int accountID) {
		
		Adres adres = adresDAO.findAdresByPK(adresID);
		Account account = accountDAO.findAccountByPK(accountID);
		Bestelling bestelling = new Bestelling(adres, account);
		bDAO.saveBestelling(bestelling);
		return bestellingToJson(bestelling).build().toString();
	}

	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getBestelling(int ID) {
		Bestelling b = bDAO.findBestellingByPK(ID);
		if(b == null){
			throw new WebApplicationException("Bestelling bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", b.getID());
		job.add("AdresID", b.getAdres().getID());
		job.add("AccountID", b.getAccount().getID());
		return job.build().toString();
	}
	
	private JsonObjectBuilder bestellingToJson(Bestelling b ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", b.getID());
		job.add("AdresID", b.getAdres().getID());
		job.add("AccountID", b.getAccount().getID());
		return job;
	}

}
