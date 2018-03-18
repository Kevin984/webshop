package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ic.webshop.domain.Adres;

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
	
	public void saveAdres(Adres adres){
		String query = "INSERT INTO public.\"Adres\"(\r\n" + 
				"	\"ID\", \"Straat\", \"Straatnummer\")\r\n" + 
				"	VALUES (nextval('adres_seq'::regclass), ?, ?);";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, adres.getStraat()); 
			preparedStatement.setString(2, adres.getStraatNummer()); 
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			System.out.println("Artikel: " + adres.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	public boolean deleteAdres(Adres adres){
		boolean result = false;
		boolean exists = findAdresByPK(adres.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Adres\" WHERE \"ID\" IN ("+adres.getID()+")";	
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
	
	
	public boolean updateAdres(Adres adres){ 
		boolean result = false;
		boolean exists = findAdresByPK(adres.getID()) != null;
		if(exists){ 
			String query = "UPDATE public.\"Adres\" "
			+ " SET \"Straat\" = '" 		+ adres.getStraat()		+"',"
			+ " \"Straatnummer\" = '" +adres.getStraatNummer()+"'"
			+ " WHERE \"ID\" = " 	+ adres.getID();
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
