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
				 int adresID = dbResultSet.getInt("Adres_ID");
				 int accountID = dbResultSet.getInt("Account_ID");
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
	
	
	public boolean checkIfOrdernumberExists(String ordernummer) {
		boolean exists = false;
		
		String query = "SELECT \"Ordernummer\" FROM public.\"Bestelling\" WHERE \"Ordernummer\" = ?";
		try(Connection con = super.getConnection()){
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, ordernummer);
			preparedStatement.executeQuery();
			ResultSet rs = preparedStatement.getResultSet();
			int counter = 0;
			while(rs.next()) {
				counter += 1;
			}			
			preparedStatement.close();
			
			if(counter == 0) {
				exists = false;
			}else {
				exists = true;
			}
			
			
		}catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
		return exists;
	}
	
	public void saveBestelling(Bestelling bestelling){
		int bestellingID = 0;
		String query = "INSERT INTO public.\"Bestelling\"(\r\n" + 
				"    \"ID\", \"Adres_ID\", \"Account_ID\")\r\n" + 
				"    VALUES (nextval('bestelling_seq'::regclass), ?,?);;";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
			preparedStatement.setInt(1, bestelling.getAdres().getID()); 
			preparedStatement.setInt(2, bestelling.getAccount().getID()); 
			preparedStatement.executeUpdate();	
			ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                bestellingID = rs.getInt(1);
            }
			preparedStatement.close();
			bestelling.setID(bestellingID);
			System.out.println("Bestelling: " + bestelling.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	public boolean deleteBestelling(Bestelling bestelling){
		boolean result = false;
		boolean exists = findBestellingByPK(bestelling.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Bestelling\" WHERE \"ID\" IN ("+bestelling.getID()+")";	
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
	
	
	public boolean updateBestelling(Bestelling bestelling){ 
		boolean result = false;
		boolean exists = findBestellingByPK(bestelling.getID()) != null;
		if(exists){ 
			String query = "UPDATE public.\"Bestelling\" "
			+ " SET \"Adres_ID\" = '" 		+ bestelling.getAdres().getID()		+"',"
			+ " \"Account_ID\" = '" +bestelling.getAccount().getID()+"',"
			+ " \"Ordernummer\" = '" +bestelling.getOrdernummer()+"'"
			+ " WHERE \"ID\" = " 	+ bestelling.getID();
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
