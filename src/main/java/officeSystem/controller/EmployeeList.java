package officeSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;

@Controller
public class EmployeeList {
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員一覧ページを表示
	@GetMapping("/employeeList")
	public String employeeListOpen(Model model) {
		//社員一覧取得
		List<Employee> employeeList = employeeRep.allEmployee();
		model.addAttribute("employeeList", employeeList);
		return "employeeList";
	}
	
	//社員一覧を並び替え
	@PostMapping("/employeeListSort")
	public String employeeListSort(@RequestParam("sort")String sort, Model model) {
		List<Employee> employeeList = null;
		
		switch(sort) {
		//役職順
		case "position":
			employeeList = employeeRep.employeeSortPosition();
			break;
		//年齢順
		case "age":
			employeeList = employeeRep.employeeSortAge();
			break;
		//入社日時順
		case "joinDate":
			employeeList = employeeRep.employeeSortJoinDate();
			break;
		//名前順
		case "name":
			employeeList = employeeRep.employeeSortName();
			break;
		//所属部署順
		case "department":
			employeeList = employeeRep.employeeSortDepartment();
			break;
		}
		model.addAttribute("employeeList", employeeList);
		return "employeeList";
	}
}
