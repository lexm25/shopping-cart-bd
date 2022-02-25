package negocio.servicios;

import java.util.List;

import negocio.vo.Product;
import negocio.vo.User;
import negocio.vo.UserCart;

public interface OnlineShopService {
	
	User login(String username, String password);
	
	boolean addProductCart(User user, int units, Product product);
	
	UserCart viewCart(User user);
	
	boolean cleanCart(User user);
	
	List<Product> availableProducts();
	
	boolean confirmCheckout(UserCart cart);
}
