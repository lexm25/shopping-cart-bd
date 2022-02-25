package presentacion;

import java.util.Scanner;

import negocio.servicios.OnlineShopService;
import negocio.servicios.impl.OnlineShopServiceImpl;
import negocio.vo.User;

public class Main {

	private static OnlineShopService service = new OnlineShopServiceImpl();

	public static void main(String[] args) {

		int opcion = -1;
		User logado = null;

		do {
			menu(logado!=null);
			opcion = leerEntero();
			
			if(logado!=null) {
				switch (opcion) {
					case 1:
						CartSection.run(logado);
						break;
					case 2:
						AdminSection.run();
						break;
					case 3:
						logado=null;
						break;
					case 0:
						break;
					default:
						System.out.println("Opción incorrecta");
					}
			}else {
				switch (opcion) {
				case 1:
					logado=login();
					break;
				case 0:
					break;
				default:
					System.out.println("Opción incorrecta");
				}
			}
		} while (opcion != 0);
		System.out.println("Hasta la próxima. Gracias por su confianza");

	}
	
	private static User login() {
		String username = "";
		String password = "";
		User logged = null;
		for(int i = 3;i>0 && logged == null;i--) {
			if(i<3) {
				System.out.println("Usuario o contraseña INCORRECTOS");
				System.out.println("Le quedan " + i + " intentos");
			}
			username = leerLinea("Introduzca su Usuario");
			password = leerLinea("Introduzca su contraseña");
			logged = service.login(username, password);
		}
		if(logged != null)
			System.out.println("Bienvenid@ " + username);
		return logged;
	}

	private static void menu(boolean logado) {
		System.out.println("Elija una de las siguientes opciones:");
		if(!logado) {
			System.out.println("1. Login");
			System.out.println("0. Salir");
		}else {
			System.out.println("1. Entrar a la tienda");
			System.out.println("2. Administrar la tienda");
			System.out.println("3. Logout");
			System.out.println("0. Salir");
		}
	}

	@SuppressWarnings("resource")
	static int leerEntero() {
		Scanner teclado = new Scanner(System.in);
		return teclado.nextInt();
	}
	
	static int leerEntero(String prompt) {
		System.out.println(prompt);
		return leerEntero();
	}


	@SuppressWarnings("resource")
	static String leerLinea() {
		Scanner teclado = new Scanner(System.in);
		return teclado.nextLine();
	}

	static String leerLinea(String prompt) {
		System.out.println(prompt);
		return leerLinea();
	}

}