package officeSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Product;
import officeSystem.repository.ProductRepository;

@Controller
public class ProductUpdate {
	@Autowired
	ProductRepository productRep;
	
	//商品情報更新ページを開く
	@GetMapping("/productUpdate")
	public String productUpdateOpen(@RequestParam("productId")int productId, Model model) {
		//商品詳細取得
		List<Product> productDetail = productRep.getProductDetail(productId);
		model.addAttribute("productDetail", productDetail);
		
		return "productUpdate";
	}
	
	//商品情報を更新する
}
