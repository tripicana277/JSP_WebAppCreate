package model;

import java.sql.SQLException;
import java.util.List;

import dao.RecipesDAO;

public class GetRecipeListLogic {

	// 全てのRecipeオブジェクトを取得するメソッド
	public List<RecipeMain> execute() throws SQLException {
		// DAOを使用してデータベースから全てのRecipeを取得
		RecipesDAO dao = new RecipesDAO();
		List<RecipeMain> recipeList = dao.getAll(); // 取得したRecipeオブジェクトのリストを保持

		// 取得したRecipeオブジェクトのリストを返す
		return recipeList;
	}
}
