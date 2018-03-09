package ic.webshop.domain;

public class Adres {
	private int ID;
	private String straat;
	private String straatnummer;
	
	public Adres(int ID, String straat, String straatnummer) {
		this.ID = ID;
		this.straat = straat;
		this.straatnummer = straatnummer;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getStraat() {
		return straat;
	}
	
	public String getStraatNummer() {
		return straatnummer;
	}
	
	public void setID(int ID) {
		this.ID= ID;
		
	}
	
	public void setStraat(String straat) {
		this.straat = straat;
	}
	
	public void setStraatNummer(String straatnummer) {
		this.straatnummer = straatnummer;
	}
}
