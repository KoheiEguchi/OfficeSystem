package officeSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import officeSystem.service.Common;

@Controller
public class Index {
	@Autowired
	Common common;
	
	//目次ページを表示
	@GetMapping("/index")
	public String indexOpen(Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "login";
		//ログインしている場合
		}else {
			return "index";		
		}
		
	}
}
