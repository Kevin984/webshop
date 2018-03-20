package ic.webshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ic.webshop.domain.Product;
import ic.webshop.persistence.ProductDAO;


 class ProductDAOTest {
	private ProductDAO pDAO;
	
	@BeforeEach
	void init() {
	pDAO = new ProductDAO();
	}
	
	@Test
	void testProductCRUD(){
		String expectedUpdatedName = "Jenkins Updated Product";
		
		Product product = new Product("Jenkins Product","A test product insert by Jenkins",99.99);
		pDAO.saveArtikel(product);
		
		Product product2 = pDAO.findByPK(product.getID());
		assertEquals(product.getID(), product2.getID());
		
		product2.setNaam("Jenkins Updated Product");
		pDAO.updateArtikel(product2);
		Product product3 = pDAO.findByPK(product2.getID());
		
		String newName = product3.getNaam();
		assertEquals(expectedUpdatedName, newName);
		
		pDAO.deleteProduct(product3);
		Product product4 = pDAO.findByPK(product3.getID());
		assertEquals(null, product4);
	}
	
	/*
	@Test
	void testBasicSalaryWithInValidSalary() {
	assertThrows(IllegalArgumentException.class, () -> salaryCalculator.setSalary(-1));
	}
	@Test
	void testSalaryCalculatorWithValidSalary() {
	double salary = 1200;
	salaryCalculator.setSalary(1200);
	double expectedInsuranceMoney = salary * 0.15;
	assertEquals(expectedInsuranceMoney, salaryCalculator.getInsuranceMoney());
	double expectedBonusMoney = salary / 11;
	assertEquals(expectedBonusMoney, salaryCalculator.getSalaryBonus());
	double expectedTotalMoney = salary + expectedInsuranceMoney + expectedBonusMoney;
	assertEquals(expectedTotalMoney, salaryCalculator.getTotalSalary());
	}
	
	*/
	@AfterEach
	void tearDown() { //commit test
	pDAO = null;
	}
	}

