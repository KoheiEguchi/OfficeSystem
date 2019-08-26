package officeSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;

@Controller
public class EmployeeCreate {
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員登録画面を表示
	@GetMapping("/employeeCreate")
	public String employeeCreateOpen() {
		return "employeeCreate";
	}
	
	//社員を登録
	@PostMapping("/employeeCreate")
	public String employeeCreate(
			@ModelAttribute Employee employeeCreateData, @RequestParam("pass1")String pass1, @RequestParam("pass2")String pass2, Model model) {
		// 入力ミスの確認用
		boolean formCheck = true;
		
		String name = employeeCreateData.getName();
		String nameRuby = employeeCreateData.getNameRuby();
		//氏名が空欄の場合
		if(name.equals("") || nameRuby.equals("")) {
			formCheck = false;
			model.addAttribute("nameCheck", true);
		}
		
		int age = employeeCreateData.getAge();
		//年齢が未選択の場合
		if(age == 0) {
			formCheck = false;
			model.addAttribute("ageCheck", true);
		}
		
		String department = employeeCreateData.getDepartment();
		//所属部署が未選択の場合
		if(department == "未選択") {
			formCheck = false;
			model.addAttribute("departmentCheck", true);
		}
		
		String position = employeeCreateData.getPosition();
		//役職が未選択の場合
		if(position == "未選択") {
			formCheck = false;
			model.addAttribute("positionCheck", true);
		}
		
		String loginId = employeeCreateData.getLoginId();
		//ログインIDが空欄の場合
		if(loginId.equals("")) {
			formCheck = false;
			model.addAttribute("loginIdCheck", true);
		}
		
		//パスワードが空欄の場合
		if(pass1.equals("") || pass2.equals("")) {
			formCheck = false;
			model.addAttribute("passNullCheck", true);
		//パスワードが同じでない場合
		}else if(!(pass1.equals(pass2))) {
			formCheck = false;
			model.addAttribute("passwordCheck", true);
		}
		
		//全て正しく入力されていた場合
		if(formCheck == true) {
			String password = pass1;
			employeeRep.employeeCreate(name, nameRuby, age, department, position, loginId, password);
			model.addAttribute("msg", "社員を登録しました。");
			
			return "login";
		//正しくない入力があった場合
		}else {
			return "employeeCreate";
		}
	}
}
