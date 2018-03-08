package ic.webshop.webservices;

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
@Path("/producten") 
public class ProductResource {
ProductService productService = ProductServiceProvider.getProductService();
	
	@GET
	//@RolesAllowed({"user", "admin"}) << staat tussen comments omdat er geen rechten worden gegeven, zelfs wanneer je inlogt als user/admin. Bij delete en GET werkt dit wel, POST en PUT niet
	@Produces("application/json")
	public String getVoorraad(){
		ProductService service = ProductServiceProvider.getProductService();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for(Product a : service.getVoorraad()){ 
			//maak json van alle gegevens van elk artikel
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("ID", a.getID());
			job.add("Naam", a.getNaam());
			job.add("Omschrijving", a.getOmschrijving());
		//	job.add("Afbeelding", a.getBlobPlaatje());
			job.add("Prijs", a.getPrijs());

			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	
	@GET
//	@RolesAllowed({"user", "admin"}) 
	@Path("{ID}")
	@Produces("application/json")
	public String getArtikelInfo(@PathParam("ID") int ID){
		ProductService service = ProductServiceProvider.getProductService();
		Product a = service.getArtikelByPK(ID);
		
		if(a == null){
			throw new WebApplicationException("Artikel bestaat niet!");
		}
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("Naam", a.getNaam());
		
		return job.build().toString();
	} 
	/*
	@POST
//	@RolesAllowed({"user", "admin"})
	@Produces("application/json")
	public String createArtikel(@FormParam("ID")int ID, @FormParam("Naam") String nm, @FormParam("Categorie") String cat, @FormParam("Maat") String maat, @FormParam("Kleur") String kleur, @FormParam("Merk")String merk ,@FormParam("Inkoopprijs") double ink, @FormParam("Verkoopprijs") double verk, @FormParam("Aantal") int aant){
		Artikel newArtikel = new Artikel(ID, nm, cat, maat, kleur, merk, ink, verk, aant);
		Product product = new Product();
		productService.saveArtikel(newArtikel);
		return artikelToJson(newArtikel).build().toString();
	}
	*/
	
	private JsonObjectBuilder artikelToJson(Product a ){
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("ID", a.getID());
		job.add("Naam", a.getNaam());

		return job;
	}
	
	
	@DELETE
	@RolesAllowed({"user", "admin"}) 
	//hier doen de rollen het wel, alleen user en admin kunnen dus een artikel verwijderen
	@Path("{ID}/{Maat}/{Kleur}")
	public Response deleteArtikel(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur") String kleur ){
		Product found = null;
		
		for (Product a : productService.getVoorraad()){
			if (a.getID() == ID ){
				found = a;
				break;
			}
		}
		
		if(found == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {
			productService.deleteArtikel(ID);
		return Response.ok().build();
		}
	}
	
	
	//hieronder staan 2 geprobeerde update functies, beide werken niet (403 forbidden error)
	/*
	@PUT
	@Path("update/{ID}/{Maat}/{Kleur}")
	@RolesAllowed({"user","admin"})
	@Produces("application/json")
	public Response updateArtikel(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur")String kleur,
			@FormParam("Naam") String naam, @FormParam("Categorie") String categorie, @FormParam("Merk") String merk,
			@FormParam("Inkoopprijs") double inkoopprijs, @FormParam("Verkoopprijs") double verkoopprijs, @FormParam("Aantal") int aantal){
		Artikel found = null;
		
		for(Artikel a : artikelService.getVoorraad()){
			if (a.getArtikelID() == ID && a.getMaat().equals(maat) && a.getKleur().equals(kleur)){
				found = a;
				break;
				}
		}
		if(found == null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {	
			found.setArtikelNaam(naam);
			found.setArtikelCategorie(categorie);
			found.setArtikelMerk(merk);
			found.setInkoopprijs(inkoopprijs);
			found.setVerkoopprijs(verkoopprijs);
			found.setAantal(aantal);
			
			artikelService.updateArtikel(found);
		return Response.ok().build();
	}
	}*/
	
	/*
	@PUT
	@Path("{ID}/{Maat}/{Kleur}")
	@RolesAllowed({"user","admin"})
	@Produces("application/json")
	public Response updateArtikel(){
		
	}
*/
		/*
	@PUT
	@RolesAllowed({"user", "admin"})
	@Path("update/{ID}/{Maat}/{Kleur}")
	@Produces("application/json")
	public String updateArtikel(@PathParam("ID") int ID, @PathParam("Maat")String maat, @PathParam("Kleur")String kleur,
	@FormParam("Naam") String naam, @FormParam("Categorie") String categorie, @FormParam("Merk") String merk,
	@FormParam("Inkoopprijs") double inkoopprijs, @FormParam("Verkoopprijs") double verkoopprijs, @FormParam("Aantal") int aantal){
		
		Artikel found = null;
		for (Artikel a : artikelService.getVoorraad()){
			if (a.getArtikelID() == ID && a.getMaat().equals(maat) && a.getKleur().equals(kleur)){
				
			    a.setArtikelNaam(naam);
				a.setArtikelCategorie(categorie);
				a.setArtikelMerk(merk);
				a.setInkoopprijs(inkoopprijs);
				a.setVerkoopprijs(verkoopprijs);
				a.setAantal(aantal);
				
			//	artikelService.updateArtikel(a);
				return artikelToJson(found).build().toString();	
			}
		}
		throw new WebApplicationException("Artikel niet gevonden");
	}
*/
}
