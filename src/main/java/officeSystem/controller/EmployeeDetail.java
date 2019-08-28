package officeSystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.model.WorkTime;
import officeSystem.repository.EmployeeRepository;
import officeSystem.repository.WorkTimeRepository;

@Controller
public class EmployeeDetail {
	@Autowired
	HttpSession session;
	@Autowired
	EmployeeRepository employeeRep;
	@Autowired
	WorkTimeRepository workTimeRep;
	
	//社員詳細ページを開く
	@GetMapping("/employeeDetail")
	public String employeeDetailOpen(@RequestParam("employeeId")int employeeId, Model model) {
		//社員の情報を取得
		List<Employee> employeeData = employeeRep.employeeData(employeeId);
		model.addAttribute("employeeData", employeeData);
		
		//社員の出退勤情報取得
		String employeeName = employeeRep.getViewerName(employeeId);
		List<WorkTime> employeeWorked = workTimeRep.employeeWorked(employeeName);
		model.addAttribute("employeeWorked", employeeWorked);
		
		int viewerId = (int)session.getAttribute("viewerId");
		//ログイン者が管理人か確認
		String role = employeeRep.checkRole(viewerId);
		//管理人だった場合
		if(role.equals("admin")) {
			model.addAttribute("isAdmin", true);
		}
		
		return "employeeDetail";
	}
}
