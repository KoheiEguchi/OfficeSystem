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
	
	//社員一覧取得
	@Query(value = "SELECT * FROM employee ORDER BY position ASC", nativeQuery = true)
	public List<Employee> allEmployee();
	
	//役職順に並び替え
	@Query(value = "SELECT * FROM employee ORDER BY position ASC", nativeQuery = true)
	public List<Employee> employeeSortPosition();
	//年齢順に並び替え
	@Query(value = "SELECT * FROM employee ORDER BY age ASC", nativeQuery = true)
	public List<Employee> employeeSortAge();
	//入社日時順に並び替え
	@Query(value = "SELECT * FROM employee ORDER BY join_date ASC", nativeQuery = true)
	public List<Employee> employeeSortJoinDate();
	//名前順に並び替え
	@Query(value = "SELECT * FROM employee ORDER BY family_name_ruby ASC, first_name_ruby ASC", nativeQuery = true)
	public List<Employee> employeeSortName();
	//所属部署順に並び替え
	@Query(value = "SELECT * FROM employee ORDER BY department ASC", nativeQuery = true)
	public List<Employee> employeeSortDepartment();
	
	//社員登録
	@Modifying
	@Query(value = 
			"INSERT INTO employee (family_name, first_name, family_name_ruby, first_name_ruby, age, department, position, login_id, password) "
			+ "VALUES (:family_name, :first_name, :family_name_ruby, :first_name_ruby, :age, :department, :position, :login_id, :password)", 
					nativeQuery = true)
	public void employeeCreate(String family_name, String first_name, String family_name_ruby, String first_name_ruby, int age, 
			int department, int position, String login_id, String password);
	
	//削除する社員の情報を取得
	@Query(value = "SELECT * FROM employee WHERE id = :id", nativeQuery = true)
	public List<Employee> employeeData(int id);
	
	//社員削除
	@Modifying
	@Query(value = "DELETE FROM employee WHERE id = :id", nativeQuery = true)
	public void employeeDelete(int id);
}
