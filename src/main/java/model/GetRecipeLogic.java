package model;

import java.sql.SQLException;
import java.util.List;

import dao.RecipesDAO;

public class GetRecipeLogic {
	// 検索対象のIDや文字列を保持するフィールド
	private String string;
	
	// コンストラクタ: 検索対象のIDや文字列を受け取り、フィールドに設定する
	public GetRecipeLogic(String string) {
		this.string = string;
	}
	
	// 指定されたIDや文字列に基づいてRecipeオブジェクトを取得するメソッド
	public List<RecipeMain> execute() throws SQLException {
		// DAOを使用してデータベースから指定された項目を取得
		RecipesDAO dao = new RecipesDAO();
		List<RecipeMain> recipeList = dao.getOne(this.string); // 取得したRecipeオブジェクトのリストを保持
		
		// 取得したRecipeオブジェクトのリストを返す
		return recipeList;
	}
}
