package ic.webshop.domain;

import java.util.Date;

public class Account {
	private int ID;
	private Date openDatum;
	private Klant klant;
	private Adres factuuradres;
	private boolean isActief;
	
	public Account(int ID, Date openDatum, Klant klant, Adres adres, boolean isActief) {
		this.ID = ID;
		this.openDatum = openDatum;
		this.klant = klant;
		this.factuuradres = adres;
		this.isActief = isActief;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public Date getOpenDatum() {
		return openDatum;
	}
	
	public void setOpenDatum(Date datum) {
		this.openDatum = datum;
	}
	
	public Klant getKlant() {
		return klant;
	}
	
	public void setKlant(Klant klant) {
		this.klant = klant;
	}
	
	public Adres getFactuurAdres() {
		return factuuradres;
	}
	
	public void setIsActief(boolean isActief) {
		this.isActief = isActief;
	}
	
	public boolean getIsActief() {
		return isActief;
	}
	
}
