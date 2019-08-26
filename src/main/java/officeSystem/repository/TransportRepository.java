package officeSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import officeSystem.model.Transport;

@Repository
@Transactional
public interface TransportRepository extends JpaRepository<Transport, Integer> {
	//指定商品の入出荷情報取得
	@Query(value = "SELECT * FROM transport WHERE product_id = :product_id ORDER BY transport_date DESC", nativeQuery = true)
	public List<Transport> getProductTransport(int product_id);
	
	//入出荷情報記録
	@Modifying
	@Query(value = "INSERT INTO transport (product_id, employee_name, r_or_s, quantity) VALUES (:product_id, :employee_name, :r_or_s, :quantity)", 
	nativeQuery = true)
	public void addTransportData(int product_id, String employee_name, String r_or_s, int quantity);
}
