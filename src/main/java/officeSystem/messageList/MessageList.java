package officeSystem.messageList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import officeSystem.model.Message;
import officeSystem.repository.MessageRepository;
import officeSystem.service.Common;

@Controller
public class MessageList {
	@Autowired
	Common common;
	@Autowired
	MessageRepository messageRep;
	
	//連絡事項ページを開く
	@GetMapping("message")
	public String messageListOpen(Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "login";
		//ログインしている場合
		}else {
			//全ての連絡事項を取得
			List<Message> messageList = messageRep.allMessage();
			model.addAttribute("messageList", messageList);
			//連絡がない場合
			if(messageList.isEmpty() == true) {
				model.addAttribute("noMessage", true);
			}
			
			return "messageList";
		}
	}
}
