package presentacion;

import java.util.ArrayList;
import java.util.List;

import negocio.servicios.AdminShopService;
import negocio.servicios.impl.AdminShopServiceImpl;
import negocio.vo.Purchase;
import negocio.vo.PurchasedProduct;
import negocio.vo.UnitsSold;
import negocio.vo.UserResume;

public class AdminSection {
	
	private static AdminShopService service = new AdminShopServiceImpl();
	
	static final String SEP_PURCHASED_PRODUCT = "\t-------------------------------------------------";
	static final String SEP1 = "---------------------------------------------------------";
	static final String CONTINUAR = "Pulsa Cualquier Tecla para continuar.";
	
	static final String TOP3_FORMAT="%-25s %-8s %n";

	static final String BIGGEST_HEADER_FORMAT="%-20s %-20s %-15S %n";
	static final String PURCHASED_PRODUCT_HEADER="\t%-20s %8s %6s %10s %n";
	static final String PURCHASED_PRODUCT_FORMAT="\t%-20s %8.2f %6d %10.2f %n";
	static final String PURCHASED_PRODUCT_FOOTER="\t%47.2f %n";
	

	static final String RANKING_HEADER="%-15s %-10s %-8S %n";
	static final String RANKING_FIELDS="%-13s %8.2f %8d %n";
	
	private AdminSection() {
		
	}
	
	static void run() {
		int opcion = 0;
		
		do {
			opcion = menu();
			switch (opcion) {
			case 1:
				top3();
				break;
			case 2:
				biggestPurchase();
				break;
			case 3:
				userRanking();
				break;
			default:
				System.out.println("Opción Incorrecta");
			}
		}while(opcion!=0);
		
	}
	
	private static int menu() {
		System.out.println("Elija una de las siguientes opciones");
		System.out.println("1. TOP 3 Productos Vendidos");
		System.out.println("2. Compra de Mayor Importe");
		System.out.println("3. Ranking de Clientes");
		System.out.println("0. Salir de Compra");
		
		return Main.leerEntero();
	}
	
	private static void top3() {
		System.out.printf(TOP3_FORMAT, "PRODUCTO", "UNIDADES");
		System.out.printf(SEP1 + "\n");
		List<UnitsSold> lista = new ArrayList<UnitsSold>();
		lista = service.top3();
		for (UnitsSold u : lista) {
			System.out.printf(TOP3_FORMAT, u.getProduct(), u.getUnits());
		}
	}
	
	private static void biggestPurchase() {
		Purchase p = service.biggestInvoice();
		List<PurchasedProduct> lista = new ArrayList<>();
		lista = p.getLineas();
		
		System.out.printf(BIGGEST_HEADER_FORMAT,"USERNAME","DATE","TOTAL PRICE");
		System.out.print(SEP1 + "\n");
		System.out.printf(BIGGEST_HEADER_FORMAT,p.getCustomer(),p.getDate(),p.getTotal());
		System.out.printf(PURCHASED_PRODUCT_HEADER, "PRODUCT NAME","PRICE","UNITS","TOTAL PRICE");
		System.out.printf(SEP_PURCHASED_PRODUCT + "\n");
		for (PurchasedProduct pp : lista) {
			System.out.printf(PURCHASED_PRODUCT_FORMAT, pp.getProduct(),pp.getPrice(),pp.getUnits(),p.getTotal());
		}
		System.out.printf(SEP_PURCHASED_PRODUCT + "\n");
		System.out.printf(PURCHASED_PRODUCT_FOOTER, p.getTotal());
		
	}
	
	private static void userRanking() {
		System.out.printf(RANKING_HEADER,"USERNAME", "GASTADO", "NUMERO DE PEDIDOS");
		System.out.printf(SEP1 + "\n");
		List<UserResume> lista = new ArrayList<UserResume>();
		lista = service.userRanking();
		for (UserResume u : lista) {
			System.out.printf(RANKING_FIELDS,u.getUsername(),u.getGasto(),u.getNumPedidos());
		}
		System.out.printf(SEP1 + "\n");
	}
	
}
