package negocio.vo;

public class PurchasedProduct {

	private String product = null;

	private Float price = 0.0f;

	private Integer units = 0;
	
	
	public PurchasedProduct() {
		super();
	}

	public PurchasedProduct(String product, Float price, Integer units) {
		super();
		this.product = product;
		this.price = price;
		this.units = units;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}
	
}