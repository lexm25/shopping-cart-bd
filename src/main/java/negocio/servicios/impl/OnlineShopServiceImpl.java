package negocio.servicios.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negocio.servicios.OnlineShopService;
import negocio.vo.Product;
import negocio.vo.PurchasedProduct;
import negocio.vo.User;
import negocio.vo.UserCart;
import persistencia.dao.UserDAO;
import persistencia.dao.impl.UserDAOImpl;

public class OnlineShopServiceImpl implements OnlineShopService{
	
	Map<String, List<PurchasedProduct>> carros = new HashMap<>();
	private UserDAO userBD = new UserDAOImpl();

	@Override
	public User login(String username, String password) {
		return userBD.login(username, password);
	}
	
	@Override
	public boolean addProductCart(User user, int units, Product product) {
		boolean retorno = false;
			
		if(userBD.productExists(product)) {
			//creo una list vacía para que apunte al carrito
			List<PurchasedProduct> list;
			if(!carros.containsKey(user.getUsername())) {
				list = new ArrayList<>();
				carros.put(user.getUsername(), list);
			}
			else {
				list = carros.get(user.getUsername());
			}
			PurchasedProduct cartItem = new PurchasedProduct(product.getName(),product.getPrice(),units);
			
			list.add(cartItem);
			retorno = true;
		}
		return retorno;
	}

	@Override
	public UserCart viewCart(User user) {
		UserCart carro = new UserCart();
		
		carro.setUser(user);
		String username = user.getUsername();
		
		if(carros.containsKey(username)) {
			carro.setItems(carros.get(username));
		}
		return carro;
	}

	@Override
	public boolean cleanCart(User user) {	
		carros.put(user.getUsername(), new ArrayList<>());
		return true;
	}
	
	public List<Product> availableProducts(){
		return userBD.productosDisponibles();
	}

	@Override
	public boolean confirmCheckout(UserCart cart) {
		return userBD.confirmCheckOut(cart);
	}
	
}