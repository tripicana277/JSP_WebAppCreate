package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.DeleteRecipeLogic;
import model.GetRecipeListLogic;
import model.RecipeMain;

@WebServlet("/DoDelete")
@MultipartConfig
public class DoDelete extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// JDBCドライバーのロード
			Class.forName("com.mysql.cj.jdbc.Driver");

			// パラメータから削除対象のrecipeのIDを取得し、削除処理を実行
			DeleteRecipeLogic deleteRecipeListLogic = new DeleteRecipeLogic(request.getParameter("delete"));
			deleteRecipeListLogic.execute();

			// 更新後のrecipeリストを取得し、リクエスト属性に設定
			GetRecipeListLogic getRecipeListLogic = new GetRecipeListLogic();
			List<RecipeMain> recipeList = getRecipeListLogic.execute();
			request.setAttribute("recipeList", recipeList);

			// JSPページにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Cooking-All.jsp");
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException | ServletException | IOException e) {
			// ドライバが見つからなかった場合の例外処理
			e.printStackTrace();
		}
	}
}