package officeSystem.controller.employeeList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;
import officeSystem.service.Common;

@Controller
public class EmployeeUpdate {
	@Autowired
	EmployeeDetail employeeDetail;
	@Autowired
	Common common;
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員情報更新ページを開く
	@GetMapping("/employeeUpdate")
	public String employeeUpdateOpen(@RequestParam("employeeId")int employeeId, Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "other/login";
		//ログインしている場合
		}else {
			//管理人専用ページを開いて良いか確認
			boolean admin = common.isOpenAdminPage(model);
			//管理人でない場合
			if(admin == false) {
				return "other/index";
			//管理人の場合
			}else {
				//社員の情報を取得
				List<Employee> employeeData = employeeRep.employeeData(employeeId);
				model.addAttribute("employeeData", employeeData);
				model.addAttribute("employeeId", employeeId);
				
				return "employeeList/employeeUpdate";
			}
		}
	}
	
	//社員情報を更新する
	@PostMapping("/employeeUpdate")
	public String employeeUpdate(
			@ModelAttribute Employee employeeUpdateData, 
			@RequestParam("newLoginId")String newLoginId, @RequestParam("newPass1")String newPass1, @RequestParam("newPass2")String newPass2, 
			@RequestParam("employeeId")int employeeId, Model model) {		
		Object loginCheck = null;
		//ログイン確認
		loginCheck = employeeRep.loginCheck(employeeUpdateData.getLoginId(), employeeUpdateData.getPassword());
		//ログインIDかパスワードが違う場合
		if(loginCheck == null) {
			model.addAttribute("msg", "ログインできません。");
			employeeUpdateOpen(employeeId, model);
			return "employeeList/employeeUpdate";
		//ログインできた場合
		}else {
			// 入力ミスの確認用
			boolean formCheck = true;
			
			String name = employeeUpdateData.getName();
			String nameRuby = employeeUpdateData.getNameRuby();
			//氏名が空欄の場合
			if(name.equals("") || nameRuby.equals("")) {
				formCheck = false;
				model.addAttribute("nameCheck", true);
			}
			
			int age = employeeUpdateData.getAge();
			//年齢が未選択の場合
			if(age == 0) {
				formCheck = false;
				model.addAttribute("ageCheck", true);
			}
			
			String department = employeeUpdateData.getDepartment();
			//所属部署が未選択の場合
			if(department.equals("未選択")) {
				formCheck = false;
				model.addAttribute("departmentCheck", true);
			}
			
			String position = employeeUpdateData.getPosition();
			//役職が未選択の場合
			if(position.equals("未選択")) {
				formCheck = false;
				model.addAttribute("positionCheck", true);
			}
			
			//ログインIDが空欄の場合
			if(newLoginId.equals("")) {
				formCheck = false;
				model.addAttribute("loginIdCheck", true);
			}
			
			//パスワードが空欄の場合
			if(newPass1.equals("") || newPass2.equals("")) {
				formCheck = false;
				model.addAttribute("passNullCheck", true);
			//パスワードが同じでない場合
			}else if(!(newPass1.equals(newPass2))) {
				formCheck = false;
				model.addAttribute("passwordCheck", true);
			}
			
			//全て正しく入力されていた場合
			if(formCheck == true) {
				String password = newPass1;
				employeeRep.employeeUpdate(name, nameRuby, age, department, position, newLoginId, password, employeeId);
				model.addAttribute("msg", "社員情報を更新しました。");
				
				employeeDetail.employeeDetailOpen(employeeId, model);
				return "employeeList/employeeDetail";
			//正しくない入力があった場合
			}else {
				employeeUpdateOpen(employeeId, model);
				return "employeeList/employeeUpdate";
			}
		}
	}
}
