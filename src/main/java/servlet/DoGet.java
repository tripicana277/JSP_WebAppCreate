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

import model.GetRecipeLogic;
import model.RecipeMain;

@WebServlet("/DoGet")
@MultipartConfig
public class DoGet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// リクエストパラメータからボタンの値を取得し、それを使用してGetRecipeLogicを初期化
		GetRecipeLogic getRecipeListLogic = new GetRecipeLogic(request.getParameter("button"));

		// GetRecipeLogicを使用してrecipeリストを取得
		List<RecipeMain> recipeList = null;
		try {
			recipeList = getRecipeListLogic.execute();

			// 取得したrecipeリストをリクエスト属性に設定
			request.setAttribute("recipeList", recipeList);
			// JSPページにフォワードして、recipeリストを表示
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Cooking-imageview.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
