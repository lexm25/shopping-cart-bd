package presentacion;

import java.util.List;

import negocio.servicios.OnlineShopService;
import negocio.servicios.impl.OnlineShopServiceImpl;
import negocio.vo.Product;
import negocio.vo.PurchasedProduct;
import negocio.vo.User;
import negocio.vo.UserCart;


public class CartSection {
	
	private static OnlineShopService onlineShop = new OnlineShopServiceImpl();
	
	private static final String CART_EMPTY = "El carro está vacío";
	private static final String CANCELLED = "Operación cancelada";
	private static final String PRODUCTS_FORMAT="%4s %-25s %-8s %n";
	private static final String CART_FORMAT="%-25s %-15s %-8s %n";
	private static final String PRODUCTS_SEP="------------------------------------------";
	private static final String CART_SEP="----------------------------------------------------";
	
	private CartSection() {}
	
	static void run(User user) {
		int opcion = 0;
		
		do {
			opcion = menu();
			switch (opcion) {
			case 1:
				mostrarProductosDisponibles();
				break;
			case 2:
				addProduct(user);
				break;
			case 3:
				viewCart(user);
				break;
			case 4:
				confirmCheckout(user);
				break;
			case 5:
				resetCart(user);
				break;
			default:
				System.out.println("Opción Incorrecta");
				break;
			}
		}while(opcion!=0);
	}
	
	private static int menu() {
		System.out.println("Elija una de las siguientes opciones");
		System.out.println("1. Listar Productos disponibles");
		System.out.println("2. Añadir producto al carrito");
		System.out.println("3. Ver Cesta");
		System.out.println("4. Confirmar Compra");
		System.out.println("5. Vaciar Carro");
		System.out.println("0. Salir de Compra");
		
		return Main.leerEntero();
	}
	
	private static void mostrarProductosDisponibles() {
		List<Product> productos = onlineShop.availableProducts();
		
		if (productos.isEmpty()) {
			System.out.println("No hay productos disponibles");
		}
		else {
			System.out.println("Productos disponibles:\n");
			System.out.printf(PRODUCTS_FORMAT, "id","Nombre","Precio");
			System.out.printf(PRODUCTS_SEP + "\n");
			int i = 0;
			
			for (Product product : productos) {
				i++;
				System.out.printf(PRODUCTS_FORMAT,i,product.getName(),product.getPrice());
			}
			System.out.println(PRODUCTS_SEP);
		}
	}
	
	private static void addProduct(User user) {
		List<Product> productos = onlineShop.availableProducts();
		
		if (productos.isEmpty()) {
			System.out.println("No hay productos disponibles");
		}
		else {
			mostrarProductosDisponibles();
			
			int id = Main.leerEntero("Introduce el id del producto");
			
			int units = Main.leerEntero("Introduce las unidades del producto");
			
			//id-1 ya que en listas 0 = primera posicion
			Product p = productos.get(id-1);
			System.out.printf("Se añadirán al carro \"%s\" unidades del producto \"%s\"" + "\n",units,p.getName());
			String opcion = Main.leerLinea("Confirmar acción s/N");
			
			if("s".equalsIgnoreCase(opcion)) {
				boolean retorno = onlineShop.addProductCart(user, units, p);
				if(retorno) {
					System.out.println("El producto se ha añadido correctamente al carro");
				}
				else {
					System.out.println("Se ha producido un error");
				}
			}
			else {
				System.out.println(CANCELLED);
			}
		}
	}
	
	private static UserCart viewCart(User user) {
		UserCart carro = new UserCart();
		carro = onlineShop.viewCart(user);
		if(onlineShop.viewCart(user).isEmpty()) {
			System.out.println(CART_EMPTY);
		}
		else {
			List<PurchasedProduct> lista = onlineShop.viewCart(user).getItems();
			printCartContent(lista);
		}
		return carro;
	}
	
	private static Float printCartContent(List<PurchasedProduct> productos) {
		Float total = 0f;
		for (PurchasedProduct p : productos) {
			System.out.printf(CART_FORMAT,"Nombre","Unidades","Precio");
			System.out.printf(CART_SEP + "\n");
			System.out.printf(CART_FORMAT,p.getProduct(),p.getUnits(),p.getPrice());
			total += p.getPrice()*p.getUnits();
		}
		System.out.println(CART_SEP);
		System.out.println(total);
		return total;
	}
	
	private static void resetCart(User user) {		
		onlineShop.cleanCart(user);
	}
	
	private static void confirmCheckout(User user) {
		UserCart carro = viewCart(user);
		onlineShop.confirmCheckout(carro);
		onlineShop.cleanCart(user);
	}

}