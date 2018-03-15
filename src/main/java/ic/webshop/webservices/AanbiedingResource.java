package ic.webshop.webservices;

import java.util.Date;
import java.util.List;

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
import ic.webshop.domain.Aanbieding;
import ic.webshop.domain.Product;
import ic.webshop.persistence.AanbiedingDAO;
import ic.webshop.persistence.ProductDAO;

@Path("/aanbiedingen") 
public class AanbiedingResource implements AanbiedingService{
	private AanbiedingDAO aDAO = new AanbiedingDAO();
	private ProductDAO pDAO = new ProductDAO();
	
	@Override
	public String getAanbiedingen() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Aanbieding a : aDAO.findAll()){ 
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", a.getID());
			job.add("VanDatum", a.getVanDatum().toString());
			job.add("TotDatum", a.getTotDatum().toString());
			job.add("ReclameTekst", a.getReclameTekst());
			job.add("Aanbiedingsprijs", a.getAanbiedingPrijs());
			job.add("ProductID", a.getProduct().getID());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@Override
	@GET
	@Path("{ID}")
	@Produces("application/json")
	public String getAanbieding(@PathParam("ID") int ID){
		Aanbieding a = aDAO.findAanbiedingByPK(ID);
		if(a == null){
			throw new WebApplicationException("Aanbieding bestaat niet!");
		}
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("VanDatum", a.getVanDatum().toString());
		job.add("TotDatum", a.getTotDatum().toString());
		job.add("ReclameTekst", a.getReclameTekst());
		job.add("Aanbiedingsprijs", a.getAanbiedingPrijs());
		job.add("ProductID", a.getProduct().getID());
		return job.build().toString();
	} 
	
	@Override
	@GET
	@Path("/product/{ID}")
	@Produces("application/json")
	public String getAanbiedingByProductID(@PathParam("ID") int ID){
		JsonArrayBuilder jab = Json.createArrayBuilder();
		List<Aanbieding> aanbiedingen = aDAO.findAanbiedingByProductID(ID);
		if(aanbiedingen.isEmpty()){
			throw new WebApplicationException("Aanbieding bestaat niet!");
		} else {
			for(Aanbieding a : aanbiedingen){ 
				JsonObjectBuilder job = Json.createObjectBuilder();
				job.add("ID", a.getID());
				job.add("VanDatum", a.getVanDatum().toString());
				job.add("TotDatum", a.getTotDatum().toString());
				job.add("ReclameTekst", a.getReclameTekst());
				job.add("Aanbiedingsprijs", a.getAanbiedingPrijs());
				job.add("ProductID", a.getProduct().getID());
				jab.add(job);
			}
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@Override
	@DELETE
	@Path("{ID}")
	public Response deleteAanbieding(@PathParam("ID") int ID) {
		Aanbieding found = null;
		found = aDAO.findAanbiedingByPK(ID);
		
		if(found != null) {
			aDAO.deleteAanbieding(found);
			return Response.ok().build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Override
	public String updateAanbieding(@PathParam("ID") int ID, @FormParam("VanDatum") Date vdatum, @FormParam("TotDatum") Date tdatum
			, @FormParam("AanbiedingsPrijs") double prijs, @FormParam("ReclameTekst") String reclametekst, @FormParam("Product_ID") int productID) {
		Aanbieding found = null;
		found = aDAO.findAanbiedingByPK(ID);
		if(found != null) {
			found.setAanbiedingprijs(prijs);
			found.setReclameTekst(reclametekst);
			found.setVanDatum(vdatum);
			found.setTotDatum(tdatum);
			Product product = pDAO.findByPK(productID);
			if(product != null) {
			found.setProduct(product);
			}
		
		aDAO.updateAanbieding(found);
		return aanbiedingToJson(found).build().toString();
		}
		throw new WebApplicationException("Aanbieding not found!");
	}

	@Override
	public String createAanbieding(@FormParam("VanDatum") Date vdatum, @FormParam("TotDatum") Date tdatum
			, @FormParam("AanbiedingsPrijs") double prijs, @FormParam("ReclameTekst") String reclametekst, @FormParam("Product_ID") int productID) {
		
		
		Product product = pDAO.findByPK(productID);
		
		
Aanbieding aanbieding = new Aanbieding(null, prijs, vdatum, tdatum, reclametekst);
if(product != null) {
	aanbieding.setProduct(product);
}
aDAO.saveAanbieding(aanbieding);
return aanbiedingToJson(aanbieding).build().toString();
	}

	
	private JsonObjectBuilder aanbiedingToJson(Aanbieding a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("VanDatum", a.getVanDatum().toString());
		job.add("TotDatum", a.getTotDatum().toString());
		job.add("ReclameTekst", a.getReclameTekst());
		job.add("Aanbiedingsprijs", a.getAanbiedingPrijs());
		job.add("ProductID", a.getProduct().getID());
		return job;
	}
}
