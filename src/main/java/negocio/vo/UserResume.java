package negocio.vo;

public class UserResume {
	
	private String username;
	
	private Float gasto;
	
	private Integer numPedidos;

	public UserResume() {
		super();
	}

	public UserResume(String username, Float gasto, Integer numPedidos) {
		super();
		this.username = username;
		this.gasto = gasto;
		this.numPedidos = numPedidos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Float getGasto() {
		return gasto;
	}

	public void setGasto(Float gasto) {
		this.gasto = gasto;
	}

	public Integer getNumPedidos() {
		return numPedidos;
	}

	public void setNumPedidos(Integer numPedidos) {
		this.numPedidos = numPedidos;
	}
	
}