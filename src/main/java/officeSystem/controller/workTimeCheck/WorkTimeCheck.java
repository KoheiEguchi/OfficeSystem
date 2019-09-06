package officeSystem.controller.workTimeCheck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Employee;
import officeSystem.model.WorkTime;
import officeSystem.repository.EmployeeRepository;
import officeSystem.repository.WorkTimeRepository;
import officeSystem.service.Common;

@Controller
public class WorkTimeCheck {
	@Autowired
	Common common;
	@Autowired
	EmployeeRepository employeeRep;
	@Autowired
	WorkTimeRepository workTimeRep;
	
	//出退勤管理、指定日の出退勤情報ページを表示
	@GetMapping("/workTimeCheck")
	public String workTimeCheckOpen(String department, String strDate, Model model) throws ParseException {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "other/login";
		//ログインしている場合
		}else {
			//社員絞り込みされていない場合
			if(department == null) {
				//社員一覧を取得
				List<Employee> employeeList = employeeRep.allEmployee();
				model.addAttribute("employeeList", employeeList);
			}
			
			//ログイン者が管理人か確認
			boolean isAdmin = common.isAdmin(model);
			//管理人である場合
			if(isAdmin == true) {
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
			//社員確認用
			String viewerName = common.getViewerName();
			model.addAttribute("viewerName", viewerName);
			
			/*ここからJavaScript用*/
			//絞り込み時の選択肢記録用
			model.addAttribute("department", department);
			/*ここまでJavaScript用*/
			
			return "workTimeCheck/workTimeCheck";
		}
	}
	
	//出退勤社員の絞り込み
	@PostMapping("/workTimeRefine")
	public String workTimeRefine(@RequestParam("department")String department, Model model) throws ParseException {
		//部署が選択されていない場合
		if(department.equals("未選択")) {
			department = null;
		//選択されている場合
		}else {
			List<Employee> employeeList = employeeRep.workTimeRefine(department);
			model.addAttribute("employeeList", employeeList);
		}
		
		//再表示用
		String strDate = null;
		workTimeCheckOpen(department, strDate, model);
		return "workTimeCheck/workTimeCheck";
	}
	
	//出退勤時刻を記録
	@PostMapping("/workTimeCheck")
	public String workTimeCheck(String name, String time, Model model) throws ParseException {
		//今日の日付を取得
		Date fullToday = new Date();
		Date today = DateUtils.truncate(fullToday, Calendar.DAY_OF_MONTH);
		//出退勤状態の設定
		int working = 0;
		//出勤時の場合
		if(time.equals("begin")) {
			workTimeRep.beginWorking(today, name);
			model.addAttribute("beginWorking", true);
			//出勤状態に変更
			working = 1;
		//退勤時の場合
		}else {
			workTimeRep.finishWorking(today, name);
			model.addAttribute("finishWorking", true);
			//退勤状態に変更
			working = 0;
		}
		//出退勤状態を変更
		employeeRep.changeWorking(working, name);
		
		//再表示用
		String department = null;
		String strDate = null;
		workTimeCheckOpen(department, strDate, model);
		return "workTimeCheck/workTimeCheck";
	}
}
