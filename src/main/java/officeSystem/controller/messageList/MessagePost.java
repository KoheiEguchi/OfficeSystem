package officeSystem.controller.messageList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.repository.MessageRepository;
import officeSystem.service.Common;

@Controller
public class MessagePost {
	@Autowired
	Common common;
	@Autowired
	MessageList messageList;
	@Autowired
	MessageRepository messageRep;
	
	//連絡投稿ページを開く
	@GetMapping("messagePost")
	public String messagePostOpen(Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "login";
		//ログインしている場合
		}else {
			String viewerName = common.getViewerName();
			model.addAttribute("viewerName", viewerName);
			
			return "messagePost";
		}
	}
	
	@PostMapping("messagePost")
	public String messagePost(@RequestParam("name")String name, @RequestParam("message")String message, Model model) {
		// 入力ミスの確認用
		boolean formCheck = true;
		
		//連絡者名が空欄の場合
		if(name.equals("")) {
			formCheck = false;
			model.addAttribute("nameCheck", true);
		}
		
		//連絡内容が空欄の場合
		if(message.equals("")) {
			formCheck = false;
			model.addAttribute("messageCheck", true);
		}
		
		//全て正しく入力されていた場合
		if(formCheck == true) {
			messageRep.postMessage(name, message);
			model.addAttribute("msg", "連絡事項を投稿しました。");
			
			messageList.messageListOpen(model);
			return "messageList";
		//正しくない入力があった場合
		}else {
			messagePostOpen(model);
			return "messagePost";
		}
	}
}
