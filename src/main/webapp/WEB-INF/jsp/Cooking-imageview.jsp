<%@ page import="model.RecipeMain"%>
<%@ page import="model.RecipeMain.RecipeSubMaterial"%>
<%@ page import="model.RecipeMain.RecipeSubHowToMake"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- リクエスト属性からrecipeListを取得し、最初のレシピを選択 -->
<%
List<RecipeMain> recipeList = (List<RecipeMain>) request.getAttribute("recipeList");

// 三項条件演算子でデータ有無を確認
RecipeMain recipe = (recipeList != null && !recipeList.isEmpty()) ? recipeList.get(0) : null;

String recipeName = "名前がありません"; // デフォルト値
String fileName = "画像ファイルが有りません"; // デフォルト値
String comment = "コメントがありません"; // デフォルト値
String number = "人数がありません"; // デフォルト値
List<RecipeSubMaterial> recipeSubs = new ArrayList<>(); // 材料リストの初期化
List<RecipeSubHowToMake> recipeSubs2 = new ArrayList<>(); // 作り方リストの初期化

// レシピ情報が存在する場合
if (recipe != null) {
	// レシピ情報を取得し、デフォルト値を上書き(三項条件演算子)
	recipeName = recipe.getRecipeName() != null && !recipe.getRecipeName().isEmpty() ? recipe.getRecipeName() : recipeName;
	comment = recipe.getComment() != null && !recipe.getComment().isEmpty() ? recipe.getComment() : comment;
	number = recipe.getNumber() != null && !recipe.getNumber().isEmpty() ? recipe.getNumber() : number;
	fileName = recipe.getFileName() != null && !recipe.getFileName().isEmpty() ? recipe.getFileName() : fileName;
	recipeSubs = recipe.getRecipeSubs() != null ? recipe.getRecipeSubs() : recipeSubs;
	recipeSubs2 = recipe.getRecipeSubHowToMakes() != null ? recipe.getRecipeSubHowToMakes() : recipeSubs2;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピの紹介</title>
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

table th, table td {
	text-align: center;
	vertical-align: middle; /*テキストをセルの水平中央に配置*/
	padding: 15px; /*余白の設定*/
	font-size: 1em;
}

table th {
	background-color: #d9d9d9; /* ヘッダーの背景色を設定 */
	color: #421000; /* ヘッダーの文字色を設定 */
	font-weight: bold; /* ヘッダーの文字を太字に設定 */
}

.transparent-table td {
	background-color: rgba(255, 255, 255, 0.5); /* テーブルセルの背景色を半透明に設定 */
}

.fixed-height-image {
	height: 250px; /* 画像の高さを指定 */
	width: auto;
	border-radius: 8px; /* 角を丸くする */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 画像に影をつけて立体感を表現 */
}

.fixed-height-image2 {
	height: 100px; /* 画像の高さを指定 */
	width: auto;
	border-radius: 4px; /* 角を丸くする */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 画像に影をつけて立体感を表現 */
}

input[type="submit"] {
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

input[type="submit"]:hover {
	background-color: #5e2500; /* ホバー時の背景色を指定 */
}

input[type="submit"]:active {
	background-color: #3e1a00; /* クリック時の背景色を指定 */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* クリック時の影を強調 */
}

.centered {
	text-align: center; /* テキストを中央揃えに設定 */
	font-size: 1.2em;
	color: #421000;
}

</style>
</head>
<body>
	<!-- ページの見出し -->
	<h1>レシピの紹介</h1>

	<!-- レシピの基本情報を表示するテーブル -->
	<table border="1">
		<tr>
			<th>レシピ名称: <%=recipeName%></th>
			<th>コメント</th>
		</tr>
		<tr class="transparent-table">
			<td>
				<!-- 画像ファイルが存在する場合 -->
				<%
				if (!"画像ファイルが有りません".equals(fileName)) {
				%>
					<!-- 画像を表示 -->
					<img src="<%=request.getContextPath() + "/" + fileName%>" alt="アップロードされた画像" class="fixed-height-image">
				<%
				} else {
				%>
					<!-- 画像がアップロードされていない場合 -->
					<p>画像がアップロードされていません。</p>
				<%
				}
				%>
			</td>
			<td><%=comment%></td>
		</tr>
	</table>

	<!-- 人数を表示するテーブル -->
	<table border="1">
		<tr>
			<th>人数</th>
		</tr>
		<tr class="transparent-table">
			<td class="centered"><%=number%>人分</td>
		</tr>
	</table>

	<!-- 材料と数量を表示するテーブル -->
	<table border="1">
		<tr>
			<th>材料</th>
			<th>数量</th>
		</tr>
		<!-- recipeSubsが空でない場合 -->
		<%
		if (!recipeSubs.isEmpty()) {
		%>
			<%
			for (RecipeSubMaterial objRecipe : recipeSubs) {
			%>
			<tr class="transparent-table">
				<td><%=" ・ " + objRecipe.getMaterial()%></td>
				<td><%=objRecipe.getQuantity()%></td>
			</tr>
			<%
			}
			%>
		<!-- recipeSubsが空の場合 -->
		<%
		} else {
		%>
		<tr>
			<!-- 2列で表示 -->
			<td colspan="2">材料がありません。</td>
		</tr>
		<%
		}
		%>
	</table>

	<!-- 作り方画像と作り方を表示するテーブル -->
	<table border="1">
		<tr>
			<th>作り方画像</th>
			<th>作り方</th>
		</tr>
		<!-- recipeSubs2が空でない場合 -->
		<%
		if (!recipeSubs2.isEmpty()) {
		%>
			<%
			int i = 1;
			%> <!-- 手順番号の初期化 -->
			<%
			for (RecipeSubHowToMake objRecipe2 : recipeSubs2) {
			%>
			<tr class="transparent-table">
				<td>
					<!-- 作り方画像が存在する場合 -->
					<%if (!"画像ファイルが有りません".equals(objRecipe2.getFileName2())) {%>
						<!-- 画像を表示 -->
						<img src="<%=request.getContextPath() + "/" + objRecipe2.getFileName2()%>" alt="アップロードされた画像" class="fixed-height-image2">
					<%} else {%>
						<!-- 画像がアップロードされていない場合 -->
						<p>画像がアップロードされていません。</p>
					<%}%>
				</td>
				<td><%=i + " : " + objRecipe2.getHowToMake()%></td>
			</tr>
			<%i++;%> <!-- 手順番号をインクリメント -->
			<%}%>
		<!-- recipeSubs2が空の場合 -->
		<%} else {%>
		<tr>
			<!-- 2列で表示 -->
			<td colspan="2">作り方がありません。</td>
		</tr>
		<%}%>
	</table>

	<!-- レシピ一覧へ移動するためのフォーム -->
	<form action="DoGetList" method="get">
		<input type="submit" value="レシピ一覧へ移動">
	</form>
</body>
</html>
