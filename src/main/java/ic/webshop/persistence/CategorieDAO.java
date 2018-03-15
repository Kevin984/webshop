package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Categorie;

public class CategorieDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;

	private List<Categorie> selectCategorieen(String query){
		List<Categorie> categorieen = new ArrayList<Categorie>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			categorieen.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 String afbeelding = dbResultSet.getString("Afbeelding");
				 String omschrijving = dbResultSet.getString("Omschrijving");
				 String naam = dbResultSet.getString("Naam");
				 Categorie categorie = new Categorie(ID, afbeelding, naam, omschrijving);
				 categorieen.add(categorie);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return categorieen; 
	}
	
	public List<Categorie> findAll(){ return selectCategorieen("SELECT * FROM public.\"Categorie\" ORDER BY \"ID\"");}
	
	public Categorie findCategorieByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectCategorieen("SELECT * FROM public.\"Categorie\"  WHERE \"ID\" = " + ID).get(0);
	}
	
	public void saveCategorie(Categorie categorie){
		String query = "INSERT INTO public.\"Categorie\"(\r\n" + 
				"    \"ID\", \"Naam\", \"Omschrijving\", \"Afbeelding\")\r\n" + 
				"    VALUES (nextval('categorie_seq'::regclass), '?', '?', null);";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, categorie.getNaam()); 
			preparedStatement.setString(2, categorie.getOmschrijving()); 
			//afbeelding
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			System.out.println("Categorie: " + categorie.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	public boolean deleteCategorie(Categorie categorie){
		boolean result = false;
		boolean exists = findCategorieByPK(categorie.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Categorie\" WHERE \"ID\" IN ("+categorie.getID()+")";	
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
	
	
	public boolean updateCategorie(Categorie categorie){ 
		boolean result = false;
		boolean exists = findCategorieByPK(categorie.getID()) != null;
		if(exists){ 
			String query = "UPDATE public.\"Categorie\" "
			+ " SET \"Naam\" = '" 		+ categorie.getNaam()		+"',"
			+ " \"Omschrijving\" = '" +categorie.getOmschrijving()+"'"
			+ " WHERE \"ID\" = " 	+ categorie.getID();
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
