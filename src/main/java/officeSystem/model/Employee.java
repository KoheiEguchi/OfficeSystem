package officeSystem.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "office_system")
/* 社員情報 */
public class Employee {
	//セッション用
	public Employee(int id) {
		this.id = id;
	}
	
	//社員の通し番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	//姓
	@Column(name = "family_name", nullable = false)
	private String familyName;
	//名
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	//姓(ルビ)
	@Column(name = "family_name_ruby", nullable = false)
	private String familyNameRuby;
	//名(ルビ)
	@Column(name = "first_name_ruby", nullable = false)
	private String firstNameRuby;
	
	//年齢
	@Column(name = "age", nullable = false)
	private int age;
	
	//所属部署
	@Column(name = "department", nullable = false)
	private int department;
	
	//役職
	@Column(name = "position", nullable = false)
	private int position;
	
	//入社日時
	@Column(name = "join_date", nullable = false)
	private Timestamp joinDate;
	
	//ログイン用ID
	@Column(name = "login_id", nullable = false)
	private String loginId;
	
	//パスワード
	@Column(name = "password", nullable = false)
	private String password;
	
	//管理人チェック
	@Column(name = "role", nullable = false)
	private String role; 
}
