package ic.webshop.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import ic.webshop.domain.Categorie;
import ic.webshop.persistence.CategorieDAO;

@Path("/categorieen") 
public class CategorieResource implements CategorieService{
	private CategorieDAO cDAO = new CategorieDAO();
	
	@Override
	public String getCategorieen() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Categorie c : cDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", c.getID());
			job.add("Naam", c.getNaam());
			job.add("Omschrijving", c.getOmschrijving());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	@Override
	@DELETE
	@Path("{ID}") 
	public Response deleteCategorie(@PathParam("ID") int ID) {
		Categorie found = null;
		found = cDAO.findCategorieByPK(ID);
		if(found != null) {
			cDAO.deleteCategorie(found);
			return Response.ok().build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Override
	public String updateCategorie(@FormParam("ID") int ID, @FormParam("Naam") String naam, @FormParam("Omschrijving") String omschrijving) {
		Categorie found = null;
		found = cDAO.findCategorieByPK(ID);		
		if(found != null) {
		found.setNaam(naam);
		found.setOmschrijving(omschrijving);
		cDAO.updateCategorie(found);
		return categorieToJson(found).build().toString();
		}
		throw new WebApplicationException("Aanbieding not found!");
	}

	@Override
	public String createCategorie(@FormParam("Naam") String naam, @FormParam("Omschrijving") String omschrijving) {
		Categorie categorie = new Categorie(null,naam, omschrijving);
		cDAO.saveCategorie(categorie);
		return categorieToJson(categorie).build().toString();
	}

	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getCategorie(@PathParam("ID") int ID) {
		Categorie c = cDAO.findCategorieByPK(ID);
		if(c == null){
			throw new WebApplicationException("Categorie bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", c.getID());
		job.add("Naam",c.getNaam());
		job.add("Omschrijving", c.getOmschrijving());
		return job.build().toString();
	}
	
	private JsonObjectBuilder categorieToJson(Categorie c ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", c.getID());
		job.add("Naam", c.getNaam());
		job.add("Omschrijving", c.getOmschrijving());
		return job;
	}
}