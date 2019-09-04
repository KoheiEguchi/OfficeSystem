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
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Message {
	//連絡事項の通し番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	//連絡日時
	@Column(name = "contact_date", nullable = false)
	private Timestamp contactDate;
	
	//連絡者
	@Column(name = "contact_name", nullable = false)
	private String contactName;
	
	//連絡内容
	@Column(name = "message", nullable = false)
	private String message;
	
	//確認チェック
	@Column(name = "confirm", nullable = false)
	private int confirm;
	
}
