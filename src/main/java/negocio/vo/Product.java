package negocio.vo;

public class Product {
	
	private String name;
	private Float price;
	
	public Product() {
		super();
	}

	public Product(String name, Float price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
		
}