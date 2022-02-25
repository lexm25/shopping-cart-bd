package persistencia.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class GenericDAOImpl {
	
	private static Connection conexion = null;
	static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		
	protected static final Connection getSharedConnection(){
		if(conexion==null)
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:6033/shop?characterEncoding=utf8",
						"developer",
						"programaciondaw");
				//Nos aseguramos de que el Autocommit es true siempre en la conexion compartida
				if(!conexion.getAutoCommit())
					conexion.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error conectando a BD");
				e.printStackTrace();
			}
		return conexion;
	}
	
	protected static final Connection getIndividualConnection(){
		Connection conexion=null;
			try {
				conexion = DriverManager.getConnection("jdbc:mysql://localhost:6033/shop?characterEncoding=utf8",
						"developer",
						"programaciondaw");
			} catch (SQLException e) {
				System.out.println("Error conectando a BD");
				e.printStackTrace();
			}
		return conexion;
	}
}
