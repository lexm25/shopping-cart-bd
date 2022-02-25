package persistencia.dao;

import java.util.List;

import negocio.vo.UnitsSold;
import negocio.vo.Purchase;
import negocio.vo.UserResume;

public interface AdminDAO {
	
	List<UnitsSold> top3();
	
	Purchase biggestInvoice();
	
	List<UserResume> userRanking();
}
