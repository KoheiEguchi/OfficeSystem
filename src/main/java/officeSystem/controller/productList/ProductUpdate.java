package officeSystem.controller.productList;

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
public class ProductUpdate {
	@Autowired
	ProductDetail productDetail;
	@Autowired
	Common common;
	@Autowired
	ProductRepository productRep;
	
	//商品情報更新ページを開く
	@GetMapping("/productUpdate")
	public String productUpdateOpen(@RequestParam("productId")int productId, Model model) {
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
				
				return "productUpdate";
			}
		}
	}
	
	//商品情報を更新する
	@PostMapping("/productUpdate")
	public String productUpdate(@RequestParam("name")String name, @RequestParam("place")String place, @RequestParam("productId")int productId, Model model) {
		// 入力ミスの確認用
		boolean formCheck = true;
		
		//商品名が空欄の場合
		if(name.equals("")) {
			formCheck = false;
			model.addAttribute("nameCheck", true);
		}
		
		//保管場所が未選択の場合
		if(place.equals("未選択")) {
			formCheck = false;
			model.addAttribute("placeCheck", true);
		}
		
		//全て正しく入力されていた場合
		if(formCheck == true) {
			//商品情報更新
			productRep.productUpdate(name, place, productId);
			model.addAttribute("msg", "商品情報を更新しました。");
			
			productDetail.productDetailOpen(productId, model);
			return "productDetail";
		//正しく入力されていない場合
		}else {
			productUpdateOpen(productId, model);
			return "productUpdate";
		}
	}
}
