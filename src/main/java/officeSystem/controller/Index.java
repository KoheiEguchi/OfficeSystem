package officeSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index {
	//目次ページを表示
	@GetMapping("/index")
	public String indexOpen() {
		return "index";
	}
}
