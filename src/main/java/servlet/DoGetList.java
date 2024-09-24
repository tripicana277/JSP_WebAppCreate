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

import model.GetRecipeListLogic;
import model.RecipeMain;

@WebServlet("/DoGetList")
@MultipartConfig
public class DoGetList extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Recipeリストを取得するためのビジネスロジックを初期化
		GetRecipeListLogic getRecipeListLogic = new GetRecipeListLogic();

		// GetRecipeListLogicを使用して、すべてのRecipeオブジェクトをリストとして取得
		List<RecipeMain> recipeList = null;
		try {
			recipeList = getRecipeListLogic.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 取得したRecipeリストをリクエスト属性に設定し、JSPに渡す準備をする
		request.setAttribute("recipeList", recipeList);

		// Cooking-All.jspページにフォワードし、リクエスト属性recipeListを使用してRecipeリストを表示
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Cooking-All.jsp");
		dispatcher.forward(request, response);
	}
}
