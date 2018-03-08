package ic.webshop.webservices;

public class ProductServiceProvider {
	private static ProductService voorraadService = new ProductService();

	public static ProductService getProductService(){
		return voorraadService;
	}
}
