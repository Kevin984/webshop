package ic.webshop.webservices;

import java.util.Date;

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
import ic.webshop.domain.Klant;
import ic.webshop.persistence.AccountDAO;
import ic.webshop.persistence.AdresDAO;
import ic.webshop.persistence.KlantDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
@Path("/accounts")
public class AccountResource implements AccountService{
private AccountDAO aDAO = new AccountDAO();
private KlantDAO klantDAO = new KlantDAO();
private AdresDAO adresDAO = new AdresDAO();

	@Override
	public String getAccounts() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Account a : aDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", a.getID());
			job.add("OpenDatum", a.getOpenDatum().toString());
			String actief;
			if(a.getIsActief()) {
				actief = "Y";
			}else {
				actief="N";
			}
			job.add("IsActief", actief);
			job.add("FactuurAdresID", a.getFactuurAdres().getID());
			job.add("KlantID", a.getKlant().getID());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteAccount(@PathParam("ID")int ID) {
		Account found = null;
		found = aDAO.findAccountByPK(ID);
		
		if(found != null) {
			aDAO.deleteAccount(found);
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
	public String updateAccount(@PathParam("ID")int ID, @FormParam("OpenDatum") Date datum, @FormParam("IsActief") String actief, @FormParam("FactuurAdresID") int adresID, @FormParam("KlantID") int klantID) {
	Account found = null;
	found = aDAO.findAccountByPK(ID);
	if(found != null) {
		
		boolean isactief = false;
		 if(actief.equalsIgnoreCase("Y")) {
			 isactief = true;
		 }
		
		Klant klant = klantDAO.findKlantByPK(klantID);
		Adres adres = adresDAO.findAdresByPK(adresID);
		
		found.setIsActief(isactief);
		found.setKlant(klant);
		found.setOpenDatum(datum);
		found.setFactuurAdres(adres);

	aDAO.updateAccount(found);
	return accountToJson(found).build().toString();
	}
	throw new WebApplicationException("Adres not found!");
		
	}

	@Override
	@POST
	@Produces("application/json")
	public String createAccount(@FormParam("OpenDatum") Date datum, @FormParam("IsActief") String actief, @FormParam("FactuurAdresID") int adresID, @FormParam("KlantID") int klantID) {
		
		 boolean isactief = false;
		 if(actief.equalsIgnoreCase("Y")) {
			 isactief = true;
		 }
		 
		 Klant klant = klantDAO.findKlantByPK(klantID);
			Adres adres = adresDAO.findAdresByPK(adresID);
		 
		Account account = new Account(datum, klant,adres , isactief);
		aDAO.saveAccount(account);
		return accountToJson(account).build().toString();
	}
	
	
	private JsonObjectBuilder accountToJson(Account a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("OpenDatum", a.getOpenDatum().toString());
		String actief;
		if(a.getIsActief()) {
			actief = "Y";
		}else {
			actief="N";
		}
		job.add("IsActief", actief);
		job.add("FactuurAdresID", a.getFactuurAdres().getID());
		job.add("KlantID", a.getKlant().getID());
		return job;
	}
	@GET
	@Path("username/{username}")
	@Produces("application/json")
	public String getAccountByUsername(@PathParam("username") String username) {
		Account a = aDAO.findAccountByUsername(username);
		if(a == null){
			throw new WebApplicationException("Account bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("OpenDatum", a.getOpenDatum().toString());
		String actief;
		if(a.getIsActief()) {
			actief = "Y";
		}else {
			actief="N";
		}
		job.add("IsActief", actief);
		job.add("FactuurAdresID", a.getFactuurAdres().getID());
		job.add("KlantID", a.getKlant().getID());
		return job.build().toString();
	}
	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getAccount(@PathParam("ID")int ID) {
		Account a = aDAO.findAccountByPK(ID);
		if(a == null){
			throw new WebApplicationException("Account bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("OpenDatum", a.getOpenDatum().toString());
		String actief;
		if(a.getIsActief()) {
			actief = "Y";
		}else {
			actief="N";
		}
		job.add("IsActief", actief);
		job.add("FactuurAdresID", a.getFactuurAdres().getID());
		job.add("KlantID", a.getKlant().getID());
		return job.build().toString();
	}

	@GET
	@Path("usernamebytoken/{token}")
	public String getUsername(@PathParam("token") String token) {
		try {
			JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
			Claims claims = parser.parseClaimsJws(token).getBody();
			
			String username = claims.getSubject();
			
			return username;
		} catch(ExpiredJwtException e) {
			return "expiredToken";
		}

	}
}
