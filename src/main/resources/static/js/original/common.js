//indexとmessageListの「未確認」を太い赤字にする
function tableNoConfirmTextRed(){
	var trConfirm = $("#messages tr");
	for(var i = 0; i < trConfirm.length; i++){
		var line = $(trConfirm[i]);
		var confirm = line.children("td")[3];
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
	//確認済みのものを非表示にする場合
	if(btnText.value == "未確認のものだけ表示"){
		var trConfirm = $("#messages tr");
		for(var i = 0; i < trConfirm.length; i++){
			var line = $(trConfirm[i]);
			var confirm = line.children("td")[3];
			//「確認済」の列を非表示にする
			if(confirm.innerHTML == "確認済"){
				alert(line);
				line.classList.add("hiddenLine");  //lineがtr要素を持っていないためaddができない
			}
		}
		alert("c");
		btnText.value = "全て表示";
	//全ての連絡事項を表示する場合
	}else{											//まだ手付かず
		var trConfirm = $("#messages tr");
		for(var i = 0; i < trConfirm.length; i++){
			var line = $(trConfirm[i]);
			var confirm = line.children("td")[3];
			if(confirm.innerHTML == "確認済"){
				var messageLine = document.getElementById("messageLine");
				messageLine.classList.add("hiddenLine");
			}
		}
		btnText.value = "未確認のものだけ表示";
	}
	alert("e");
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