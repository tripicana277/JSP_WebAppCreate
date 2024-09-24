<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>入力フォーム</title>

<!-- ページ全体のスタイルを定義 -->
<style>
body {
	font-family: 'メイリオ'; /* 日本語対応のフォントを指定 */
	font-size: 18px;
	background-image: url('watercolor_00686.jpg'); /* 背景画像を設定 */
	background-size: cover; /* 背景画像を要素の大きさに合わせて拡大・縮小 */
	background-position: center center; /* 中央に配置 */
	color: #421000; /* 文字色を設定 */
	display: flex; /* フレキシブルボックスレイアウトでは位置 */
	justify-content: center; /* 中央に配置 */
	align-items: center; /*コンテナの縦の中央に配置*/
	flex-direction: column; /*コンテナ内のアイテムを縦方向に並べる*/
}

h1 {
	margin-bottom: 20px;
	text-align: center;
}

.centered-text {
	text-align: center;
	margin-bottom: 20px;
}

table {
	width: 850px;
	border-collapse: collapse; /*セルの境界線の結合*/
	margin-top: 15px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* テーブルに影をつけて立体感を表現 */
	background-color: rgba(255, 255, 255, 0.85); /* 背景色を半透明に設定 */
	border-radius: 8px; /*要素の角を丸くする*/
	overflow: hidden; /*要素の境界を超えるコンテンツを隠す*/
}

table th, table td {
	text-align: center;
	vertical-align: middle; /*テキストをセルの水平中央に配置*/
	padding: 15px; /*余白の設定*/
	font-size: 1em;
}

button, input[type="submit"], input[type="button"] {
	background-color: #421000;
	border: none;
	color: #FFFFFF; /* ボタンの文字色を指定 */
	padding: 10px 20px; /*ボタン文字のスペース*/
	text-align: center; /*テキストを中央に設定*/
	font-size: 18px;
	text-decoration: none; /*テキストの装飾*/
	margin: 4px 2px; /*ボタン周りのスペース*/
	cursor: pointer; /*カーソルを手の形に変更*/
	border-radius: 8px; /*要素の角を丸くする*/
	transition: background 0.3s ease, box-shadow 0.3s ease;/* 背景色と影の遷移を設定 */
}

button:hover, input[type="submit"]:hover, input[type="button"]:hover {
	background-color: #5e2500; /* ホバー時の背景色を指定 */
}

button:active, input[type="submit"]:active, input[type="button"]:active
	{
	background-color: #3e1a00;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* クリック時の影を強調 */
}

.transparent-table {
	background-color: rgba(255, 255, 255, 0.5); /* テーブルの背景を半透明に設定 */
}

.transparent-table th, .transparent-table td {
	padding: 10px; /*テーブルのヘッダ,データにスペースを追加*/
}
</style>

<!-- クライアントサイドでフォーム入力のバリデーションを行うJavaScript -->
<!-- (フォームが空の場合ダイアログを表示) -->
<script>
	function validateForm() {
		var requiredFields = ["recipeName", "fileName", "comment", "number"];
		for (var i = 0; i < requiredFields.length; i++) {
			// 入力データの最初の値を取得
			var field = document.getElementsByName(requiredFields[i])[0];
			// フィールドが空でないことを確認(文字列のスペースを取り除き比較)
			if (field.value.trim() === "") {
				// フィールドが空の場合エラーメッセージを表示(型変換無しで比較)
				alert(field.name + "を入力してください。");
				return false;
			}
		}

		var materials = document.getElementsByName("material[]");
		for (var i = 0; i < materials.length; i++) {
			// 材料フィールドが空でないことを確認
			if (materials[i].value.trim() === "") {
				// 材料フィールドが空の場合エラーメッセージを表示(型変換無しで比較)
				alert("材料を入力してください。");
				return false;
			}
		}

		var quantities = document.getElementsByName("quantity[]");
		for (var i = 0; i < quantities.length; i++) {
			// 数量フィールドが空でないことを確認
			if (quantities[i].value.trim() === "") {
				// 数量フィールドが空の場合エラーメッセージを表示(型変換無しで比較)
				alert("数量を入力してください。");
				return false;
			}
		}

		var fileNames = document.getElementsByName("fileName2[]");
		for (var i = 0; i < fileNames.length; i++) {
			// 作り方の画像フィールドが空でないことを確認
			if (fileNames[i].value.trim() === "") {
				// 作り方の画像フィールドが空の場合エラーメッセージを表示(型変換無しで比較)
				alert("作り方の画像を入力してください。");
				return false;
			}
		}

		var howToMakes = document.getElementsByName("howToMake[]");
		for (var i = 0; i < howToMakes.length; i++) {
			// 作り方フィールドが空でないことを確認
			if (howToMakes[i].value.trim() === "") {
				// 作り方フィールドが空の場合エラーメッセージを表示(型変換無しで比較)
				alert("作り方を入力してください。");
				return false;
			}
		}
		return true; // すべてのチェックが成功した場合にフォームを送信
	}

	// テーブルに新しい行を追加する関数
	function addRow(tableId, rowHtml) {
		var table = document.getElementById(tableId).getElementsByTagName('tbody')[0];
		var newRow = table.insertRow();// <tr>を挿入
		newRow.innerHTML = rowHtml;
	}

	// 材料の新しい行を追加する関数
	function addMaterialRow() {
		var rowHtml = `
			<td>・<input type="text" name="material[]" /></td>
			<td><input type="text" name="quantity[]" /></td>
			<td><input type="button" value="削除" onclick="deleteRow(this, 'materialTable')"></td>
		`;
		addRow('materialTable', rowHtml);
	}

	// 作り方の新しい行を追加する関数
	function addHowToMakeRow() {
		var rowHtml = `
			<td><input type="file" name="fileName2[]" /></td>
			<td>・<input type="text" name="howToMake[]" /></td>
			<td><input type="button" value="削除" onclick="deleteRow(this, 'howToMakeTable')"></td>
		`;
		addRow('howToMakeTable', rowHtml);
	}

	// 指定した行を削除する関数
	function deleteRow(button, tableId) {
		var table = document.getElementById(tableId).getElementsByTagName('tbody')[0];
		var rows = table.getElementsByTagName('tr');
		if (rows.length > 1) {
			// 行が2行の以上の場合は削除
			var row = button.closest('tr');
			row.parentNode.removeChild(row);
		} else {
			alert("これ以上削除できません。"); // 行が1つしかない場合の警告メッセージ
		}
	}
</script>
</head>
<body>
	<!-- フォームの開始タグ -->
	<form action="DoSet" method="post" enctype="multipart/form-data"
		onsubmit="return validateForm();">
		<h1>入力フォーム</h1>
		<p class="centered-text">
			1.各項目に内容を入力してください。<br>2.全ての項目を入力した後、「登録」ボタンを押してレシピを投稿してください。
		</p>
		<input type="submit" value="登録">

		<!-- レシピ名、画像、コメントを入力するテーブル -->
		<table border="1">
			<tr bgcolor="silver">
				<th>レシピ名称</th>
				<th>レシピ画像ファイル<br>(例: .png、.jpg、.bmp、.webp)
				</th>
				<th>コメント</th>
			</tr>
			<tr class="transparent-table">
				<td><input type="text" name="recipeName"></td>
				<td><input type="file" name="fileName"></td>
				<td><input type="text" name="comment"></td>
			</tr>
		</table>

		<!-- 人数を入力するテーブル -->
		<table border="1">
			<tr bgcolor="silver">
				<th>人数</th>
			</tr>
			<tr class="transparent-table">
				<td><input type="text" name="number">人分</td>
			</tr>
		</table>

		<!-- 材料と数量を入力するテーブル -->
		<table id="materialTable" border="1">
			<thead>
				<tr bgcolor="silver">
					<th>材料</th>
					<th>数量<br>(単位も含めて入力してください<br>例:「1個」「大さじ1」)
					</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>・<input type="text" name="material[]" /></td>
					<td><input type="text" name="quantity[]" /></td>
					<td><input type="button" value="削除"
						onclick="deleteRow(this, 'materialTable')"></td>
				</tr>
			</tbody>
		</table>
		<button type="button" onclick="addMaterialRow()">「材料」の行を追加</button>

		<!-- 作り方を入力するテーブル -->
		<table id="howToMakeTable" border="1">
			<thead>
				<tr bgcolor="silver">
					<th>作り方画像ファイル<br>(.png、.jpg、.bmp、.webp)
					</th>
					<th>作り方</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="file" name="fileName2[]" /></td>
					<td>・<input type="text" name="howToMake[]" /></td>
					<td><input type="button" value="削除"
						onclick="deleteRow(this, 'howToMakeTable')"></td>
				</tr>
			</tbody>
		</table>
		<button type="button" onclick="addHowToMakeRow()">「作り方」の行を追加</button>
	</form>

	<!-- レシピ一覧ページに移動するためのフォーム -->
	<form action="DoGetList" method="get">
		<input type="submit" value="レシピ一覧へ移動">
	</form>
</body>
</html>
