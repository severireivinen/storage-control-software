package DataAccessObjectTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;



/**
 * The Class ProductAccessObjectTest.
 * Tests for various different product testing.
 * @author Olli Kolkki
 */
public class ProductAccessObjectTest {
	
	/** The product DAO. */
	private ProductAccessObject productDAO = new ProductAccessObject();
	

	/** The product. */
	private Product product;
	
	/** The product state. */
	private boolean productState = false;
	
	/** The product id. */
	private String productId;
	
	/**
	 * Before each.
	 */
	@BeforeEach
	public void beforeEach() {
		product = new Product("123", "Boots", 99.99, "Big red boots", 66, "BBBBB");
	}
	
	
	/**
	 * Test for adding a product
	 */
	@Test
	@Order(1)
	@DisplayName("Test for adding a product")
	public void testAdd() {
		Product newProduct = productDAO.addProduct(product);
		
		boolean productState = false;
		if(newProduct != null)
			productState = true;
		assertTrue(productState, "Creating a product doesn't work");	
		assertTrue((product = (Product) productDAO.getProduct(newProduct.getId())) != null, "Fetching a product doesn't work");
		
		
		assertEquals("Boots", product.getName(), "Wrong name");
		assertEquals(99.99, product.getPrice(), "Wrong price");
		assertEquals("Big red boots", product.getDescription(), "Wrong description");
		assertEquals(66, product.getStock(), "Wrong stock");
		assertEquals("BBBBB", product.getLocation(), "Wrong location");	
		productId = newProduct.getId();
		productDAO.removeProduct(productId);
	}
	
	
	
	/**
	 * Identical products should not exist
	 */
	@Test
	@Order(2)
	@DisplayName("Identical products should not exist")
	
	public void testAddIdentical() {
		
		Product newProduct = productDAO.addProduct(product);
		productState = false;
		if(newProduct != null)
			productState = true;
		assertTrue(productState, "addProduct() - adding a product does not work");
		String productId = newProduct.getId();
		
		Product newProduct2 = productDAO.addProduct(product);
		productState = false;
		if(newProduct2 != null)
			productState = true;
		assertFalse(productState, "addProduct(): The same product can be added twice");
		
		productDAO.removeProduct(productId);	
	}
	
	
	/**
	 * Removing a non-existent product should not work
	 */
	@Test
	@Order(3)
	@DisplayName("Removing a non-existent product should not work")
	public void testRemoveNull() {
		
		Product fakeproduct3 = new Product("123123", "Shirt", 99.99, "Big red boots", 66, "123asd");
		System.out.println("feikkitesti" + fakeproduct3.getId());
		assertFalse(productDAO.removeProduct(fakeproduct3.getId()), "removeProduct() - Non-existent product was removed");
	}
	
	/**
	 * Editing the details of a product should work
	 */
	@Test
	@Order(4)
	@DisplayName("Editing the details of a product should work")
	public void testEdit() {
		
		Product newProduct = productDAO.addProduct(product);
		boolean productState = false;
		if(newProduct != null)
			productState = true;
		assertTrue(productState, "Creating a product doesn't nyt");	
		assertTrue((product = (Product) productDAO.getProduct(newProduct.getId())) != null, "Fetching a product doesn't work");
		
		
		assertEquals("Boots", product.getName(), "Wrong name");
		assertEquals(99.99, product.getPrice(), "Wrong price");
		assertEquals("Big red boots", product.getDescription(), "Wrong description");
		assertEquals(66, product.getStock(), "Wrong stock");
		assertEquals("BBBBB", product.getLocation(), "Wrong location");	
		
		product.setName("New boots");
		product.setDescription("Orange boots");
		assertEquals((productDAO.editProduct(product)).getId(), product.getId(), "editProduct() - editing a product failed");
		
		
		productId = newProduct.getId();
		productDAO.removeProduct(productId);
		
	
	}
	
	

}
