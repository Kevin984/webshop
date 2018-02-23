package ic.webshop.domain;

import java.util.ArrayList;

public class Product {
private int ID;
private String blobPlaatje, naam, omschrijving;
double prijs;
private ArrayList<Categorie> categorieen;

public Product(int ID, String blobPlaatje, String naam, String omschrijving, double prijs) {
	this.ID = ID;
	this.blobPlaatje = blobPlaatje;
	this.naam =naam;
	this.omschrijving = omschrijving;
	this.prijs=prijs;
	categorieen = new ArrayList<Categorie>();
}

public void setID(int ID) {this.ID = ID; }
public int getID() {return ID;}
public void setBlobPlaatje(String blobPlaatje) {this.blobPlaatje = blobPlaatje; }
public String getBlobPlaatje() {return blobPlaatje;}
public void setNaam(String naam) {this.naam = naam; }
public String getNaam() {return naam;}
public void setOmschrijving(String omschrijving) {this.omschrijving = omschrijving; }
public String getOmschrijving() {return omschrijving;}
public void setPrijs(double prijs) {this.prijs = prijs; }
public double getPrijs() {return prijs;}
public boolean voegCategorieToe(Categorie cat) {
	boolean result = false;
	if(!categorieen.contains(cat)) {
	categorieen.add(cat); 
	result = true;}
	return result;
	 }
public boolean verwijderCategorie(Categorie cat) {
	boolean result = false;
	if(categorieen.contains(cat)) {
	categorieen.remove(cat); 
	result = true;}
	return result;
}
public ArrayList<Categorie> getCategories() {return categorieen;}
}
