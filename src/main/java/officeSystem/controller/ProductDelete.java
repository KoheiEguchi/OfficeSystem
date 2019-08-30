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
import officeSystem.service.Common;

@Controller
public class ProductDelete {
	@Autowired
	Common common;
	@Autowired
	ProductList productList;
	@Autowired
	ProductRepository productRep;
	
	//商品削除ページを表示
	@GetMapping("/productDelete")
	public String productDeleteOpen(@RequestParam("productId")int productId, Model model) {
		//ログイン確認
		int viewerId = common.isLogin(model);
		//ログインしていない場合
		if(viewerId == 0) {
			return "login";
		//ログインしている場合
		}else {
			//管理人専用ページを開いて良いか確認
			boolean admin = common.isOpenAdminPage(model);
			//管理人でない場合
			if(admin == false) {
				return "index";
			//管理人の場合
			}else {
				//商品詳細取得
				List<Product> productDetail = productRep.getProductDetail(productId);
				model.addAttribute("productDetail", productDetail);
				
				return "productDelete";
			}
		}
	}
	
	//商品削除
	@PostMapping("/productDelete")
	public String productDelete(@RequestParam("productId")int productId, Model model) {
		productRep.productDelete(productId);
		model.addAttribute("msg", "商品を削除しました。");
		
		productList.productListOpen(model);
		return "productList";
	}
}
