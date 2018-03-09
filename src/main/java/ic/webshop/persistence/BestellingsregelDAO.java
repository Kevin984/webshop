package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Adres;
import ic.webshop.domain.Bestelling;
import ic.webshop.domain.Bestellingsregel;
import ic.webshop.domain.Product;

public class BestellingsregelDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
private ProductDAO productDAO = new ProductDAO();
private BestellingDAO bestellingDAO = new BestellingDAO();

	private List<Bestellingsregel> selectBestellingsregels(String query){
		List<Bestellingsregel> bestellingsregels = new ArrayList<Bestellingsregel>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			bestellingsregels.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 int aantal = dbResultSet.getInt("Aantal");
				 double totaalprijs = dbResultSet.getDouble("Totaalprijs");
				 int productID = dbResultSet.getInt("ProductID");
				 int bestellingID = dbResultSet.getInt("BestellingID");
				 
				 Product product = productDAO.findByPK(productID);
				 Bestelling bestelling = bestellingDAO.findBestellingByPK(bestellingID);	
				 Bestellingsregel bestellingsregel = new Bestellingsregel(ID,bestelling,aantal,totaalprijs,product );
				 bestellingsregels.add(bestellingsregel);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return bestellingsregels; 
	}
	
	public List<Bestellingsregel> findAll(){ return selectBestellingsregels("SELECT * FROM public.\"Bestellingsregel\" ORDER BY \"ID\"");}
	
	public Bestellingsregel findBestellingsregelByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectBestellingsregels("SELECT * FROM public.\"Bestellingsregel\"  WHERE \"ID\" = " + ID).get(0);
	}
}
