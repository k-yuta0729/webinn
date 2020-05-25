package la.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.MemberBean;
import la.dao.DAOException;
import la.dao.MemberDAO;


@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public MemberServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		try {
			//リクエストパラメータの文字コードを設定
			request.setCharacterEncoding("UTF-8");

			//セッションの取得
			HttpSession session = request.getSession(false);
			if(session == null) {
				session = request.getSession();
			}

			//MemberDAOの取得
			MemberDAO mdao = new MemberDAO();

			//Memberリスト型を定義
			List<MemberBean> members = new ArrayList<MemberBean>();

			//管理者ならセッションスコープにMemberリストを格納(操作前)
			if(getMyId(session) < 10) {
				members = mdao.findAll();
				session.setAttribute("members",members);
			}

			//アクションの取得
			String action = request.getParameter("action");


			//アクションパラメータなしのとき
			if(action == null || action.length() == 0 ) {
				//管理者ならセッションスコープにMemberリストを格納し管理者メニューへ遷移する
				if(getMyId(session) < 10) {
					members = mdao.findAll();
					session.setAttribute("members",members);
					gotoPage(request,response,"/adminMenu.jsp");
				//会員なら会員メニューに遷移する
				}else if(getMyId(session) >=10 ) {
					gotoPage(request,response,"/memberMenu.jsp");
				//セッションからidを取得できなければログイン画面に遷移する
				}else {
					gotoPage(request,response,"/login.jsp");
				}




			//会員仮登録
			}else if(action.equals("registration")) {
				//会員情報パラメータを取得
				String name = request.getParameter("name");
				String postal = request.getParameter("postal");
				String address = request.getParameter("address");
				String tel = request.getParameter("tel");
				String mailAddress = request.getParameter("email");
				String strYear = request.getParameter("year");
				String strMonth = request.getParameter("month");
				String strDay = request.getParameter("day");
				String strBirthday = strYear + "-" + strMonth + "-" + strDay;
				Date birthday = Date.valueOf(strBirthday);
				//Date birthday = Integer.parseInt(strBirthday);
				String passWord = request.getParameter("passWord");

				//入力に欠損があればエラー表示する
				if(name == null || name.length() == 0 || postal == null || postal.length() == 0
					|| address == null || address.length() == 0 || tel == null || tel.length() == 0
					|| mailAddress == null || mailAddress.length() == 0
					|| passWord == null || passWord.length() == 0) {
					request.setAttribute("message","すべての項目を入力してください");
					gotoPage(request,response,"/errInput.jsp");
				}else {
					//seesionスコープに会員情報を格納する
					MemberBean member = new MemberBean(mdao.getMaxId()+1,name, postal, address,
							tel, mailAddress,  birthday,  passWord);
					session.setAttribute("member", member);
					//setBirthdayToSession(session,strYear,strMonth,strDay);
					//
					gotoPage(request,response,"/checkMemberRegistration.jsp");
				}



			//会員本登録
			}else if(action.equals("registration2")) {
					//セッションスコープの会員情報をデータベースに追加
					mdao.addMember((MemberBean)session.getAttribute("member"));
					gotoPage(request,response,"/completeMemberRegistration.jsp");



			////会員情報仮変更
			}else if(action.equals("change")) {

				//管理者が会員情報を変更する
				if(getMyId(session) < 10) {
					//削除する会員の会員idがパラメータに含まれていることを確認する
					String strId = request.getParameter("id");
					if(strId == null || strId.length() == 0 ) {
						request.setAttribute("message","情報を変更する会員の会員idが特定できませんでした");
						gotoPage(request,response,"/errInput.jsp");
					//会員idと変更するパラメータを取得
					}else{
						int id = Integer.parseInt(strId);
						if(id > 0) {
							//変更前後の会員情報を保持するMemberBeanを用意する
							MemberBean oldMember = mdao.findById(id);
							MemberBean newMember = null;
							String name = request.getParameter("name");
							String postal = request.getParameter("postal");
							String address = request.getParameter("address");
							String tel = request.getParameter("tel");
							String mailAddress = request.getParameter("email");
							String strYear = request.getParameter("year");
							String strMonth = request.getParameter("month");
							String strDay = request.getParameter("day");
							String strBirthday = strYear + "-" + strMonth + "-" + strDay;
							Date birthday = Date.valueOf(strBirthday);
							String passWord = request.getParameter("passWord");
							//変更する項目が1つもなければエラー表示する
							if(name == null && postal == null && address == null && tel == null
							&& mailAddress == null && passWord == null
							&& birthday.toString() == oldMember.getBirthday().toString()) {
								request.setAttribute("message","変更する項目が入力されていません");
								gotoPage(request,response,"/errInput.jsp");
							//変更する項目があれば一時的にセッションスコープに変更後の会員情報を格納する
							}else {
								newMember = MemberBean.updateMember(oldMember,name,postal,address,tel,mailAddress,birthday,passWord);
								//seesionスコープに新旧の会員情報を格納する
								session.setAttribute("newMember", newMember );
								session.setAttribute("oldMember", oldMember );
								//会員情報変更確認画面へ遷移する
								gotoPage(request,response,"/checkChangeMemberInformation2.jsp");
							}
						}
					}

				//会員が自身で会員情報を変更する
				}else if(getMyId(session) >= 10){
					//変更前後の会員情報を保持するMemberBeanを用意する
					MemberBean oldMember = (MemberBean)session.getAttribute("member");
					MemberBean newMember = null;
					//Integer id = Integer.parseInt(request.getParameter("id"));
					int id = getMyId(session);
					String name = request.getParameter("name");
					String postal = request.getParameter("postal");
					String address = request.getParameter("address");
					String tel = request.getParameter("tel");
					String mailAddress = request.getParameter("email");
					String strYear = request.getParameter("year");
					String strMonth = request.getParameter("month");
					String strDay = request.getParameter("day");
					String strBirthday = strYear + "-" + strMonth + "-" + strDay;
					Date birthday = Date.valueOf(strBirthday);
					String passWord = request.getParameter("passWord");
					//変更する項目が1つもなければエラー表示する
					if(name == null && postal == null && address == null && tel == null
					&& mailAddress == null && passWord == null
					&& birthday.toString() == oldMember.getBirthday().toString()) {
						request.setAttribute("message","変更する項目が入力されていません");
						gotoPage(request,response,"/errInput.jsp");
					//変更する項目があれば一時的にセッションスコープに変更後の会員情報を格納する
					}else {
						//newMember = mdao.changeMemberInformation(id,name,postal,address,tel,mailAddress,birthday,passWord);
						newMember = MemberBean.updateMember(oldMember,name,postal,address,tel,mailAddress,birthday,passWord);
						//seesionスコープに新旧の会員情報を格納する
						session.setAttribute("newMember", newMember );
						session.setAttribute("oldMember", oldMember );
						//会員情報変更確認画面へ遷移する
						gotoPage(request,response,"/checkChangeMemberInformation.jsp");
					}
				//会員IDが見つからなければエラー表示する
				}else if(getMyId(session) < 0) {
					request.setAttribute("message","あなたの会員Idが見つかりません");
					gotoPage(request,response,"/errInput.jsp");
				}


			//会員情報本変更
			}else if(action.equals("change2")) {
				//管理者が会員情報を本変更する
				if(getMyId(session) < 10) {
					//セッションスコープの会員情報をもとに会員情報を変更し、memberesリストを更新する
					mdao.changeMemberInformation((MemberBean)session.getAttribute("newMember"));
					members = mdao.findAll();
					session.setAttribute("members",members);
					gotoPage(request,response,"/completeChangeMemberInformation2.jsp");
				//会員が自身で会員情報を本変更する
				}else if(getMyId(session) >= 10){
					//セッションスコープの会員情報をもとに会員情報を変更し、変更後の会員情報をセッションスコープに格納する
					mdao.changeMemberInformation((MemberBean)session.getAttribute("newMember"));
					MemberBean member = (MemberBean)session.getAttribute("newMember");
					session.setAttribute("member", member);
					gotoPage(request,response,"/completeChangeMemberInformation.jsp");
				//会員IDが見つからなければエラー表示する
				}else if(getMyId(session) < 0) {
					request.setAttribute("message","あなたの会員Idが見つかりません");
					gotoPage(request,response,"/errInput.jsp");
				}



			//会員退会
			}else if(action.equals("delete")) {
				//管理者が会員を退会させる
				if(getMyId(session) < 10) {
					//リクエストコープから会員idを取得し会員退会
					String strId = request.getParameter("id");
					int id = Integer.parseInt(strId);
					if( strId == null|| strId.length() == 0) {
						request.setAttribute("message","会員idが特定できませんでした");
						gotoPage(request,response,"/errInput.jsp");
					}
					mdao.deleteById(id);
					members = mdao.findAll();
					session.setAttribute("members",members);
					//管理者用の会員情報変更確認画面へ遷移する
					gotoPage(request,response,"/completeDeleteMemberInformation2.jsp");
				//会員が自身で退会する
				}else if(getMyId(session) >= 10){
					//自身のidをセッションスコープから取り出し会員削除,セッションスコープの削除
					mdao.deleteById(getMyId(session));
					//session.invalidate();
					//会員情報変更確認画面へ遷移する
					gotoPage(request,response,"/completeDeleteMemberInformation.jsp");
				}else if(getMyId(session) < 0) {
					request.setAttribute("message","あなたの会員Idが見つかりません");
					gotoPage(request,response,"/errInput.jsp");
				}

			//会員検索
			}else if(action.equals("search")) {
				//管理者であることを確認する
				if(getMyId(session) < 10) {
					String name = request.getParameter("name");
					if(name != null && name.length() != 0) {
						members = mdao.findByName(name);
					}else{
						members = mdao.findAll();
					}
					session.setAttribute("members",members);
					//会員一覧検索画面へ遷移する
					gotoPage(request,response,"/searchMember.jsp");
				}else {
					request.setAttribute("message","あなたには会員を検索する権限がありません");
					gotoPage(request,response,"/errInput.jsp");
				}
			}

		}catch(DAOException e){
				e.printStackTrace();
		}

	}



	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	//セッションスコープから自身の会員idを取得するメソッド
	private int getMyId(HttpSession session) {
		MemberBean bean = (MemberBean)session.getAttribute("member");
		if( bean != null ) {
			return bean.getId();
		}else {
			return -1;
		}
	}


	private void setBirthdayToSession(HttpSession session,String year, String month,String day) {
		String birthday[] = {year,month,day};
		session.setAttribute("birthday",birthday);
	}

}