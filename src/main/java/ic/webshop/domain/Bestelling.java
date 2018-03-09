package ic.webshop.domain;

public class Bestelling {
	private int ID;
	private Adres adres;
	private Account account;
	
	public Bestelling(int ID, Adres adres, Account account) {
		this.ID = ID;
		this.adres = adres;
		this.account = account;
	}
	
	public void setID(int ID) {
		this.ID = ID;
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
