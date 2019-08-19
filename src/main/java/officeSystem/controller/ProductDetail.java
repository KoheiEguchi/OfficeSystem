package officeSystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import officeSystem.model.Product;
import officeSystem.model.Transport;
import officeSystem.repository.ProductRepository;
import officeSystem.repository.TransportRepository;

@Controller
public class ProductDetail {
	@Autowired
	HttpSession session;
	@Autowired
	ProductRepository productRep;
	@Autowired
	TransportRepository transportRep;
	
	//商品詳細ページを表示
	@GetMapping("productDetail")
	public String productDetailOpen(@RequestParam("productId")int productId, Model model) {
		//商品詳細取得
		List<Product> productDetail = productRep.getProductDetail(productId);
		model.addAttribute("productDetail", productDetail);
		
		//商品入出荷情報取得
		List<Transport> productTransport = transportRep.getProductTransport(productId);
		model.addAttribute("productTransport", productTransport);
		
		return "productDetail";
	}
	
	//入出荷
	@PostMapping("transport")
	public String transport(@RequestParam("stock")int stock, @RequestParam("productId")int productId, 
			@RequestParam("strQuantity")String strQuantity, @RequestParam("rOrS")String rOrS, Model model) {
		//担当者の番号を取得
		int viewerId = (int)session.getAttribute("viewerId");
		
		// 入力ミスの確認用
		boolean formCheck = true;
		int quantity = 0;
		
		//個数に数字が入力されていない場合
		if(strQuantity.equals("") || StringUtils.isNumeric(strQuantity) == false) {
			formCheck = false;
			model.addAttribute("quantityCheck", true);
		//数字が入力されている場合
		}else {
			quantity = Integer.parseInt(strQuantity);
		}
		
		//入出荷選択がされていない場合
		if(rOrS.equals("未選択")) {
			formCheck = false;
			model.addAttribute("rOrSCheck", true);
		//出荷する際在庫が足りない場合
		}else if(rOrS.equals("出荷") && stock - quantity < 0) {
			formCheck = false;
			model.addAttribute("msg", "在庫が足りません。");
		}
		
		//全て正しく入力されていた場合
		if(formCheck == true) {
			//変更後の個数
			int newQuantity = 0;
			//入荷する場合
			if(rOrS.equals("入荷")) {
				newQuantity = stock + quantity;
			//出荷する場合
			}else if(rOrS.equals("出荷")) {
				newQuantity = stock - quantity;
			}
			//商品入出荷
			productRep.updateQuantity(newQuantity, productId);
			//入出荷情報記録
			transportRep.addTransportData(productId, viewerId, rOrS, quantity);
			model.addAttribute("msg", "個数を変更しました。");
		}
		productDetailOpen(productId, model);
		return "productDetail";
	}
}
