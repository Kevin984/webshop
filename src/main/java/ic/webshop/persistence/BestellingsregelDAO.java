package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Bestelling;
import ic.webshop.domain.Bestellingsregel;
import ic.webshop.domain.Product;

public class BestellingsregelDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
private ProductDAO productDAO = new ProductDAO();
private BestellingDAO bestellingDAO = new BestellingDAO();

	private List<Bestellingsregel> selectBestellingsregels(String query){
		List<Bestellingsregel> bestellingsregels = new ArrayList<Bestellingsregel>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			bestellingsregels.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 int aantal = dbResultSet.getInt("Aantal");
				 double totaalprijs = dbResultSet.getDouble("Totaalprijs");
				 int productID = dbResultSet.getInt("Product_ID");
				 int bestellingID = dbResultSet.getInt("Bestelling_ID");
				 
				 Product product = productDAO.findByPK(productID);
				 Bestelling bestelling = bestellingDAO.findBestellingByPK(bestellingID);	
				 Bestellingsregel bestellingsregel = new Bestellingsregel(ID,bestelling,aantal,totaalprijs,product );
				 bestellingsregels.add(bestellingsregel);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return bestellingsregels; 
	}
	
	public List<Bestellingsregel> findAll(){ return selectBestellingsregels("SELECT * FROM public.\"Bestellingsregel\" ORDER BY \"ID\"");}
	
	public Bestellingsregel findBestellingsregelByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectBestellingsregels("SELECT * FROM public.\"Bestellingsregel\"  WHERE \"ID\" = " + ID).get(0);
	}
	
	public void saveBestellingsregel(Bestellingsregel bestellingsregel){
		String query = "INSERT INTO public.\"Bestellingsregel\"(\r\n" + 
				"    \"ID\", \"Aantal\", \"Prijs\", \"Product_ID\", \"Bestelling_ID\")\r\n" + 
				"    VALUES (nextval('bestellingsregel_seq'::regclass), ?, (SELECT \"Prijs\" from public.\"Product\" WHERE \"ID\" = ?) * ?, ?, ?);";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, bestellingsregel.getAantal()); 
			preparedStatement.setInt(2, bestellingsregel.getProduct().getID()); 
			preparedStatement.setInt(3, bestellingsregel.getAantal()); 
			preparedStatement.setInt(1, bestellingsregel.getProduct().getID()); 
			preparedStatement.setInt(1, bestellingsregel.getBestelling().getID()); 	
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			System.out.println("bestellingsregel: " + bestellingsregel.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	public boolean deleteBestellingsregel(Bestellingsregel bestellingsregel){
		boolean result = false;
		boolean exists = findBestellingsregelByPK(bestellingsregel.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Bestellingsregel\" WHERE \"ID\" IN ("+bestellingsregel.getID()+")";	
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				preparedStatement = con.prepareStatement(query);
				if(preparedStatement.executeUpdate() == 1){     
					result = true;
				}
				preparedStatement.close();
				if(stmt.executeUpdate(query) == 1){ 
					result = true;
				}
			} 
			catch (SQLException sqle){
				sqle.printStackTrace(); }
		}
			return result;
	}
	
	
	public boolean updateBestellingsregel(Bestellingsregel bestellingsregel){ 
		boolean result = false;
		boolean exists = findBestellingsregelByPK(bestellingsregel.getID()) != null;
		if(exists){ 
			String query = "UPDATE public.\"Bestellingsregel\" "
			+ " SET \"Aantal\" = '" 		+ bestellingsregel.getAantal()		+"',"
			+ " \"Bestelling_ID\" = '" +bestellingsregel.getBestelling().getID()+"',"
			+ " \"Product_ID\" = '" +bestellingsregel.getProduct().getID()+"'"			
			+ " WHERE \"ID\" = " 	+ bestellingsregel.getID();
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
