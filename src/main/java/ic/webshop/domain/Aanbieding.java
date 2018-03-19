package ic.webshop.domain;

import java.util.Date;

public class Aanbieding {
	private int ID;
private Product product;
private double aanbiedingPrijs;
private Date vanDatum, totDatum;
private String reclameTekst;

public Aanbieding(int ID, Product product, double aanbiedingPrijs, Date vanDatum, Date totDatum, String reclameTekst) {
	this.ID = ID;
	this.product = product;
	this.aanbiedingPrijs = aanbiedingPrijs;
	this.vanDatum = vanDatum;
	this.totDatum = totDatum;
	this.reclameTekst = reclameTekst;
}
public Aanbieding(Product product, double aanbiedingPrijs, Date vanDatum, Date totDatum, String reclameTekst) {
	
	this.product = product;
	this.aanbiedingPrijs = aanbiedingPrijs;
	this.vanDatum = vanDatum;
	this.totDatum = totDatum;
	this.reclameTekst = reclameTekst;
}
public int getID() {
	return ID;
}

public Product getProduct() {
	return product;
}

public double getAanbiedingPrijs() {
	return aanbiedingPrijs;
}

public Date getVanDatum() {
	return vanDatum;
}

public Date getTotDatum() {
	return totDatum;
}

public String getReclameTekst() {
	return reclameTekst;
}

public void setID(int ID) {
	this.ID = ID;
}

public void setProduct(Product product) {
	this.product = product;
}

public void setAanbiedingprijs(double prijs) {
	this.aanbiedingPrijs = prijs;
}

public void setVanDatum(Date datum) {
	this.vanDatum = datum;
}

public void setTotDatum(Date datum) {
	this.totDatum = datum;
}

public void setReclameTekst(String tekst) {
	this.reclameTekst = tekst;
}

public boolean checkIfGeldig() {
	Date sysdate = new Date();
	return (sysdate.after(vanDatum) && sysdate.before(totDatum));
}

}
