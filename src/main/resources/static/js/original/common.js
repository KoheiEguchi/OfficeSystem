//indexとmessageListの「未確認」を太い赤字にする
function tableNoConfirmTextRed(){
	var trConfirm = $("#messages tr");
	for(var i = 0; i < trConfirm.length; i++){
		var confirm = $(trConfirm[i]).children("td")[3];
		noConfirm(confirm);
	}
}
//messageDetailの「未確認」を太い赤字にする
function noConfirmTextRed(){
	var confirm = document.getElementById("confirm");
	noConfirm(confirm);
}
//上二つの共通処理
function noConfirm(confirm){
	if(confirm.innerHTML == "未確認"){
		confirm.classList.add("noConfirm");
	}
}

//連絡事項のうち確認済のものを非表示にするか切り替える
function changeOnlyNoConfirm(){
	var btnText = document.getElementById("btnText");
	
	//toggleは列を消したらtrue、戻したらfalse
	var toggle = toggleHiddenLine();
	//確認済みのものを非表示にする場合
	if(toggle){
		btnText.value = "全て表示";
	//全ての連絡事項を表示する場合
	}else{
		btnText.value = "未確認のものだけ表示";
	}
}
//上での非表示化処理
function toggleHiddenLine(){
	var trConfirm = $("#messages tr");
	for(var i = 0; i < trConfirm.length; i++){
		//各列を参照し「確認済」の列のhiddenLineを切り替える
		if($(trConfirm[i]).children("td")[3].innerHTML == "確認済"){
			 var toggle = trConfirm[i].classList.toggle("hiddenLine");
		}
	}
	return toggle;
}

//出退勤の部署絞り込み条件を残す
function workTimeRefine(department){
	if(department != null){
		document.getElementById("department").value = department;
	}
}
//社員一覧の検索条件を残す
function employeeRefine(name, ageMin, ageMax, department, position){
	if(name != null){
		document.getElementById("name").value = name;
	}
	if(ageMin != null){
		document.getElementById("ageMin").value = ageMin;
	}
	if(ageMax != null){
		document.getElementById("ageMax").value = ageMax;
	}
	if(department != null){
		document.getElementById("department").value = department;
	}
	if(position != null){
		document.getElementById("position").value = position;
	}
}

//社員、商品削除時に確認する
function deleteCheck(){
	if(confirm("本当に削除しますか？(戻せません！)")){
		return true;
	}else{
		alert("削除を中止しました。");
		return false;
	}
}
