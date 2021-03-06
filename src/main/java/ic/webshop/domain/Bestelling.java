package ic.webshop.domain;

public class Bestelling {
	private int ID;
	private Adres adres;
	private Account account;
	private String ordernummer;
	
	public Bestelling(int ID, Adres adres, Account account) {
		this.ID = ID;
		this.adres = adres;
		this.account = account;
	}
	
	public Bestelling(Adres adres, Account account) {
		this.adres = adres;
		this.account = account;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public void setOrdernummer(String on) {
		this.ordernummer = on;
	}
	
	public String getOrdernummer() {
		return ordernummer;
	}
	
	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	
	public Adres getAdres() {
		return adres;
	}
	
	public int getID() {
		return ID;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
}
