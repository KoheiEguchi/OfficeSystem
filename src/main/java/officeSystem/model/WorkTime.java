package officeSystem.model;

import java.sql.Date;
import java.sql.Time;

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
/* 出退勤管理 */
public class WorkTime {
	//出退勤の通し番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	//勤務日
	@Column(name = "work_date", nullable = false)
	private Date workDate;
	
	//社員名
	@Column(name = "family_name", nullable = false)
	private String familyName;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	//出勤時刻
	@Column(name = "begin_time")
	private Time beginTime;
	
	//退勤時刻
	@Column(name = "finish_time")
	private Time finishTime;
}
