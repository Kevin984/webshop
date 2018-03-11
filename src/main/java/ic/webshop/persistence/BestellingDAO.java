package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Account;
import ic.webshop.domain.Adres;
import ic.webshop.domain.Bestelling;

public class BestellingDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
	private AccountDAO accountDAO = new AccountDAO();
	private AdresDAO adresDAO = new AdresDAO();
	
	private List<Bestelling> selectBestellingen(String query){
		List<Bestelling> bestellingen = new ArrayList<Bestelling>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			bestellingen.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 int adresID = dbResultSet.getInt("AdresID");
				 int accountID = dbResultSet.getInt("AccountID");
				 Adres adres = adresDAO.findAdresByPK(adresID);
				 Account account = accountDAO.findAccountByPK(accountID);
				 
				 Bestelling bestelling = new Bestelling(ID, adres, account);
				 bestellingen.add(bestelling);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return bestellingen; 
	}
	
	public List<Bestelling> findAll(){ return selectBestellingen("SELECT * FROM public.\"Bestelling\" ORDER BY \"ID\"");}
	
	public Bestelling findBestellingByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectBestellingen("SELECT * FROM public.\"Bestelling\" WHERE \"ID\" = " + ID).get(0);
	}
}