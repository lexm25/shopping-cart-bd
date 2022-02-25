package persistencia.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import negocio.vo.Purchase;
import negocio.vo.PurchasedProduct;
import negocio.vo.UnitsSold;
import negocio.vo.UserResume;
import persistencia.dao.AdminDAO;

public class AdminDAOImpl extends GenericDAOImpl implements AdminDAO {
	
	private static final String TOP3_PRODUCTS_QUERY = "SELECT productname, SUM(units) FROM Purchases GROUP BY productname ORDER BY SUM(units) DESC LIMIT 3;";
	private static final String BIGGEST_PURCHASE_QUERY = "SELECT * FROM ImporteCompras LIMIT 1;";
//	SELECT username,date,SUM(price*units) AS Importe FROM Purchases GROUP BY username,date ORDER BY Importe DESC LIMIT 1;
//	CREATE VIEW ImporteCompras AS SELECT username,date,SUM(price*units) AS Importe FROM Purchases GROUP BY username,date ORDER BY Importe DESC;
	private static final String PURCHASE_PRODUCTS_QUERY = "SELECT * FROM Purchases WHERE username=? AND date=?;";
	private static final String USER_RANKING_QUERY = "SELECT username, COUNT(username) AS pedidos, SUM(Importe) as facturacion from ImporteCompras GROUP BY username ORDER BY 3 DESC;";
//	SELECT username, SUM(price * units) AS Importe FROM Purchases GROUP BY username ORDER BY Importe DESC;
	
	@Override
	public List<UnitsSold> top3() {
		List<UnitsSold> unidades = new ArrayList<UnitsSold>();
		UnitsSold u = null;
		Connection connection = getIndividualConnection();
		
		try(PreparedStatement sequence = connection.prepareStatement(TOP3_PRODUCTS_QUERY);){
			
			ResultSet rs = sequence.executeQuery();
			
			while(rs.next()) {
				u = new UnitsSold(rs.getString(1),rs.getInt(2));
				unidades.add(u);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return unidades;
	}

	@Override
	public Purchase biggestInvoice() {
		Purchase purchase = null;
		Connection connection = getIndividualConnection();
		
		try(PreparedStatement sequence = connection.prepareStatement(BIGGEST_PURCHASE_QUERY);) {
		
			ResultSet rs = sequence.executeQuery();
			
			if(rs.next()) {
				String fecha = rs.getString(2);
				LocalDateTime ldt = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern(DATE_FORMAT));
				purchase = new Purchase(ldt,rs.getString(1),rs.getFloat(3),getItems(rs.getString(1),fecha));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en la base de datos, por favor, reinicie la base de datos");
		}
		
		return purchase;
	}

	private List<PurchasedProduct> getItems(String customer, String fechaBD) {
		List<PurchasedProduct> productos =  new ArrayList<PurchasedProduct>();
		PurchasedProduct p = null;
		Connection connection = getIndividualConnection();
		
		try(PreparedStatement sequence = connection.prepareStatement(PURCHASE_PRODUCTS_QUERY);) {
			
			sequence.setString(1, customer);
			sequence.setString(2, fechaBD);
			
			ResultSet rs = sequence.executeQuery();
			//String product, Float price, Integer units
			while(rs.next()) {
				p = new PurchasedProduct(rs.getString(2),rs.getFloat(5),rs.getInt(4));
				productos.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en la base de datos, por favor, reinicie la base de datos");
		}
		
		return productos;
	}

	@Override
	public List<UserResume> userRanking() {
		List<UserResume> resume = new ArrayList<>();
		UserResume ur = null;
		Connection connection = getIndividualConnection();
		try(PreparedStatement sequence = connection.prepareStatement(USER_RANKING_QUERY);) {
			
			ResultSet rs = sequence.executeQuery();

			while(rs.next()) {
				ur = new UserResume(rs.getString(1), rs.getFloat(3),rs.getInt(2));
				resume.add(ur);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en la base de datos, por favor, reinicie la base de datos");
		}
		
		return resume;
	}

}
