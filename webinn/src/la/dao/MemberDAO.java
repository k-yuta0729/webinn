package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import la.bean.MemberBean;

public class MemberDAO {

	private Connection con;


	//コンストラクタ
	public MemberDAO() throws DAOException{
		getConnection();
	}

	//SQLへのemailによるmember取得命令メソッド(番号順)
	public MemberBean findByEmail(String email) throws DAOException{
		if(con == null) {
			getConnection();
		}
		MemberBean bean = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//SQLの設定
			String sql = "SELECT * FROM member WHERE email=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			st.setString(1,email);
			//ResultSetの取得
			rs = st.executeQuery();
			while(rs.next()) {
				bean =  new MemberBean(rs.getInt("member_id"),rs.getString("member_name"),
										rs.getString("postal"),rs.getString("address"),
										rs.getString("tel"),rs.getString("email"),
										rs.getDate("birthday"),rs.getString("password")) ;
			}
			return bean;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");

		}finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}


	//SQLへのidによるmember取得命令メソッド(番号順)
	public MemberBean findById(int id) throws DAOException{
		if(con == null) {
			getConnection();
		}
		MemberBean bean = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//SQLの設定
			String sql = "SELECT * FROM member WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			st.setInt(1,id);
			//ResultSetの取得
			rs = st.executeQuery();
			while(rs.next()) {
				bean =  new MemberBean(rs.getInt("member_id"),rs.getString("member_name"),
										rs.getString("postal"),rs.getString("address"),
										rs.getString("tel"),rs.getString("email"),
										rs.getDate("birthday"),rs.getString("password")) ;
			}
			return bean;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");

		}finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}




	//パスワードをチェックするメソッド
	public boolean checkPass(String email,String pass) throws DAOException{
		if(findByEmail(email) == null) {
			throw new DAOException("入力されたメールアドレスを持つ会員が見つかりません");
		}else {
			if(findByEmail(email).getPassWord().equals(pass)) {
				return true;
			}else {
				return false;
			}
		}
	}


	//SQLへの全member取得命令メソッド(番号順)
	public ArrayList<MemberBean> findAll() throws DAOException{
		if(con == null) {
			getConnection();
		}
		ArrayList<MemberBean> list = new ArrayList<MemberBean>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//SQLの設定
			String sql = "SELECT * FROM member";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//ResultSetの取得
			rs = st.executeQuery();
			while(rs.next()) {
				list.add( new MemberBean(rs.getInt("member_id"),rs.getString("member_name"),
						rs.getString("postal"),rs.getString("address"),
						rs.getString("tel"),rs.getString("email"),
						rs.getDate("birthday"),rs.getString("password")) );
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");

		}finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}


	//SQLへの名前によるmember検索命令メソッド(番号順)
	public ArrayList<MemberBean> findByName(String keyWord) throws DAOException{
		if(con == null) {
			getConnection();
		}
		ArrayList<MemberBean> list = new ArrayList<MemberBean>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//SQLの設定
			String sql = "SELECT * FROM member WHERE member_name LIKE ? ";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			st.setString(1,"%" + keyWord + "%");
			//ResultSetの取得
			rs = st.executeQuery();
			while(rs.next()) {
				list.add( new MemberBean(rs.getInt("member_id"),rs.getString("member_name"),
						rs.getString("postal"),rs.getString("address"),
						rs.getString("tel"),rs.getString("email"),
						rs.getDate("birthday"),rs.getString("password")) );
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");

		}finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}



	//SQLへの会員データ追加命令メソッド
	public int addMember(int id, String name, String postal, String address,
						String tel, String mailAddress, Date birthday, String passWord)
						throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "INSERT INTO member(member_id,member_name,postal,address,tel,email,birthday,password)"
							+ " VALUES(?,?,?,?,?,?,?,?)";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.setString(2,name);
			st.setString(3,postal);
			st.setString(4,address);
			st.setString(5,tel);
			st.setString(6,mailAddress);
			st.setDate(7,birthday);
			st.setString(8,passWord);
			//SQLの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの追加に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}


	//SQLへの会員データ追加命令メソッド(Memberbeanを引数にとる)
	public int addMember(MemberBean member)
						throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "INSERT INTO member(member_id,member_name,postal,address,tel,email,birthday,password)"
							+ " VALUES(?,?,?,?,?,?,?,?)";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			st.setInt(1, member.getId());
			st.setString(2,member.getName());
			st.setString(3,member.getPostal());
			st.setString(4,member.getAddress());
			st.setString(5,member.getTel());
			st.setString(6,member.getMailAddress());
			st.setDate(7,member.getBirthday());
			st.setString(8,member.getPassWord());
			//SQLの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの追加に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}




	//SQLへの会員情報変更命令メソッド
	public MemberBean changeMemberInformation(int id, String name, String postal,
												String address, String tel, String mailAddress,
												Date birthday, String passWord)
													 throws DAOException{
		//null値でないパラメータを更新する
		if(name != null) {
			changeName(id,name);
		}
		if(postal != null) {
			changePostal(id,postal);
		}
		if(address != null) {
			changeAddress(id,address);
		}
		if(tel != null) {
			changeTel(id,tel);
		}
		if(mailAddress != null) {
			changeMailAddress(id,mailAddress);
		}
		if(birthday != null) {
			changeBirthday(id,birthday);
		}
		if(passWord != null) {
			changePassWord(id,passWord);
		}
		//変更後の会員情報をMemberBeanの形で返す
		return  findById(id);
	}


	//SQLへの会員情報変更命令メソッド(Memberbeanを引数にとる)
	public MemberBean changeMemberInformation(MemberBean member)
													 throws DAOException{
		//変更後の会員情報をMemberBeanの形で返す
		return  changeMemberInformation(member.getId(), member.getName(), member.getPostal(),
				member.getAddress(), member.getTel(), member.getMailAddress(),
				member.getBirthday(), member.getPassWord() );
	}



	//SQLへの会員削除命令メソッド
	public int deleteById(int id) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "DELETE FROM member WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setInt(1,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}



	//SQLへの名前変更命令メソッド
	public int changeName(int id,String name) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET member_name= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setString(1, name );
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}



	//SQLへの郵便番号変更命令メソッド
	public int changePostal(int id,String postal) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET postal= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setString(1, postal );
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}



	//SQLへの住所変更命令メソッド
	public int changeAddress(int id,String address) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET address= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setString(1, address );
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}




	//SQLへの電話番号変更命令メソッド
	public int changeTel(int id,String tel) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET tel= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setString(1, tel );
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}




	//SQLへのメールアドレス変更命令メソッド
	public int changeMailAddress(int id,String mailAddress) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET email= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setString(1, mailAddress );
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}



	//SQLへの生年月日変更命令メソッド
	public int changeBirthday(int id,Date birthday) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET birthday= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setDate(1, birthday);
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}




	//SQLへのパスワード変更命令メソッド
	public int changePassWord(int id,String passWord) throws DAOException{
		if(con == null) {
			getConnection();
		}
		PreparedStatement st = null;
		try {
			//SQLの設定
			String sql = "UPDATE member SET password= ? WHERE member_id=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//プレースホルダーの設定
			st.setString(1, passWord);
			st.setInt(2,id);
			//SQlの実行
			int rows = st.executeUpdate();
			return rows;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの変更に失敗しました");

		}finally {
			try {
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}

	public int getMaxId()  throws DAOException{
		if(con == null) {
			getConnection();
		}
		Integer maxId = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//SQLの設定
			String sql = "SELECT max(member_id) FROM member";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//ResultSetの取得
			rs = st.executeQuery();
			while(rs.next()) {
				maxId = rs.getInt("max");
			}
			if(maxId == null) {
				return 0;
			}
			return maxId;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました");

		}finally {
			try {
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			}catch(Exception e) {
				throw new DAOException("リソースの開放に失敗しました");
			}
		}
	}



	//データベースへの接続メソッド
	private void getConnection() throws DAOException{
		try {
			//デバイスドライバーをドライバーマネージャーに登録する
			Class.forName("org.postgresql.Driver");
			//URI,ユーザー名、パスワードを設定
			String uri = "jdbc:postgresql:webinn";
			String user = "postgres";
			String pass = "himitu";
			//データベースへの接続
			con = DriverManager.getConnection(uri,user,pass);
		}catch(Exception e){
			throw new DAOException("データベースへの接続に失敗しました");
		}
	}

	//コネクションリソースの開放メソッド
	private void close() throws SQLException {
		if(con != null) {
			con.close();
			con = null;
		}
	}



}
