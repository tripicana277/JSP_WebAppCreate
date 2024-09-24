package model;

import java.sql.SQLException;

import dao.RecipesDAO;

public class DeleteRecipeLogic {
	// 削除対象のIDや文字列を保持するフィールド
	private String string;

	// コンストラクタ: 削除対象のIDや文字列を受け取り、フィールドに設定する
	public DeleteRecipeLogic(String string) {
		this.string = string;
	}

	// 削除処理を実行するメソッド
	public void execute() throws SQLException {
		// DAOを使用してデータベースから指定された項目を削除
		RecipesDAO dao = new RecipesDAO();
		dao.deleteOne(this.string);
	}
}
