package officeSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import officeSystem.model.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	//ログイン確認
	@Query(value = "SELECT id FROM employee WHERE login_id = :login_id AND password = :password", nativeQuery = true)
	public Object loginCheck(String login_id, String password);
	
	//ログイン者が管理人か確認
	@Query(value = "SELECT role FROM employee WHERE id = :id", nativeQuery = true)
	public String checkRole(int id);
	
	//IDから名前取得
	@Query(value = "SELECT name FROM employee WHERE id = :id", nativeQuery = true)
	public String getViewerName(int id);
	
	//社員一覧取得
	@Query(value = "SELECT * FROM employee", nativeQuery = true)
	public List<Employee> allEmployee();
	 
	//絞り込み
	@Query(value = "SELECT * FROM employee "
			+ "WHERE name_ruby LIKE %:name% AND (age BETWEEN :age_min AND :age_max) AND department LIKE %:department% AND position LIKE %:position%", 
			nativeQuery = true)
	public List<Employee> employeeRefine(String name, int age_min, int age_max, String department, String position);
	
	//出退勤社員絞り込み
	@Query(value = "SELECT * FROM employee WHERE department = :department", nativeQuery = true)
	public List<Employee> workTimeRefine(String department);
	
	//出退勤状態変更
	@Modifying
	@Query(value = "UPDATE employee SET working = :working WHERE name = :name", nativeQuery = true)
	public void changeWorking(int working, String name);
	
	//ログインID被り確認
	@Query(value = "SELECT id FROM employee WHERE login_id = :login_id", nativeQuery = true)
	public int loginIdSameCheck(String login_id);
	
	//社員登録
	@Modifying
	@Query(value = 
			"INSERT INTO employee (name, name_ruby, age, department, position, login_id, password) "
			+ "VALUES (:name, :name_ruby, :age, :department, :position, :login_id, :password)", 
					nativeQuery = true)
	public void employeeCreate(String name, String name_ruby, int age, String department, String position, String login_id, String password);
	
	//社員の情報を取得
	@Query(value = "SELECT * FROM employee WHERE id = :id", nativeQuery = true)
	public List<Employee> employeeData(int id);
	
	//社員情報更新
	@Modifying
	@Query(value = 
			"UPDATE employee SET name = :name, name_ruby = :name_ruby, age = :age, department = :department, position = :position, "
			+ "login_id = :login_id, password = :password WHERE id = :id", nativeQuery = true)
	public void employeeUpdate(String name, String name_ruby, int age, String department, String position, String login_id, String password, int id);
	
	//社員削除
	@Modifying
	@Query(value = "DELETE FROM employee WHERE id = :id", nativeQuery = true)
	public void employeeDelete(int id);
}
