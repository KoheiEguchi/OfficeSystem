package officeSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import officeSystem.model.Product;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
	//全商品を取得
	@Query(value = "SELECT * FROM product", nativeQuery = true)
	public List<Product> allProduct();
	
	//商品追加
	@Modifying
	@Query(value = "INSERT INTO product (name, quantity, place) VALUES (:name, :quantity, :place)", nativeQuery = true)
	public void addProduct(String name, int quantity, int place);
}