package negocio.vo;

import java.util.ArrayList;
import java.util.List;

public class UserCart {
	
	private User user = new User();
	private List<PurchasedProduct> items = new ArrayList<>();
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public Float getTotal() {
		Float total = 0f;
		
		return total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PurchasedProduct> getItems() {
		return items;
	}

	public void setItems(List<PurchasedProduct> items) {
		this.items = items;
	}
}