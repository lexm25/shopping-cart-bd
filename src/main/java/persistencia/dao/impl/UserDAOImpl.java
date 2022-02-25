package persistencia.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import negocio.vo.Product;
import negocio.vo.PurchasedProduct;
import negocio.vo.User;
import negocio.vo.UserCart;
import persistencia.dao.UserDAO;

public class UserDAOImpl extends GenericDAOImpl implements UserDAO {

	private static final String LOGIN_QUERY = "SELECT * FROM Users WHERE username=? AND passwd=?;";
	private static final String PRODUCTS_QUERY = "SELECT * FROM Products;";
	private static final String PRODUCTS_EXISTS_QUERY = "SELECT * FROM Products WHERE productname=?;";
	private static final String INSERT_PURCHASE = "INSERT INTO Purchases VALUES (?,?,?,?,?)";
													//VALUES(username,productname,date,units,price)
	@Override
	public User login(String username, String password) {
		User user = null;
		Connection connection = getSharedConnection();
		
		//try-with-resources
		try(PreparedStatement sequence = connection.prepareStatement(LOGIN_QUERY);) {
			sequence.setString(1, username);
			sequence.setString(2, password);
			
			ResultSet rs = sequence.executeQuery();
			
			if(rs.next()) {
				user = new User (rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en la base de datos, por favor, reinicie la base de datos");
		}
		return user;
	}

	@Override
	public List<Product> productosDisponibles() {
		List<Product> productos = new ArrayList<>();
		Connection connection = getSharedConnection();
		
		try(PreparedStatement sequence = connection.prepareStatement(PRODUCTS_QUERY);){
			
			ResultSet rs = sequence.executeQuery();
			
			while(rs.next()) {
				Product p = new Product(rs.getString(1), rs.getFloat(2));
				productos.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}

	@Override
	public boolean productExists(Product product) {
		boolean exists = false;
		Connection connection = getSharedConnection();
		
		try(PreparedStatement sequence = connection.prepareStatement(PRODUCTS_EXISTS_QUERY);){
			
			sequence.setString(1, product.getName());
			
			ResultSet rs = sequence.executeQuery();
			
			exists = rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	//VALUES(username,productname,date,units,price)
	@Override
	public boolean confirmCheckOut(UserCart carro) {
		boolean retorno = false;
		try (Connection connection = getIndividualConnection();) {

			try (PreparedStatement sequence = connection.prepareStatement(INSERT_PURCHASE);) {

				//transaccion
				connection.setAutoCommit(false);
				//ya que usuario y la fecha nunca cambian
				//formateo de fecha date_format genericDAO
				sequence.setString(1, carro.getUser().getUsername());
				String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
				sequence.setString(3, fecha);
				
				List<PurchasedProduct> items = carro.getItems();
				for (PurchasedProduct p : items) {
					sequence.setString(2, p.getProduct());
					sequence.setInt(4, p.getUnits());
					sequence.setFloat(5, p.getPrice());
				}
				if (sequence.executeUpdate() !=1) {
					throw new SQLException("Error en la transaccion de la compra");
				} else {
					retorno = true;
				}
				connection.commit();
				retorno = true;
			} catch (SQLException e1) {
				connection.rollback();
				e1.printStackTrace();
			}
			return retorno;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
}