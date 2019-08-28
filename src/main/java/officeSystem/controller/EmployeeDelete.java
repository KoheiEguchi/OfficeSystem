package officeSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;

@Controller
public class EmployeeDelete {
	@Autowired
	EmployeeList employeeList;
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員削除ページを表示
	@GetMapping("/employeeDelete")
	public String employeeDeleteOpen(int employeeId, Model model) {
		//削除する社員の情報を取得
		List<Employee> employeeData = employeeRep.employeeData(employeeId);
		model.addAttribute("employeeData", employeeData);
		
		return "employeeDelete";
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
