package ic.webshop.domain;

public class Klant {
	private int ID;
	private Adres adres;
	private String afbeelding;
	
	public Klant(int ID, Adres adres) {
		this.ID = ID;
		this.adres = adres;
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
