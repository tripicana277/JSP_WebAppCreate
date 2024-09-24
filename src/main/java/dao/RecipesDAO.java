package dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.RecipeMain;
import model.RecipeMain.RecipeSubHowToMake;
import model.RecipeMain.RecipeSubMaterial;

public class RecipesDAO {
	// データベース接続情報が格納されたプロパティファイルの名前
	private static final String DB_PROPERTIES_FILE = "db.properties";

	// レシピ名に基づいてレシピのレコードを取得するSQL文
	private static final String SELECT_RECIPEMAIN = "SELECT * FROM RECIPEMAIN";
	private static final String SELECT_RECIPEMAIN_BY_NAME = "SELECT * FROM RECIPEMAIN WHERE RECIPENAME = ?";
	private static final String SELECT_MATERIAL_BY_NAME = "SELECT * FROM MATERIAL WHERE RECIPENAME = ?";
	private static final String SELECT_HOWTOMAKE_BY_NAME = "SELECT * FROM HOWTOMAKE WHERE RECIPENAME = ?";

	// 新しいレシピをレコードを追加するためのSQL文
	private static final String INSERT_RECIPEMAIN = "INSERT INTO RECIPEMAIN (RECIPENAME, FILENAME, COMMENT, NUMBER) VALUES (?, ?, ?, ?)";
	private static final String INSERT_MATERIAL = "INSERT INTO MATERIAL (RECIPENAME, MATERIAL, QUANTITY) VALUES (?, ?, ?)";
	private static final String INSERT_HOWTOMAKE = "INSERT INTO HOWTOMAKE (RECIPENAME, FILENAME2, HOWTOMAKE) VALUES (?, ?, ?)";

	// 指定したレシピ名に基づいてレシピのレコードを削除するSQL文
	private static final String DELETE_RECIPEMAIN_BY_NAME = "DELETE FROM RECIPEMAIN WHERE RECIPENAME = ?";

	// データベース接続用のプロパティを格納するオブジェクト
	private Properties dbProperties;

	// JDBCドライバをロードするための静的初期化ブロック
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}

	/**
	* DAOのコンストラクタ。
	* プロパティファイルを読み込みます
	*/
	public RecipesDAO() {
		this.dbProperties = loadProperties();
	}

	/**
	* 指定されたレシピ名に基づいて、レシピのメイン情報、
	* 材料リスト、作り方リストを取得するメソッド
	*/
	public List<RecipeMain> getOne(String name) throws SQLException {
		List<RecipeMain> recipeList = new ArrayList<>();
		try (Connection conn = getConnection()) {
			// レシピのメイン情報を取得
			RecipeMain recipeMain = fetchRecipe(conn, name);
			if (recipeMain != null) {
				// 材料リストと作り方リストを設定
				recipeMain.setRecipeSubMaterials(fetchMaterials(conn, name));
				recipeMain.setRecipeSubHowToMakes(fetchHowToMakes(conn, name));
				recipeList.add(recipeMain);
			}
		}
		return recipeList;
	}

	/**
	* 全てのレシピを取得するメソッド
	*/
	public List<RecipeMain> getAll() throws SQLException {
		String sql = SELECT_RECIPEMAIN;
		return executeQuery(sql);
	}

	/**
	* 新しいレシピをデータベースに追加し、
	* そのレシピ情報を返すメソッド
	*/
	public List<RecipeMain> setOne(RecipeMain recipeMain) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement pStmt = conn.prepareStatement(INSERT_RECIPEMAIN)) {
			// レシピメイン情報をデータベースに挿入
			pStmt.setString(1, recipeMain.getRecipeName());
			pStmt.setString(2, recipeMain.getFileName());
			pStmt.setString(3, recipeMain.getComment());
			pStmt.setString(4, recipeMain.getNumber());
			pStmt.executeUpdate();

			// 材料と作り方をデータベースに挿入
			insertMaterials(conn, recipeMain);
			insertHowToMakes(conn, recipeMain);
		}
		return getOne(recipeMain.getRecipeName());
	}

	/**
	* 指定されたレシピ名に基づいて、
	* レシピのメイン情報をデータベースから削除するメソッド
	*/
	public void deleteOne(String name) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement pStmt = conn.prepareStatement(DELETE_RECIPEMAIN_BY_NAME)) {
			pStmt.setString(1, name);
			pStmt.executeUpdate();
		}
	}

	/**
	* 指定されたレシピ名に基づいて、
	* レシピのメイン情報を取得するメソッド
	*/
	private RecipeMain fetchRecipe(Connection conn, String name) throws SQLException {
		try (PreparedStatement pStmt = conn.prepareStatement(SELECT_RECIPEMAIN_BY_NAME)) {
			pStmt.setString(1, name);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					return new RecipeMain(
							rs.getString("recipeName"),
							rs.getString("filename"),
							rs.getString("comment"),
							rs.getString("number"));
				}
			}
		}
		return null;
	}

	/**
	* 指定されたレシピ名に基づいて、
	* 材料リストを取得するメソッド
	*/
	private List<RecipeSubMaterial> fetchMaterials(Connection conn, String name) throws SQLException {
		List<RecipeSubMaterial> materialList = new ArrayList<>();
		try (PreparedStatement pStmt = conn.prepareStatement(SELECT_MATERIAL_BY_NAME)) {
			pStmt.setString(1, name);
			try (ResultSet rs = pStmt.executeQuery()) {
				while (rs.next()) {
					RecipeSubMaterial sub = new RecipeMain().new RecipeSubMaterial();
					sub.setMaterial(rs.getString("material"));
					sub.setQuantity(rs.getString("quantity"));
					materialList.add(sub);
				}
			}
		}
		return materialList;
	}

	/**
	* 指定されたレシピ名に基づいて、
	* 作り方リストを取得するメソッド
	*/
	private List<RecipeSubHowToMake> fetchHowToMakes(Connection conn, String name) throws SQLException {
		List<RecipeSubHowToMake> howToMakeList = new ArrayList<>();
		try (PreparedStatement pStmt = conn.prepareStatement(SELECT_HOWTOMAKE_BY_NAME)) {
			pStmt.setString(1, name);
			try (ResultSet rs = pStmt.executeQuery()) {
				while (rs.next()) {
					RecipeSubHowToMake sub = new RecipeMain().new RecipeSubHowToMake();
					sub.setFileName2(rs.getString("filename2"));
					sub.setHowToMake(rs.getString("howtomake"));
					howToMakeList.add(sub);
				}
			}
		}
		return howToMakeList;
	}

	/**
	* 材料をデータベースに挿入するメソッド
	*/
	private void insertMaterials(Connection conn, RecipeMain recipeMain) throws SQLException {
		try (PreparedStatement pStmt = conn.prepareStatement(INSERT_MATERIAL)) {
			for (RecipeSubMaterial sub : recipeMain.getRecipeSubs()) {
				pStmt.setString(1, recipeMain.getRecipeName());
				pStmt.setString(2, sub.getMaterial());
				pStmt.setString(3, sub.getQuantity());
				pStmt.addBatch();
			}
			pStmt.executeBatch();
		}
	}

	/**
	* 作り方をデータベースに挿入するメソッド
	*/
	private void insertHowToMakes(Connection conn, RecipeMain recipeMain) throws SQLException {
		try (PreparedStatement pStmt = conn.prepareStatement(INSERT_HOWTOMAKE)) {
			for (RecipeSubHowToMake sub : recipeMain.getRecipeSubHowToMakes()) {
				pStmt.setString(1, recipeMain.getRecipeName());
				pStmt.setString(2, sub.getFileName2());
				pStmt.setString(3, sub.getHowToMake());
				pStmt.addBatch();
			}
			pStmt.executeBatch();
		}
	}

	/**
	* SQLクエリを実行し、レシピのリストを返すメソッド
	*/
	private List<RecipeMain> executeQuery(String sql) throws SQLException {
		List<RecipeMain> recipeList = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery()) {
			while (rs.next()) {
				RecipeMain recipeMain = new RecipeMain(
						rs.getString("recipeName"),
						rs.getString("fileName"),
						rs.getString("comment"),
						rs.getString("number"));
				recipeList.add(recipeMain);
			}
		}
		return recipeList;
	}

	/**
	* データベース接続を確立するメソッド
	*/
	private Connection getConnection() throws SQLException {
		// データベース接続を確立し、接続オブジェクトを返す
		return DriverManager.getConnection(
				dbProperties.getProperty("db.url"),
				dbProperties.getProperty("db.user"),
				dbProperties.getProperty("db.password"));
	}

	/**
	* プロパティファイルを読み込み、
	* プロパティオブジェクトを返すメソッド
	*/
	private Properties loadProperties() {
		// プロパティオブジェクトを作成
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {
			if (input == null) {
				throw new IOException("プロパティファイルが見つかりません: " + DB_PROPERTIES_FILE);
			}
			// プロパティファイルを読み込む
			properties.load(input);
		} catch (IOException e) {
			throw new UncheckedIOException("プロパティファイルの読み込みに失敗しました", e);
		}
		return properties;
	}
}
