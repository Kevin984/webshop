package ic.webshop.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ic.webshop.domain.Aanbieding;
import ic.webshop.domain.Adres;
import ic.webshop.domain.Product;

public class AanbiedingDAO extends BaseDAO{
	private PreparedStatement preparedStatement = null;
private ProductDAO productDAO = new ProductDAO();

	private List<Aanbieding> selectAanbiedingen(String query){
		List<Aanbieding> aanbiedingen = new ArrayList<Aanbieding>();
				
		try(Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			aanbiedingen.clear();
			while(dbResultSet.next()){ 
				 int ID = dbResultSet.getInt("ID");
				 Double aanbiedingprijs = dbResultSet.getDouble("Aanbiedingprijs");
				 Date vanDatum = dbResultSet.getDate("VanDatum");
				 Date totDatum = dbResultSet.getDate("TotDatum");
				 String reclametekst = dbResultSet.getString("Reclametekst");
				 int productID = dbResultSet.getShort("ProductFKIDAtsdgsdggkkkkkkkkkkkkkkkkkkkkkk");				 
				 
				 Product product = productDAO.findByPK(productID);
				 Aanbieding aanbieding = new Aanbieding(ID, product, aanbiedingprijs, vanDatum, totDatum, reclametekst);

				 aanbiedingen.add(aanbieding);
			}
		}
		catch(SQLException sqle){ sqle.printStackTrace();
	}
		return aanbiedingen; 
	}
	
	public List<Aanbieding> findAll(){ return selectAanbiedingen("SELECT * FROM public.\"Aanbieding\" ORDER BY \"ID\"");}
	
	public Aanbieding findAdresByPK(int ID){ 	//nog een nullpointerexception handler toevoegen? nette 404 error geven
		return selectAanbiedingen("SELECT * FROM public.\"Aanbieding\" WHERE \"ID\" = " + ID).get(0);
	}
}
