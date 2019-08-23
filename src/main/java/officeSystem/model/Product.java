package officeSystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
/* 商品情報 */
public class Product {
	//商品の通し番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	//商品名
	@Column(name = "name", nullable = false)
	private String name;
	
	//在庫数
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	//管理場所
	@Column(name = "place", nullable = false)
	private String place;
}
