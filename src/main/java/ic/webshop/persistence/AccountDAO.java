package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ic.webshop.domain.Account;
import ic.webshop.domain.Adres;
import ic.webshop.domain.Klant;

public class AccountDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
private AdresDAO adresDAO = new AdresDAO();
private KlantDAO klantDAO = new KlantDAO();

	private List<Account> selectAccounts(String query){
		List<Account> accounts = new ArrayList<Account>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			accounts.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 Date openDatum = dbResultSet.getDate("OpenDatum");
				 boolean isActief = dbResultSet.getBoolean("IsActief"); // ff kijken hoe een Y/N in db vertaald kan worden naar een boolean
				 int klantID = dbResultSet.getInt("KlantID");
				 int adresID = dbResultSet.getInt("AdresID");
				 Klant klant = klantDAO.findKlantByPK(klantID);
				 Adres adres = adresDAO.findAdresByPK(adresID);
				 
				 Account account = new Account(ID, openDatum, klant,adres, isActief );
				 accounts.add(account);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return accounts; 
	}
	
	public List<Account> findAll(){ return selectAccounts("SELECT * FROM public.\"Account\" ORDER BY \"ID\"");}
	
	public Account findAccountByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectAccounts("SELECT * FROM public.\"Account\" WHERE \"ID\" = " + ID).get(0);
	}
}
