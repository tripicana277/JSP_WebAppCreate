package model;

import java.util.List;

// Recipeクラスは、投稿内容を保持するためのモデルクラスです。
// このクラスはSerializableインターフェースを実装しており、セッションに保存されたり、外部に保存されたりする際に使用されます。
public class RecipeMain {

	private String recipeName; // レシピ名
	private String fileName; // レシピ画像ファイル名
	private String comment; // コメント
	private String number; // 人数
	private List<RecipeSubMaterial> recipeSubMaterials; // RecipeSubオブジェクトのリスト
	private List<RecipeSubHowToMake> recipeSubHowToMakes; // RecipeSub2オブジェクトのリスト

	// インナークラスRecipeSubは、材料とその数量を保持します。
	public class RecipeSubMaterial {
		private String material; // 材料名
		private String quantity; // 数量

		// デフォルトコンストラクタ
		public RecipeSubMaterial() {
		}

		// materialフィールドのgetterメソッド
		public String getMaterial() {
			return material;
		}

		// materialフィールドのsetterメソッド
		public void setMaterial(String material) {
			this.material = material;
		}

		// quantityフィールドのgetterメソッド
		public String getQuantity() {
			return quantity;
		}

		// quantityフィールドのsetterメソッド
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
	}

	// インナークラスRecipeSub2は、作り方とその画像ファイルを保持します。
	public class RecipeSubHowToMake {
		private String fileName2; // 作り方画像ファイル名
		private String howToMake; // 作り方の詳細

		// fileName2フィールドのgetterメソッド
		public String getFileName2() {
			return fileName2;
		}

		// fileName2フィールドのsetterメソッド
		public void setFileName2(String fileName2) {
			this.fileName2 = fileName2;
		}

		// howToMakeフィールドのgetterメソッド
		public String getHowToMake() {
			return howToMake;
		}

		// howToMakeフィールドのsetterメソッド
		public void setHowToMake(String howToMake) {
			this.howToMake = howToMake;
		}
	}

	// デフォルトコンストラクタ
	public RecipeMain() {
	}

	// レシピ名とファイル名を初期化するコンストラクタ
	public RecipeMain(String recipeName, String fileName) {
		this.setRecipeName(recipeName);
		this.setFileName(fileName);
	}

	// レシピ名、ファイル名、コメント、人数を初期化するコンストラクタ
	public RecipeMain(String recipeName, String fileName, String comment, String number) {
		this.setRecipeName(recipeName);
		this.setFileName(fileName);
		this.setComment(comment);
		this.setNumber(number);
	}

	// 全てのフィールドを初期化するコンストラクタ
	public RecipeMain(String recipeName, String fileName, String comment,
			String number,
			List<RecipeSubMaterial> recipeSubMaterials,
			List<RecipeSubHowToMake> recipeSubs2) {
		this.setRecipeName(recipeName);
		this.setFileName(fileName);
		this.setComment(comment);
		this.setNumber(number);
		this.setRecipeSubMaterials(recipeSubMaterials);
		this.setRecipeSubHowToMakes(recipeSubs2);
	}

	// recipeNameフィールドのgetterメソッド
	public String getRecipeName() {
		return recipeName;
	}

	// recipeNameフィールドのsetterメソッド
	public void setRecipeName(String name1) {
		this.recipeName = name1;
	}

	// fileNameフィールドのgetterメソッド
	public String getFileName() {
		return fileName;
	}

	// fileNameフィールドのsetterメソッド
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// commentフィールドのgetterメソッド
	public String getComment() {
		return comment;
	}

	// commentフィールドのsetterメソッド
	public void setComment(String name2) {
		this.comment = name2;
	}

	// recipeSubsフィールドのgetterメソッド
	public List<RecipeSubMaterial> getRecipeSubs() {
		return recipeSubMaterials;
	}

	// recipeSubsフィールドのsetterメソッド
	public void setRecipeSubMaterials(List<RecipeSubMaterial> recipeSubMaterials) {
		this.recipeSubMaterials = recipeSubMaterials;
	}

	// recipeSubs2フィールドのgetterメソッド
	public List<RecipeSubHowToMake> getRecipeSubHowToMakes() {
		return recipeSubHowToMakes;
	}

	// recipeSubs2フィールドのsetterメソッド
	public void setRecipeSubHowToMakes(List<RecipeSubHowToMake> recipeSubs2) {
		this.recipeSubHowToMakes = recipeSubs2;
	}

	// numberフィールドのgetterメソッド
	public String getNumber() {
		return number;
	}

	// numberフィールドのsetterメソッド
	public void setNumber(String number) {
		this.number = number;
	}
}
