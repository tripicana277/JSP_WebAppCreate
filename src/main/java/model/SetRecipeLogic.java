package model;

import java.sql.SQLException;
import java.util.List;

import dao.RecipesDAO;

// SetRecipeLogicクラスは、投稿内容（Recipeオブジェクト）をデータベースに保存するためのビジネスロジッククラスです。
public class SetRecipeLogic {

	// 保存するRecipeオブジェクトを保持するためのフィールド
	private RecipeMain recipeMain;

	// コンストラクタは、保存するRecipeオブジェクトを引数として受け取り、フィールドに設定します。
	public SetRecipeLogic(RecipeMain recipeMain) {
		this.recipeMain = recipeMain;
	}

	// executeメソッドは、Recipeオブジェクトをデータベースに保存するために実行されます。
	public List<RecipeMain> execute() throws SQLException {
		// データベース操作を担当するRecipesDAOオブジェクトを作成します。
		RecipesDAO dao = new RecipesDAO();

		// RecipesDAOのsetOneメソッドを呼び出して、Recipeオブジェクトをデータベースに保存します。
		List<RecipeMain> recipeList = dao.setOne(this.recipeMain);

		// 処理が完了したら、return文でメソッドを終了します。（ここではvoidメソッドなので、returnは省略可能です）
		return recipeList;
	}
}
