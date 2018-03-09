package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Adres;
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
	
	public Categorie findAdresByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectCategorieen("SELECT * FROM public.\"Categorie\"  WHERE \"ID\" = " + ID).get(0);
	}
}
