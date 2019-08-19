package officeSystem.model;

import java.sql.Timestamp;

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
/* 商品入出荷情報 */
public class Transport {
	//入出荷情報の通し番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	//商品番号
	@Column(name = "product_id", nullable = false)
	private int productId;
	
	//入出荷日時
	@Column(name = "transport_date", nullable = false)
	private Timestamp transportDate;
	
	//社員番号
	@Column(name = "employee_id", nullable = false)
	private int employeeId;
	
	//入出荷選択
	@Column(name = "r_or_s", nullable = false)
	private String rOrS;
	
	//入出荷個数
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
}
