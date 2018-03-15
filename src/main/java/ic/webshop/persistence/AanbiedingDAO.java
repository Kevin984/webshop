package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ic.webshop.domain.Aanbieding;
import ic.webshop.domain.Adres;
import ic.webshop.domain.Product;

public class AanbiedingDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
private ProductDAO productDAO = new ProductDAO();

	private List<Aanbieding> selectAanbiedingen(String query){
		List<Aanbieding> aanbiedingen = new ArrayList<Aanbieding>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			aanbiedingen.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 Date vanDatum = dbResultSet.getDate("VanDatum");
				 Date totDatum = dbResultSet.getDate("TotDatum");
				 Double aanbiedingprijs = dbResultSet.getDouble("Aanbiedingsprijs");

				 String reclametekst = dbResultSet.getString("Reclametekst");
				 int productID = dbResultSet.getInt("Product_ID");				 
				 
				 Product product = productDAO.findByPK(productID);
				 Aanbieding aanbieding = new Aanbieding(ID, product, aanbiedingprijs, vanDatum, totDatum, reclametekst);
				 aanbiedingen.add(aanbieding);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return aanbiedingen; 
	}
	
	public List<Aanbieding> findAll(){ return selectAanbiedingen("SELECT * FROM public.\"Aanbieding\" ORDER BY \"ID\"");}
	
	public Aanbieding findAanbiedingByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectAanbiedingen("SELECT * FROM public.\"Aanbieding\" WHERE \"ID\" = " + ID).get(0);
	}
	
	public List<Aanbieding> findAanbiedingByProductID(int ID){
		return selectAanbiedingen("SELECT * FROM public.\"Aanbieding\" WHERE \"PRODUCT_ID\" = " + ID);
	}
	
	public void saveAanbieding(Aanbieding aanbieding){
			String query = "INSERT INTO public.\"Aanbieding\"(\r\n" + 
					"	\"ID\", \"VanDatum\", \"TotDatum\", \"Aanbiedingsprijs\", \"Reclametekst\", \"Product_ID\")\r\n" + 
					"	VALUES (nextval('aanbieding_seq'::regclass),?,?,?,?,?);";
			try (Connection con = super.getConnection()) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setDate(1, (java.sql.Date) aanbieding.getVanDatum()); 
				preparedStatement.setDate(2, (java.sql.Date) aanbieding.getTotDatum()); 
				preparedStatement.setDouble(3, aanbieding.getAanbiedingPrijs()); 
				preparedStatement.setString(4, aanbieding.getReclameTekst()); 
				preparedStatement.setInt(5, aanbieding.getProduct().getID()); 

				preparedStatement.executeUpdate();	
				preparedStatement.close();

				System.out.println("Artikel: " + aanbieding.getID()  + " saved.");
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}	
	}
		
		
		public boolean deleteAanbieding(Aanbieding aanbieding){
			boolean result = false;
			boolean artikelExists = findAanbiedingByPK(aanbieding.getID()) != null;
			
			if(artikelExists){
				
				String query = "DELETE FROM public.\"Aanbieding\" WHERE \"ID\" IN ("+aanbieding.getID()+")";	
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
		
		
		public boolean updateAanbieding(Aanbieding aanbieding){ 
			boolean result = false;
			boolean exists = findAanbiedingByPK(aanbieding.getID()) != null; //zoek artikel met de primary keys
			
			if(exists){ 
				String query = "UPDATE public.\"Aanbieding\" "
				+ " SET \"VanDatum\" = '" 		+ aanbieding.getVanDatum()		+"',"
				+ " \"TotDatum\" = '" +aanbieding.getTotDatum()+"',"
				+ " \"Aanbiedingsprijs\" = " +aanbieding.getAanbiedingPrijs()+","
				+ " \"Reclametekst\" = '" +aanbieding.getReclameTekst()+"',"
				+ " \"Product_ID\" = " +aanbieding.getProduct().getID()+""
						
				+ " WHERE \"ID\" = " 	+ aanbieding.getID();
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
