<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.RecipeMain,java.util.List"%>

<!-- リクエスト属性からrecipeListを取得 -->
<%
List<RecipeMain> recipeList = (List<RecipeMain>) request.getAttribute("recipeList");
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>レシピ一覧</title>

<!-- ページ全体のスタイルを定義 -->
<style>	
body {
	font-family: 'メイリオ'; /* 日本語対応のフォントを指定 */
	font-size: 18px;
	background-image: url('watercolor_00686.jpg'); /* 背景画像を設定 */
	background-size: cover; /* 背景画像を要素の大きさに合わせて拡大・縮小 */
	background-position: center center; /* 中央に配置 */
	color: #421000; /* 文字色を設定 */
	display: flex; /* フレキシブルボックスレイアウトで配置 */
	justify-content: center; /* 中央に配置 */
	align-items: center; /*コンテナの縦の中央に配置*/
	flex-direction: column;/*コンテナ内のアイテムを縦方向に並べる*/
}

h1 {
	margin-bottom: 20px;
	text-align: center;
}

table {
	width: 800px;
	border-collapse: collapse; /*セルの境界線の結合*/
	margin-top: 15px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* テーブルに影をつけて立体感を表現 */
	background-color: rgba(255, 255, 255, 0.85); /* 背景色を半透明に設定 */
	border-radius: 8px; /*要素の角を丸くする*/
	overflow: hidden; /*要素の境界を超えるコンテンツを隠す*/
}

table td, table th {
	text-align: center;
	vertical-align: middle; /*テキストをセルの水平中央に配置*/
	padding: 15px; /*余白の設定*/
	font-size: 1em;
}

.fixed-height-image {
	height: 75px;
	width: auto;
	border-radius: 4px; /* 角を丸くする */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 画像に影をつけて立体感を表現 */
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

button:active, input[type="submit"]:active, input[type="button"]:active {
	background-color: #3e1a00;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* クリック時の影を強調 */
}
</style>
</head>
<body style="color: #421000;">
	<!-- ページの見出し -->
	<h1>レシピ一覧</h1>

	<!-- レシピ一覧を表示するテーブル -->
	<table border="1">
		<tr bgcolor="silver">
			<th>レシピ画像</th>
			<th>レシピ名称</th>
			<th>レシピ削除</th>
		</tr>

		<!-- recipeListがnullでなく、かつ空でない場合 -->
		<%
		if (recipeList != null && !recipeList.isEmpty()) {
		%>

		<!-- recipeList内の各Recipeオブジェクトに対して繰り返し処理を行う -->
		<%
		for (RecipeMain recipe : recipeList) {
		%>
		<tr>
			<td>
				<!-- 画像ファイルが存在する場合 -->
				<%if (!"画像ファイルが有りません".equals(recipe.getFileName())) {%>
					<!-- 画像を表示 -->
					<img src="<%=request.getContextPath() + "/" + recipe.getFileName()%>" alt="アップロードされた画像" class="fixed-height-image"> 
				<%} else {%>
					<!-- 画像がアップロードされていない場合 -->
					<p>画像がアップロードされていません。</p>
				<%}%>
			</td>
			<td>
				<!-- レシピ名称を表示し、クリックで詳細を表示 -->
				<form action="DoGet" method="get">
					<input type="submit" name="button" value="<%=recipe.getRecipeName()%>" />
				</form>
			</td>
			<td>
				<!-- レシピを削除するフォーム -->
				<form action="DoDelete" method="post">
					<!-- 削除するレシピ名称をhiddenで送信 -->
					<input type="hidden" name="delete" value="<%=recipe.getRecipeName()%>" />
					<button type="submit" onclick="return confirm('本当に削除しますか？');">削除</button>
				</form>
			</td>
		</tr>
		<%}%>
		<!-- recipeListが空の場合 -->
		<%} else {%>
		<p>メニューがありません。</p>
		<%}%>
	</table>

	<!-- 入力フォームページへ移動するためのフォーム -->
	<form action="Cooking.jsp">
		<input type="submit" value="入力フォームへ移動">
	</form>
</body>
</html>
