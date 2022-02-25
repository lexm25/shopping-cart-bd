package negocio.servicios.impl;

import java.util.List;

import negocio.servicios.AdminShopService;
import negocio.vo.Purchase;
import negocio.vo.UnitsSold;
import negocio.vo.UserResume;
import persistencia.dao.AdminDAO;
import persistencia.dao.impl.AdminDAOImpl;

public class AdminShopServiceImpl implements AdminShopService {
	
	private AdminDAO adminDAO = new AdminDAOImpl();
	
	@Override
	public List<UnitsSold> top3() {
		return adminDAO.top3();
	}

	@Override
	public Purchase biggestInvoice() {
		return adminDAO.biggestInvoice();
	}

	@Override
	public List<UserResume> userRanking() {
		return adminDAO.userRanking();
	}

}
