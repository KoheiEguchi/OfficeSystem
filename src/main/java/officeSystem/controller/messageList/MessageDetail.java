package officeSystem.controller.messageList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Message;
import officeSystem.repository.MessageRepository;
import officeSystem.service.Common;

@Controller
public class MessageDetail {
	@Autowired
	Common common;
	@Autowired
	MessageRepository messageRep;
	
	//連絡詳細ページを開く
	@GetMapping("/messageDetail")
	public String messageDetailOpen(@RequestParam("messageId")int messageId, Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "login";
		//ログインしている場合
		}else {
			List<Message> messageData = messageRep.getMessage(messageId);
			model.addAttribute("messageData", messageData);
			
			return "messageDetail";
		}
	}
	
	//連絡を確認する
	@PostMapping("/messageDetail")
	public String messageDetail(@RequestParam("messageId")int messageId, Model model) {
		messageRep.messageConfirm(messageId);
		model.addAttribute("msg", "連絡を確認しました。");
		
		messageDetailOpen(messageId, model);
		return "messageDetail";
	}
}
