package officeSystem.controller.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import officeSystem.model.Message;
import officeSystem.repository.MessageRepository;
import officeSystem.service.Common;

@Controller
public class Index {
	@Autowired
	Common common;
	@Autowired
	MessageRepository messageRep;
	
	//目次ページを表示
	@GetMapping("/index")
	public String indexOpen(Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "other/login";
		//ログインしている場合
		}else {
			//最新の連絡事項を取得
			List<Message> newMessage = messageRep.getNewMessage();
			model.addAttribute("newMessage", newMessage);
			
			return "other/index";		
		}
		
	}
}
