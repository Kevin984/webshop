package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Categorie;
import ic.webshop.domain.Product;

public class ProductCategorieDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private CategorieDAO cDAO = new CategorieDAO();
	
	private List<Product> selectProductCategorieen(String query){
		List<Product> producten = new ArrayList<Product>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			producten.clear();
			while(dbResultSet.next()){ 
			//	 int ID = dbResultSet.getInt("ID");
			//	 int productID = dbResultSet.getInt("Product_ID");
				 int categorieID = dbResultSet.getInt("Categorie_ID");
				 int prID = dbResultSet.getInt("Product_ID");
				 String naam = dbResultSet.getString("Naam");
				 String omschrijving = dbResultSet.getString("Omschrijving");
			//	 String afbeelding = dbResultSet.getString("Afbeelding");
				 double prijs = dbResultSet.getDouble("Prijs");
				 Product product = new Product(prID, naam, omschrijving, prijs);
				 System.out.println("888883- " + prID + naam + omschrijving + prijs);
				 Categorie categorie = cDAO.findCategorieByPK(categorieID);	
				 product.voegCategorieToe(categorie);
				 producten.add(product);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return producten; 
	}
	
	public List<Product> findAll(){ return selectProductCategorieen("SELECT * FROM public.\"Product_Categorie\" as pc INNER JOIN  public.\"Product\" as p ON pc.\"Product_ID\" = p.\"ID\";");}
	
	public List<Product> getProductenByCategorie(int ID){ return selectProductCategorieen("SELECT * FROM public.\"Product_Categorie\" as pc INNER JOIN  public.\"Product\" as p ON pc.\"Product_ID\" = p.\"ID\" WHERE \"Categorie_ID\" = "+ID+";");}

	
	public Product findProductCategorieBypIDcID(int ID, int ID2){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectProductCategorieen("SELECT * FROM public.\"Product_Categorie\" as pc INNER JOIN  public.\"Product\" as p ON pc.\"Product_ID\" = p.\"ID\" WHERE \"Product_ID\" = "+ID+" AND \"Categorie_ID\" = "+ID2+";").get(0);

//		return selectProductCategorieen("SELECT * FROM public.\"Product_Categorie\"  WHERE \"Product_ID\" = " + ID + " AND \"Categorie_ID\" = " + ID2 ).get(0);
	}
	
	public void saveProductCategorie(Product product, Categorie categorie){
		System.out.println("5 Product: " + product.getID() + ", " + product.getNaam()  +", " + product.getOmschrijving());
		String query = "INSERT INTO public.\"Product_Categorie\"(\r\n" + 
				"    \"ID\", \"Product_ID\", \"Categorie_ID\")\r\n" + 
				"    VALUES (nextval('productcategorie_seq'::regclass), ?, ?);";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, product.getID()); 			
			preparedStatement.setInt(2, categorie.getID()); 
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
		System.out.println("6 Product: " + product.getID() + ", " + product.getNaam()  +", " + product.getOmschrijving());

}
	
	public boolean deleteProductCategorie(Product product, Categorie categorie){
		boolean result = false;
		boolean exists = findProductCategorieBypIDcID(product.getID(), categorie.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Product_Categorie\" WHERE \"Product_ID\" IN ("+product.getID()+") AND \"Categorie_ID\" IN ("+categorie.getID()+")";	
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
	
	
	public boolean deleteProductCategorieByCategorieID( Categorie categorie){
		boolean result = false;
boolean exists = true;		
		if(exists){	
			String query = "DELETE FROM public.\"Product_Categorie\" WHERE \"Categorie_ID\" IN ("+categorie.getID()+")";	
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				preparedStatement = con.prepareStatement(query);
					result = true;
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
	
	public boolean deleteProductCategorieByProductID(Product product){
		boolean result = false;
		boolean exists = true;
		
		if(exists){	
			String query = "DELETE FROM public.\"Product_Categorie\" WHERE \"Product_ID\" IN ("+product.getID()+")";	
			try(Connection con = super.getConnection()){
				Statement stmt = con.createStatement();
				preparedStatement = con.prepareStatement(query);
					result = true;
				
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
	
	public boolean updateProductCategorie(Product product, Categorie categorie){ 
		boolean result = false;
		boolean exists = findProductCategorieBypIDcID(product.getID(), categorie.getID()) != null;
		if(exists){ 
			String query = "UPDATE public.\"Product_Categorie\" "
			+ " SET \"Product_ID\" = '" 		+ product.getID()		+"',"
			+ " \"Categorie_ID\" = '" +categorie.getID()+"'"
			+ " WHERE \"Product_ID\" = " 	+ product.getID() + " AND \"Categorie_ID\" = " +categorie.getID();
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
