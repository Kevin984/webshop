package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ic.webshop.domain.Adres;
import ic.webshop.domain.Product;

public class AdresDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;

	private List<Adres> selectAdressen(String query){
		List<Adres> adressen = new ArrayList<Adres>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			adressen.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 String straat = dbResultSet.getString("Straat");
				 String straatnummer = dbResultSet.getString("Straatnummer");
				 Adres adres = new Adres(ID, straat, straatnummer);
				 adressen.add(adres);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return adressen; 
	}
	
	public List<Adres> findAll(){ return selectAdressen("SELECT * FROM public.\"Adres\" ORDER BY \"ID\"");}
	
	public Adres findAdresByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectAdressen("SELECT * FROM public.\"Adres\"  WHERE \"ID\" = " + ID).get(0);
	}
}
