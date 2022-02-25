package persistencia.dao;

import java.util.List;

import negocio.vo.Product;
import negocio.vo.User;
import negocio.vo.UserCart;

public interface UserDAO {

	User login(String username, String password);
	
	List<Product> productosDisponibles();
	
	boolean productExists(Product product);
	
	boolean confirmCheckOut(UserCart carro);
}
