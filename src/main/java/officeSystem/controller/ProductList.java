package officeSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Product;
import officeSystem.repository.ProductRepository;

@Controller
public class ProductList {
	@Autowired
	ProductRepository productRep;
	
	//商品在庫一覧ページを表示
	@GetMapping("productList")
	public String productListOpen(Model model) {
		//商品一覧取得
		List<Product> productList = productRep.allProduct();
		model.addAttribute("productList", productList);
		return "productList";
	}
	
	//商品を追加
	@PostMapping("addProduct")
	public String addProduct(@RequestParam("name")String name, @RequestParam("strQuantity")String strQuantity, @RequestParam("place")int place, 
			Model model) {
		// 入力ミスの確認用
		boolean formCheck = true;
		
		//商品名が空欄の場合
		if(name.equals("")) {
			formCheck = false;
			model.addAttribute("nameCheck", true);
		}
		
		//入荷数が空欄の場合
		if(strQuantity.equals("")) {
			formCheck = false;
			model.addAttribute("quantityCheck", true);
		}
		int quantity = Integer.parseInt(strQuantity);
		
		//保管場所が未選択の場合
		if(place == 0) {
			formCheck = false;
			model.addAttribute("placeCheck", true);
		}
		
		//全て正しく入力されていた場合
		if(formCheck == true) {
			productRep.addProduct(name, quantity, place);
			model.addAttribute("msg", "商品を追加しました。");
		}
		productListOpen(model);
		return "productList";
	}
}