package officeSystem.controller.employeeList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.model.WorkTime;
import officeSystem.repository.EmployeeRepository;
import officeSystem.repository.WorkTimeRepository;
import officeSystem.service.Common;

@Controller
public class EmployeeDetail {
	@Autowired
	Common common;
	@Autowired
	EmployeeRepository employeeRep;
	@Autowired
	WorkTimeRepository workTimeRep;
	
	//社員詳細ページを開く
	@GetMapping("/employeeDetail")
	public String employeeDetailOpen(@RequestParam("employeeId")int employeeId, Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "other/login";
		//ログインしている場合
		}else {
			//社員の情報を取得
			List<Employee> employeeData = employeeRep.employeeData(employeeId);
			model.addAttribute("employeeData", employeeData);
			
			//社員の出退勤情報取得
			String employeeName = employeeRep.getViewerName(employeeId);
			List<WorkTime> employeeWorked = workTimeRep.employeeWorked(employeeName);
			model.addAttribute("employeeWorked", employeeWorked);
			
			//ログイン者が管理人か確認
			boolean isAdmin = common.isAdmin(model);
			if(isAdmin == true) {
				//管理人である
			}
			
			return "employeeList/employeeDetail";
		}
	}
}
