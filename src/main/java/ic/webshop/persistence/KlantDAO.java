package ic.webshop.persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ic.webshop.domain.Adres;
import ic.webshop.domain.Klant;
import ic.webshop.domain.Product;

public class KlantDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private AdresDAO adresDAO = new AdresDAO();
	private List<Klant> selectKlanten(String query){
		List<Klant> klanten = new ArrayList<Klant>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			klanten.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 int adresID = dbResultSet.getInt("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaEEN ID van adres FK");
				 Adres adres = adresDAO.findAdresByPK(adresID);
				 Klant newklant = new Klant(ID, adres);
				 klanten.add(newklant);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return klanten; 
	}
	
	public List<Klant> findAll(){return selectKlanten("SELECT \"ID\", \"Naam\", \"Omschrijving\", \"Afbeelding\", \"Prijs\" FROM public.\"Klant\" ORDER BY \"ID\"");}
	
	public Klant findKlantByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectKlanten("SELECT * FROM public.\"Klant\" WHERE \"ID\" = " + ID).get(0);
	}
}
