package officeSystem.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import officeSystem.controller.Index;
import officeSystem.controller.Login;
import officeSystem.repository.EmployeeRepository;

@Service
public class Common {
	@Autowired
	HttpSession session;
	@Autowired
	Login login;
	@Autowired
	Index index;
	@Autowired
	EmployeeRepository employeeRep;
	
	//ログイン確認
	public int isLogin(Model model) {
		int viewerId = 0;
		Object loginCheck = session.getAttribute("viewerId");
		//ログインしていない場合
		if(loginCheck == null) {
			model.addAttribute("msg", "ログインしてください。");
			login.loginOpen();
		//ログインしている場合
		}else {
			viewerId = ((Integer)loginCheck).intValue();
		}
		return viewerId;
	}
	
	//社員名を取得
	public String getViewerName() {
		String viewerName = (String)session.getAttribute("viewerName");
		return viewerName;
	}
	
	//ログイン者が管理人か確認
	public boolean isAdmin(Model model) {
		boolean isAdmin = false;
		
		int viewerId = (int)session.getAttribute("viewerId");
		String role = employeeRep.checkRole(viewerId);
		//管理人だった場合
		if(role.equals("admin")) {
			model.addAttribute("isAdmin", true);
			isAdmin = true;
		}
		return isAdmin;
	}
	
	//管理人専用ページを開いて良いか確認
	public boolean isOpenAdminPage(Model model) {
		boolean admin = isAdmin(model);
		if(admin == false) {
			model.addAttribute("msg", "そのページは管理人以外閲覧できません。");
			index.indexOpen(model);
		}
		return admin;
	}
}
