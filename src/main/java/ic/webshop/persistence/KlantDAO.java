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
				 String naam = dbResultSet.getString("Naam");
				 int adresID = dbResultSet.getInt("Adres_ID");
				 String afbeelding = dbResultSet.getString("Afbeelding");
				 Adres adres = adresDAO.findAdresByPK(adresID);
				 Klant newklant = new Klant(ID, naam, afbeelding, adres);
				 klanten.add(newklant);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return klanten; 
	}
	
	public List<Klant> findAll(){return selectKlanten("SELECT * FROM public.\"Klant\" ORDER BY \"ID\"");}
	
	public Klant findKlantByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectKlanten("SELECT * FROM public.\"Klant\" WHERE \"ID\" = " + ID).get(0);
	}
	
	public void saveKlant(Klant klant){
		String query = "INSERT INTO public.\"Klant\"(\r\n" + 
				"	\"ID\", \"Naam\", \"Afbeelding\", \"Adres_ID\")\r\n" + 
				"	VALUES (nextval('klant_seq'::regclass), '?', null, ?);";
		try (Connection con = super.getConnection()) {
			preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, klant.getNaam()); 
			preparedStatement.setInt(2,klant.getAdres().getID()); 
			preparedStatement.executeUpdate();	
			preparedStatement.close();
			System.out.println("Artikel: " + klant.getID()  + " saved.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}	
}
	
	public boolean deleteKlant(Klant klant){
		boolean result = false;
		boolean exists = findKlantByPK(klant.getID()) != null;
		
		if(exists){	
			String query = "DELETE FROM public.\"Klant\" WHERE \"ID\" IN ("+klant.getID()+")";	
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
	
	
	public boolean updateKlant(Klant klant){ 
		boolean result = false;
		boolean exists = findKlantByPK(klant.getID()) != null;
		if(exists){ 
			String query = "UPDATE public.\"Klant\" "
			+ " SET \"Naam\" = '" 		+klant.getNaam() 		+"',"
			+ " \"Afbeelding\" = '" +klant.getAfbeelding()+"',"
					+ " \"Adres_ID\" = " +klant.getAdres().getID()+""
			+ " WHERE \"ID\" = " 	+ klant.getID();
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
