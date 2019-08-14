package officeSystem.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import officeSystem.model.WorkTime;

@Repository
@Transactional
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer> {
	//出退勤情報取得
	@Query(value = "SELECT * FROM work_time", nativeQuery = true)
	public List<WorkTime> allWorkTime();
	
	//出勤時刻記録
	@Modifying
	@Query(value = "INSERT INTO work_time (work_date, employee_id, begin_time) VALUES (:today, :employee_id, now())", nativeQuery = true)
	public void beginWorking(Date today, int employee_id);
	
	//退勤時刻記録
	@Modifying
	@Query(value = "UPDATE work_time SET finish_time = cast(now() AS datetime) WHERE work_date = :today AND employee_id = :employee_id", nativeQuery = true)
	public void finishWorking(Date today, int employee_id);
}
