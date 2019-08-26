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
	//指定日の出退勤情報取得
	@Query(value = "SELECT * FROM work_time WHERE work_date = :work_date", nativeQuery = true)
	public List<WorkTime> getWorkTime(Date work_date);
	
	//出勤時刻記録
	@Modifying
	@Query(value = "INSERT INTO work_time (work_date, name, begin_time) VALUES (:work_date, :name, now())", nativeQuery = true)
	public void beginWorking(Date work_date, String name);
	
	//退勤時刻記録
	@Modifying
	@Query(value = "UPDATE work_time SET finish_time = now() WHERE work_date = :work_date AND name = :name", nativeQuery = true)
	public void finishWorking(Date work_date, String name);
}
