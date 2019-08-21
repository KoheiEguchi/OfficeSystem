package officeSystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
	HttpSession session;
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員一覧ページを表示
	@GetMapping("/employeeList")
	public String employeeListOpen(Model model) {
		//社員一覧取得
		List<Employee> employeeList = employeeRep.allEmployee();
		model.addAttribute("employeeList", employeeList);
		
		int viewerId = (int)session.getAttribute("viewerId");
		//ログイン者が管理人か確認
		String role = employeeRep.checkRole(viewerId);
		//管理人だった場合
		if(role.equals("admin")) {
			model.addAttribute("isAdmin", true);
		}
		
		return "employeeList";
	}
	
	//社員一覧を絞り込み
	@PostMapping("/employeeListRefine")
	public String employeeListRefine(@RequestParam("refineFamilyName")String refinefamilyName, @RequestParam("refineFirstName")String refineFirstName, 
			@RequestParam("refineAgeMin")String refineAgeMin, @RequestParam("refineAgeMax")String refineAgeMax, 
			@RequestParam("department")String refineDepartment, @RequestParam("position")String refinePosition, 
			Model model) {
		int ageMin = 0;
		int ageMax = 0;
		//年齢に数字が入力されていない場合
		if(StringUtils.isNumeric(refineAgeMin) == false || StringUtils.isNumeric(refineAgeMax) == false || ageMin > ageMax) {
			//model.addAttribute("ageCheck", true);
			ageMin = 18;
			ageMax = 65;
		//数字が入力されている場合
		}else {
			ageMin = Integer.parseInt(refineAgeMin);
			ageMax = Integer.parseInt(refineAgeMax);
		}
			
		//絞り込み
		List<Employee> employeeList = 
				employeeRep.employeeRefine(refinefamilyName, refineFirstName, ageMin, ageMax, refineDepartment, refinePosition);
		model.addAttribute("employeeList", employeeList);
		int viewerId = (int)session.getAttribute("viewerId");
		//ログイン者が管理人か確認
		String role = employeeRep.checkRole(viewerId);
		//管理人だった場合
		if(role.equals("admin")) {
			model.addAttribute("isAdmin", true);
		}
		
		return "employeeList";
	}
}
