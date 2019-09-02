package officeSystem.controller.other;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import officeSystem.model.Employee;
import officeSystem.repository.EmployeeRepository;

@Controller
public class Login {
	@Autowired
	HttpSession session;
	@Autowired
	Index index;
	@Autowired
	EmployeeRepository employeeRep;
	
	//ログイン画面を表示
	@GetMapping({"/", "/login"})
	public String loginOpen() {
		return "login";
	}
	
	//ログイン処理
	@PostMapping("/login")
	public String login(@ModelAttribute Employee loginData, Model model) {
		Object loginCheck = null;
		//ログイン確認
		loginCheck = employeeRep.loginCheck(loginData.getLoginId(), loginData.getPassword());
		//ログインIDかパスワードが違う場合
		if(loginCheck == null) {
			model.addAttribute("msg", "ログインできません。");
			return "login";
		//ログインできた場合
		}else {
			int viewerId = ((Integer) loginCheck).intValue();
			//ログイン者のIDをセッションに登録
			session.setAttribute("viewerId", viewerId);
			//ログイン者の名前をセッションに登録
			String viewerName = employeeRep.getViewerName(viewerId);
			session.setAttribute("viewerName", viewerName);
			
			index.indexOpen(model);
			return "index";
		}
	}
	
	//ログアウト
	@GetMapping("/logout")
	public String logout(Model model) {
		//セッション削除
		session.invalidate();
		model.addAttribute("msg", "ログアウトしました。");
		return "login";
	}
}
