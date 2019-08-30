package officeSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;
import officeSystem.service.Common;

@Controller
public class EmployeeDelete {
	@Autowired
	EmployeeList employeeList;
	@Autowired
	Common common;
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員削除ページを表示
	@GetMapping("/employeeDelete")
	public String employeeDeleteOpen(int employeeId, Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "login";
		//ログインしている場合
		}else {
			//管理人専用ページを開いて良いか確認
			boolean admin = common.isOpenAdminPage(model);
			//管理人でない場合
			if(admin == false) {
				return "index";
			//管理人の場合
			}else {
				//削除する社員の情報を取得
				List<Employee> employeeData = employeeRep.employeeData(employeeId);
				model.addAttribute("employeeData", employeeData);
				
				return "employeeDelete";
			}
		}
	}
	
	//社員を削除する
	@PostMapping("/employeeDelete")
	public String employeeDelete(int employeeId, Model model) {
		employeeRep.employeeDelete(employeeId);
		model.addAttribute("msg", "社員を削除しました。");
		
		employeeList.employeeListOpen(model);
		return "employeeList";
	}
}
