package officeSystem.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import officeSystem.model.Employee;
import officeSystem.model.WorkTime;
import officeSystem.repository.EmployeeRepository;
import officeSystem.repository.WorkTimeRepository;

@Controller
public class WorkTimeCheck {
	@Autowired
	HttpSession session;
	@Autowired
	EmployeeRepository employeeRep;
	@Autowired
	WorkTimeRepository workTimeRep;
	
	//出退勤管理ページを表示
	@GetMapping("/workTimeCheck")
	public String workTimeCheckOpen(Model model) {
		List<Employee> employeeList = employeeRep.allEmployee();
		model.addAttribute("employeeList", employeeList);
		
		int viewerId = (int)session.getAttribute("viewerId");
		//ログイン者が管理人か確認
		String role = employeeRep.checkRole(viewerId);
		//管理人だった場合
		if(role.equals("admin")) {
			model.addAttribute("isAdmin", true);
			//出退勤情報を取得
			List<WorkTime> workTimeList = workTimeRep.allWorkTime();
			model.addAttribute("workTimeList", workTimeList);
		}
		return "workTimeCheck";
	}
	
	//出退勤時刻を記録
	@PostMapping("/workTimeCheck")
	public String workTimeCheck(int employeeId, String time, Model model) {
		//今日の日付を取得
		Date today = new Date();
		//出退勤状態の設定
		int working = 0;
		//出勤時の場合
		if(time.equals("begin")) {
			workTimeRep.beginWorking(today, employeeId);
			model.addAttribute("beginWorking", true);
			//出勤状態に変更
			working = 1;
		//退勤時の場合
		}else {
			workTimeRep.finishWorking(today, employeeId);  //退勤時刻が記録されない
			model.addAttribute("finishWorking", true);
			//退勤状態に変更
			working = 0;
		}
		//出退勤状態を変更
		employeeRep.changeWorking(working, employeeId);
		
		workTimeCheckOpen(model);
		return "workTimeCheck";
	}
}
