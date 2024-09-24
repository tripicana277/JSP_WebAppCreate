package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import model.RecipeMain;
import model.RecipeMain.RecipeSubHowToMake;
import model.RecipeMain.RecipeSubMaterial;
import model.SetRecipeLogic;

@WebServlet("/DoSet")
@MultipartConfig
public class DoSet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ファイルをアップロードし、ファイル名を取得する
		String fileName = uploadFile(request.getPart("fileName"));
		if (fileName == null) {
			// アップロード中にエラーが発生した場合、エラーレスポンスを送信
			handleError(response, "ファイルのアップロード中にエラーが発生しました。");
			return;
		}

		// リクエストからRecipeオブジェクトを作成
		RecipeMain recipeMain = createRecipeFromRequest(request, fileName);

		// Recipeオブジェクトを用いてビジネスロジックを実行
		SetRecipeLogic setRecipeLogic = new SetRecipeLogic(recipeMain);
		List<RecipeMain> recipeList = null;
		try {
			recipeList = setRecipeLogic.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// ビジネスロジックの結果をリクエスト属性に設定
		request.setAttribute("recipeList", recipeList);

		// JSPページにフォワードして結果を表示
		forwardToJSP(request, response, "WEB-INF/jsp/Cooking-imageview.jsp");
	}

	/**
	* アップロードされたファイルを保存し、
	* ファイル名を返すメソッド
	*/
	private String uploadFile(Part filePart) {

		if (filePart == null) {
			// ファイルアップロードの
			// パートがnullの場合は処理しない
			return null;
		}
		// アップロードされたファイルの名前を取得
		String fileName = filePart.getSubmittedFileName();

		// 絶対パスを取得
		Path filePath = getFilePath(fileName);

		// 必要なディレクトリが存在しない場合は作成
		createDirectoriesIfNeeded(filePath.getParent().toFile());

		// ファイルを保存し、成功した場合はファイル名を返す
		return saveFile(filePart, filePath) ? fileName : null;
	}

	/**
	* ファイルの保存先絶対パスを取得するメソッド
	*/
	private Path getFilePath(String fileName) {
		// ルートディレクトリにファイルシステムパスを付加し返す
		return Path.of(getServletContext().getRealPath("/"), fileName);
	}

	/**
	* 指定されたディレクトリが存在しない場合、
	* 作成するメソッド
	*/
	private void createDirectoriesIfNeeded(File fileDir) {
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
	}

	/**
	* ファイルを保存するメソッド
	*/
	private boolean saveFile(Part filePart, Path filePath) {

		// ファイルの内容を読み込むための入力ストリームを取得
		try (InputStream inputStream = filePart.getInputStream()) {
			// ファイルを指定されたパスに保存
			// ファイルがすでに存在する場合上書き
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			// エラー発生時のログ出力
			log("ファイルのアップロード中にエラーが発生しました: " + e.getMessage(), e);
			return false;
		}
	}

	/**
	* リクエストから
	* RecipeMainオブジェクトを作成するメソッド
	*/
	private RecipeMain createRecipeFromRequest(HttpServletRequest request, String fileName)
			throws IOException, ServletException {

		// 材料と数量のリストを作成
		List<RecipeSubMaterial> recipeSubMaterials = createRecipeSubMaterials(
				request.getParameterValues("material[]"),
				request.getParameterValues("quantity[]"));

		// fileName2[]に一致する要素をリストに挿入
		List<Part> fileName2Parts = new ArrayList<>();
		for (Part part : request.getParts()) {
			if ("fileName2[]".equals(part.getName())) {
				fileName2Parts.add(part);
			}
		}

		// 作り方のリストを作成
		List<RecipeSubHowToMake> recipeSubHowToMakes = createRecipeSubHowToMakes(
				fileName2Parts,
				request.getParameterValues("howToMake[]"));

		// RecipeMainオブジェクトを生成
		return new RecipeMain(
				request.getParameter("recipeName"),
				fileName,
				request.getParameter("comment"),
				request.getParameter("number"),
				recipeSubMaterials,
				recipeSubHowToMakes);
	}

	/**
	* RecipeSubMaterialオブジェクトの
	* リストを作成するメソッド
	*/
	private List<RecipeSubMaterial> createRecipeSubMaterials(String[] materials, String[] quantities) {
		List<RecipeSubMaterial> recipeSubMaterials = new ArrayList<>();
		if (materials != null && quantities != null) {
			for (int i = 0; i < materials.length; i++) {
				RecipeSubMaterial sub = new RecipeMain().new RecipeSubMaterial();
				sub.setMaterial(materials[i]);
				sub.setQuantity(quantities[i]);
				recipeSubMaterials.add(sub);
			}
		}
		return recipeSubMaterials;
	}

	/**
	* RecipeSubHowToMakeオブジェクトの
	* リストを作成するメソッド
	*/
	private List<RecipeSubHowToMake> createRecipeSubHowToMakes(List<Part> fileParts, String[] howToMake)
			throws IOException, ServletException {
		List<RecipeSubHowToMake> recipeSubHowToMakes = new ArrayList<>();
		if (fileParts != null && howToMake != null) {
			for (int i = 0; i < fileParts.size(); i++) {
				// 各ファイルをアップロード
				String fileName = uploadFile(fileParts.get(i));
				if (fileName == null) {
					throw new ServletException("ファイルのアップロード中にエラーが発生しました。");
				}
				// RecipeSubHowToMakeオブジェクトを生成
				RecipeSubHowToMake sub = new RecipeMain().new RecipeSubHowToMake();
				sub.setFileName2(fileName);
				sub.setHowToMake(howToMake[i]);
				recipeSubHowToMakes.add(sub);
			}
		}
		return recipeSubHowToMakes;
	}

	/**
	* JSPページにリクエストを
	* フォワードするメソッド
	*/
	private void forwardToJSP(HttpServletRequest request, HttpServletResponse response, String jspPath)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	/**
	* エラーレスポンスを
	* 送信するメソッド
	*/
	private void handleError(HttpServletResponse response, String message) throws IOException {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
	}
}
