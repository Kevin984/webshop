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
				 String isActief = dbResultSet.getString("IsActief"); // ff kijken hoe een Y/N in db vertaald kan worden naar een boolean
				 boolean actief = false;
				 if(isActief.equalsIgnoreCase("Y")) {
					 actief = true;
				 }
				 int klantID = dbResultSet.getInt("Klant_ID");
				 int adresID = dbResultSet.getInt("Factuur_AdresID");
				 Klant klant = klantDAO.findKlantByPK(klantID);
				 Adres adres = adresDAO.findAdresByPK(adresID);
				 
				 Account account = new Account(ID, openDatum, klant,adres, actief );
				 accounts.add(account);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return accounts; 
	}
	
	public List<Account> findAll(){ return selectAccounts("SELECT * FROM public.\"Account\" ORDER BY \"ID\"");}
	
	public Account findAccountByPK(int ID){ 	
		return selectAccounts("SELECT * FROM public.\"Account\" WHERE \"ID\" = " + ID).get(0);
	}
	
	public Account findAccountByUsername(String username){ 	
		return selectAccounts("SELECT * FROM public.\"Account\" WHERE \"Username\" = '" + username+"'").get(0);
	}
	
	public void saveAccount(Account account){
		String query = "INSERT INTO public.\"Account\"(\r\n" + 
				"    \"ID\", \"OpenDatum\", \"IsActief\", \"Factuur_AdresID\", \"Klant_ID\")\r\n" + 
				"    VALUES (nextval('account_seq'::regclass), '?', '?', ?, ?);";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setDate(1, (java.sql.Date) account.getOpenDatum()); 
			
			String actief;
			if(account.getIsActief()) {
				actief = "Y";
			}else {
				actief="N";
			}
			preparedStatement.setString(2, actief);
			preparedStatement.setInt(3, account.getFactuurAdres().getID());
			preparedStatement.setInt(4, account.getKlant().getID());
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			System.out.println("account: " + account.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	public boolean deleteAccount(Account account){
		boolean result = false;
		boolean exists = findAccountByPK(account.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Account\" WHERE \"ID\" IN ("+account.getID()+")";	
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
	
	
	public boolean updateAccount(Account account){ 
		boolean result = false;
		boolean exists = findAccountByPK(account.getID()) != null;
		String actief;
		if(account.getIsActief()) {
			actief = "Y";
		}else {
			actief="N";
		}
		if(exists){ 
			String query = "UPDATE public.\"Account\" "
			+ " SET \"OpenDatum\" = '" 		+ account.getOpenDatum()	+"',"
			+ " \"IsActief\" = '" +actief +"',"
						+ " \"Factuur_AdresID\" = '" +account.getFactuurAdres().getID() +"',"
			+ " \"Klant_ID\" = '" +account.getKlant().getID() +"'"
			+ " WHERE \"ID\" = " 	+ account.getID();
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
