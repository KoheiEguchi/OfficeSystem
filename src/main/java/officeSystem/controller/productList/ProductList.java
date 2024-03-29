package officeSystem.controller.productList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Product;
import officeSystem.repository.ProductRepository;
import officeSystem.service.Common;

@Controller
public class ProductList {
	@Autowired
	Common common;
	@Autowired
	ProductRepository productRep;
	
	//商品管理ページを表示
	@GetMapping("/productList")
	public String productListOpen(Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "other/login";
		//ログインしている場合
		}else {
			//商品一覧取得
			List<Product> productList = productRep.allProduct();
			model.addAttribute("productList", productList);
			
			//ログイン者が管理人か確認
			boolean isAdmin = common.isAdmin(model);
			if(isAdmin == true) {
				//管理人である
			}
			
			return "productList/productList";
		}
	}
	
	//新商品を追加
	@PostMapping("/addNewProduct")
	public String addNewProduct(@RequestParam("name")String name, @RequestParam("strQuantity")String strQuantity, @RequestParam("place")String place, 
			Model model) {
		// 入力ミスの確認用
		boolean formCheck = true;
		int quantity = 0;
		
		//商品名が空欄の場合
		if(name.equals("")) {
			formCheck = false;
			model.addAttribute("nameCheck", true);
		}
		
		//入荷数に数字が入力されていない場合
		if(strQuantity.equals("") || StringUtils.isNumeric(strQuantity) == false) {
			formCheck = false;
			model.addAttribute("quantityCheck", true);
		//数字が入力されている場合
		}else {
			quantity = Integer.parseInt(strQuantity);
		}
		
		//保管場所が未選択の場合
		if(place.equals("未選択")) {
			formCheck = false;
			model.addAttribute("placeCheck", true);
		}
		
		//全て正しく入力されていた場合
		if(formCheck == true) {
			productRep.addNewProduct(name, quantity, place);
			model.addAttribute("msg", "商品を追加しました。");
		}
		productListOpen(model);
		return "productList/productList";
	}
}
