package ic.webshop.webservices;

import java.util.ArrayList;
import java.util.List;

import ic.webshop.domain.Product;
import ic.webshop.persistence.ProductDAO;

public class ProductService {
	private ProductDAO productDAO = new ProductDAO(); 

	public List<Product> getVoorraad(){
		
		List<Product> aa = new ArrayList<Product>();
		aa.add(new Product(0, "yes", "aa", "yee", 10.00));
		
		return productDAO.findAll();
	}

	public Product getArtikelByPK(int ID){
		Product result = null;
		
		for (Product a : productDAO.findAll()){
		//	if(a.getArtikelID() == (ID)  && a.getMaat().equals(maat) && a.getKleur().equals(kleur) ){
		//		result = a;
			//	break;
		//	}
		}
		return result;
	}

	public void saveArtikel(Product artikel){
		if (artikel != null){
			productDAO.saveArtikel(artikel);
		}
		else throw new IllegalArgumentException("kan niet opslaan");
	} 


	public void deleteArtikel(int ID){
		Product a  = productDAO.findByPK(ID);
		
		if(a != null){
			productDAO.deleteArtikel(a);
		} else throw new IllegalArgumentException("ID, maat en/of kleur bestaat niet.");
	}

	public void updateArtikel(Product artikel){
		if (artikel != null){
			productDAO.updateArtikel(artikel);
		}
		
		else throw new IllegalArgumentException("kan niet updaten");
		
	}

}
