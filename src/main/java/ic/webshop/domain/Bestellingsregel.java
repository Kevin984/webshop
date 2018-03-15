package ic.webshop.domain;

public class Bestellingsregel {
	private int ID;
	private Bestelling bestelling;
	private double totaalprijs;
	private int aantal;
	private Product product;
	
	public Bestellingsregel(int ID, Bestelling bestelling, int aantal, double totaalprijs, Product product) {
		this.ID = ID;
		this.bestelling = bestelling;
		this.aantal = aantal;
		this.totaalprijs = totaalprijs;
		this.product = product;
	}
	
	public Bestellingsregel(Bestelling bestelling, int aantal, double totaalprijs, Product product) {
		this.bestelling = bestelling;
		this.aantal = aantal;
		this.totaalprijs = totaalprijs;
		this.product = product;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public Bestelling getBestelling() {
		return bestelling;
	}
	
	public void setBestelling(Bestelling bestelling) {
		this.bestelling = bestelling;
	}
	
	public double getTotaalPrijs() {
		return totaalprijs;
	}
	
	public void setTotaalPrijs(double totaalprijs) {
		this.totaalprijs = totaalprijs;
	}
	
	public int getAantal() {
		return aantal;
	}
	
	public void setAantal(int aantal) {
		this.aantal = aantal;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

}
