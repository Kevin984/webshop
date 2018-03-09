package ic.webshop.domain;

public class Categorie {
	private int ID;
	private String afbeelding, naam, omschrijving;
	

	public Categorie(int ID, String afbeelding, String naam,String omschrijving) {
		this.ID = ID;
		this.afbeelding = afbeelding;
		this.naam = naam;
		this.omschrijving = omschrijving;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getAfbeelding() {
		return afbeelding;
	}
	
	public void setAfbeelding(String plaatje) {
		this.afbeelding = plaatje;
	}
	
	public String getNaam() {
		return naam;
	}
	
	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public void setOmschrijving(String omschr) {
		this.omschrijving = omschr;
	}
	public String getOmschrijving() {
		return omschrijving;
	}
	
}

