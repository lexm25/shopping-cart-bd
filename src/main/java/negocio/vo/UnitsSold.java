package negocio.vo;

public class UnitsSold {
	
	private String product;
	
	private Integer units;

	public UnitsSold() {
		super();
	}

	public UnitsSold(String product, Integer units) {
		super();
		this.product = product;
		this.units = units;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}
	
}