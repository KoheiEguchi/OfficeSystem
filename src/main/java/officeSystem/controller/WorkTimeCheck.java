package officeSystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
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
	
	//出退勤管理、指定日の出退勤情報ページを表示
	@GetMapping("/workTimeCheck")
	public String workTimeCheckOpen(String strDate, Model model) throws ParseException {
		//社員一覧を取得
		List<Employee> employeeList = employeeRep.allEmployee();
		model.addAttribute("employeeList", employeeList);
		
		int viewerId = (int)session.getAttribute("viewerId");
		//ログイン者が管理人か確認
		String role = employeeRep.checkRole(viewerId);
		//管理人だった場合
		if(role.equals("admin")) {
			model.addAttribute("isAdmin", true);
			
			Date selectDate = null;
			//日付指定がない場合
			if(strDate == null) {
				//今日の日付を取得
				Date fullToday = new Date();
				selectDate = DateUtils.truncate(fullToday, Calendar.DAY_OF_MONTH);
			//日付指定されている場合
			}else {
				//日付をDate型に変換
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				selectDate = sdf.parse(strDate);
			}
			//指定日の出退勤情報を取得
			List<WorkTime> workTimeList = workTimeRep.getWorkTime(selectDate);
			model.addAttribute("workTimeList", workTimeList);
			//日付表示用
			model.addAttribute("workDate", selectDate);
		}
		return "workTimeCheck";
	}
	
	//出退勤時刻を記録
	@PostMapping("/workTimeCheck")
	public String workTimeCheck(int employeeId, String time, Model model) throws ParseException {
		//今日の日付を取得
		Date fullToday = new Date();
		Date today = DateUtils.truncate(fullToday, Calendar.DAY_OF_MONTH);
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
			workTimeRep.finishWorking(today, employeeId);
			model.addAttribute("finishWorking", true);
			//退勤状態に変更
			working = 0;
		}
		//出退勤状態を変更
		employeeRep.changeWorking(working, employeeId);
		
		String strDate = null;
		workTimeCheckOpen(strDate, model);
		return "workTimeCheck";
	}
}
