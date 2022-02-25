package negocio.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Purchase {
		
	private LocalDateTime date;
	
	private String customer;
	
	private Float total;
	
	private List<PurchasedProduct> lineas = new ArrayList<>();
	
	public Purchase() {
		super();
	}

	public Purchase(LocalDateTime date, String customer, Float total, List<PurchasedProduct> lineas) {
		super();
		this.date = date;
		this.customer = customer;
		this.total = total;
		this.lineas = lineas;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public List<PurchasedProduct> getLineas() {
		return lineas;
	}

	public void setLineas(List<PurchasedProduct> lineas) {
		this.lineas = lineas;
	}
	
}