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
	
	//名前
	@Column(name = "name", nullable = false)
	private String name;
	//名前(ルビ)
	@Column(name = "name_ruby", nullable = false)
	private String nameRuby;
	
	//年齢
	@Column(name = "age", nullable = false)
	private int age;
	
	//所属部署s
	@Column(name = "department", nullable = false)
	private String department;
	
	//役職
	@Column(name = "position", nullable = false)
	private String position;
	
	//入社日時
	@Column(name = "join_date", nullable = false)
	private Timestamp joinDate;
	
	//出退勤状態
	@Column(name = "working", nullable = false)
	private int working;
	
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
