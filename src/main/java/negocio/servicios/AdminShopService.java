package negocio.servicios;

import java.util.List;

import negocio.vo.Purchase;
import negocio.vo.UnitsSold;
import negocio.vo.UserResume;

public interface AdminShopService {

	List<UnitsSold> top3();
	
	Purchase biggestInvoice();
	
	List<UserResume> userRanking();
	
}
