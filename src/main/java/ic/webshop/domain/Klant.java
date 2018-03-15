package ic.webshop.domain;

public class Klant {
	private int ID;
	private String naam;
	private Adres adres;
	private String afbeelding;
	
	public Klant(int ID, String naam, String afbeelding, Adres adres) {
		this.ID = ID;
		this.naam = naam;
		this.afbeelding = afbeelding;
		this.adres = adres;
	}
	
	public Klant(String naam, String afbeelding, Adres adres) {
		this.naam = naam;
		this.afbeelding = afbeelding;
		this.adres = adres;
	}
	
	public String getNaam() {
		return naam;
	}
	
	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public int getID() {
		return ID;
	}
	
	public Adres getAdres() {
		return adres;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	
	public void setAfbeelding(String afbeelding) {
		this.afbeelding = afbeelding;
	}
	
	public String getAfbeelding() {
		return afbeelding;
	}
}
