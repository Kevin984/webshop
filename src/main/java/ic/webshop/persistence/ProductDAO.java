package ic.webshop.persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ic.webshop.domain.Product;

public class ProductDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;

	private List<Product> selectArtikelen(String query){
		List<Product> producten = new ArrayList<Product>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			producten.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 String naam = dbResultSet.getString("Naam");
				 String omschrijving = dbResultSet.getString("Omschrijving");
			//	 String afbeelding = dbResultSet.getString("Afbeelding");
				 double prijs = dbResultSet.getDouble("Prijs");
				 Product product = new Product(ID, naam, omschrijving, prijs);
				 producten.add(product);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return producten; 
	}
	
	public List<Product> findAll(){
		return selectArtikelen("SELECT \"ID\", \"Naam\", \"Omschrijving\", \"Afbeelding\", \"Prijs\" FROM public.\"Product\" ORDER BY \"ID\"");
	}
	
	//gebruik de primary keys (id, maat, kleur) van artikel om een specifiek artikel te vinden
	public Product findByPK(int ID){ 	
		return selectArtikelen("SELECT \"ID\", \"Naam\", \"Categorie\", \"Maat\", \"Kleur\", \"Merk\", \"Inkoopprijs\", \"Verkoopprijs\", \"Aantal\" "
			+ "FROM public.\"Artikel\" " 
				+ " WHERE \"ID\" = " + ID).get(0);
	}
	 
	public void saveArtikel(Product artikel){
		String query = "INSERT INTO public.\"Product\" (\"ID\", \"Afbeelding\", \"Naam\", \"Omschrijving\",\"Prijs\")  VALUES(?,?,?,?,?)"; 
		
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			// eerste vraagteken = 1
			preparedStatement.setInt(1, artikel.getID()); 
			preparedStatement.setString(1, artikel.getBlobPlaatje()); 
			preparedStatement.setString(1, artikel.getNaam()); 
			preparedStatement.setString(1, artikel.getOmschrijving()); 
			preparedStatement.setDouble(1, artikel.getPrijs()); 

			preparedStatement.executeUpdate();	
			preparedStatement.close();

			System.out.println("Artikel: " + artikel.getNaam()  + " saved.");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	
	public boolean deleteArtikel(Product artikel){
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getID()) != null;
		
		if(artikelExists){
			
			String query = "DELETE FROM public.\"Artikel\" WHERE \"ID\" IN ("+artikel.getID()+"))";
			String query2 = "DELETE FROM public.\"VerkoopRegel\" WHERE \"Artikel_ID\" IN (?) AND \"Artikel_Maat\" IN (?) AND \"Artikel_Kleur\" IN (?)";
			
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				//verwijder eerst alle verkoopregels met hetzelfde artikel omdat ze gelinkt zijn aan artikel
				preparedStatement = con.prepareStatement(query2); 
				
				preparedStatement.setInt(1, artikel.getID());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				preparedStatement = con.prepareStatement(query);
				//verwijder nu het artikel
				if(preparedStatement.executeUpdate() == 1){     
					result = true;
				}
				preparedStatement.close();
				
				
				//als er niet meer en niet minder dan 1 regel is verwijderd, result = true
				if(stmt.executeUpdate(query) == 1){ 
					result = true;
					
				
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}
	
	/*
	 public boolean deleteArtikel(Product artikel){
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getID()) != null;
		
		if(artikelExists){
			String query = "DELETE FROM public.\"Artikel\" WHERE \"ID\" = " + artikel.getID();
			String query2 ="DELETE FROM public.\"Verkoop\" WHERE \"Klant_ID\" IN (?)";
			String query3 = "DELETE FROM public.\"VerkoopRegel\" WHERE \"ID\" IN ((SELECT \"ID\" FROM public.\"Verkoop\" WHERE \"Klant_ID\" = ?))";
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){
					result = true;
					
				
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}*/
	 
	
	//deze functie zou eerst gebruikt worden voor Use Case: wijzig artikel maar wordt nu gebruikt om het aantal van het artikel te verlagen wanneer er iets verkocht wordt
	public boolean updateArtikel(Product artikel){ 
		boolean result = false;
		boolean artikelExists = findByPK(artikel.getID()) != null; //zoek artikel met de primary keys
		
		//update artikel in database
		if(artikelExists){ 
			String query = "UPDATE public.\"Artikel\" "
			+ " SET \"Naam\" = '" 		+ artikel.getNaam()			+"'," 
			+ " WHERE \"ID\" = " 		+ artikel.getID();

			
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				if(stmt.executeUpdate(query) == 1){
					result = true;
				}
			}
			
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
			return result;
		
	}
}