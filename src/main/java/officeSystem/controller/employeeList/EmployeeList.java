package officeSystem.controller.employeeList;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;
import officeSystem.service.Common;

@Controller
public class EmployeeList {
	@Autowired
	Common common;
	@Autowired
	EmployeeRepository employeeRep;
	
	//社員一覧ページを表示
	@GetMapping("/employeeList")
	public String employeeListOpen(Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "other/login";
		//ログインしている場合
		}else {
			//社員一覧取得
			List<Employee> employeeList = employeeRep.allEmployee();
			model.addAttribute("employeeList", employeeList);
			
			/*ここからJavaScript用*/
			//絞り込み時の選択肢初期化用
			model.addAttribute("name", "");
			model.addAttribute("ageMin", 18);
			model.addAttribute("ageMax", 65);
			/*ここまでJavaScript用*/
			
			return "employeeList/employeeList";
		}
	}
	
	//社員一覧を絞り込み
	@PostMapping("/employeeListRefine")
	public String employeeListRefine(
			@RequestParam("refineName")String refineName, @RequestParam("refineAgeMin")int refineAgeMin, @RequestParam("refineAgeMax")int refineAgeMax, 
			@RequestParam("department")String refineDepartment, @RequestParam("position")String refinePosition, 
			Model model) {
		boolean formCheck = true;
		
		//名前が全角カナでない場合
		if(Pattern.matches("^[ァ-ヶー]*$", refineName) == false) {
			formCheck = false;
			model.addAttribute("nameCheck", true);
		}
		
		//年齢が未指定の場合
		if(refineAgeMin == 0) {
			refineAgeMin = 18;
		}
		if(refineAgeMax == 0) {
			refineAgeMax = 65;
		}
		//年齢幅指定が逆の場合
		if(refineAgeMin > refineAgeMax) {
			formCheck = false;
			model.addAttribute("ageCheck", true);
		}
		
		//入力ミスがない場合
		if(formCheck == true) {
			//「未選択」を検索可能なよう書き換える
			if(refineDepartment.equals("未選択")) {
				refineDepartment = "";
			}
			if(refinePosition.equals("未選択")) {
				refinePosition = "";
			}
			
			//絞り込み
			List<Employee> employeeList = employeeRep.employeeRefine(refineName, refineAgeMin, refineAgeMax, refineDepartment, refinePosition);
			model.addAttribute("employeeList", employeeList);
			//誰も該当しなかった場合
			if(employeeList.isEmpty()) {
				model.addAttribute("noEmployee", true);
			}
			
			boolean isAdmin = common.isAdmin(model);
			if(isAdmin == true) {
				//管理人である
			}
			
			/*ここからJavaScript用*/
			//未指定の場合
			if(refineDepartment.equals("")) {
				refineDepartment = "未選択";
			}
			if(refinePosition.equals("")) {
				refinePosition = "未選択";
			}
			//絞り込み時の選択肢記録用
			model.addAttribute("name", refineName);
			model.addAttribute("ageMin", refineAgeMin);
			model.addAttribute("ageMax", refineAgeMax);
			model.addAttribute("department", refineDepartment);
			model.addAttribute("position", refinePosition);
			/*ここまでJavaScript用*/
		//入力ミスがあった場合
		}else {
			employeeListOpen(model);
		}
		return "employeeList/employeeList";
	}
}
