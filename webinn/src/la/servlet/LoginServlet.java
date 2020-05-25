package la.servlet;

import java.io.IOException;

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



@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public LoginServlet() {
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


			//アクションの取得
			String action = request.getParameter("action");


			//アクションの指定なし
			if (action == null || action.length() == 0) {
				//ログイン状態を確認する
				String isLogin = (String)session.getAttribute("isLogin");
				//ログインしてれば会員メニューへ遷移する
				if(isLogin != null && isLogin.equals("true") ){
					MemberBean bean = (MemberBean)session.getAttribute("user");
					request.setAttribute("message",bean.getName() + "さんはすでにログインしています");
					gotoPage(request,response,"/memberMenu.jsp");
				//ログアウトしていればログイン画面へ遷移する
				}else {
					gotoPage(request,response,"/login.jsp");
				}


			//ログインアクション
			}else if(action.equals("login")) {
				//ログインならメールアドレスとパスワードを取得
				String mailAddress = request.getParameter("mailAddress");
				String passWord = request.getParameter("passWord");
				//ユーザー名とパスワードが未入力ならエラー表示
				if(mailAddress == null || mailAddress.length()==0
					|| passWord == null || passWord.length() == 0) {
					request.setAttribute("message","メールアドレスかパスワード、またはその両方が入力されていません");
					gotoPage(request,response,"/errInput.jsp");
					return;
				//入力されたメールアドレスを持つ会員がいるか確認する
				}else if(mdao.findByEmail(mailAddress) != null) {
					//ユーザー名とパスワードが一致
					if(mdao.checkPass(mailAddress,passWord)) {
						//ログインステータスをログインにし、sessionScopeに会員情報を格納する
						session.setAttribute("isLogin", "true");
						session.setAttribute("member", mdao.findByEmail(mailAddress));
						//管理者なら管理者メニューへ遷移する
						if(mdao.findByEmail(mailAddress).getId() < 10) {
							gotoPage(request,response,"/adminMenu.jsp");
						//会員なら会員メニューへ遷移する
						}else if(mdao.findByEmail(mailAddress).getId() >= 10) {
							gotoPage(request,response,"/memberMenu.jsp");
						}
					}else {
						//ユーザー名とパスワードが不一致の場合エラー表示
						request.setAttribute("message","メールアドレスかパスワードが違います");
						gotoPage(request,response,"/errInput.jsp");
						return;
					}
				//入力されたメールアドレスを持つ会員が見つからない場合はエラー表示
				}else {
					request.setAttribute("message","入力されたメールアドレスを持つ会員が見つかりません");
					gotoPage(request,response,"/errInput.jsp");
					return;
				}


			//ログアウトアクション
			}else {
				//HttpSession session = request.getSession(false);
				if(session != null) {
					session.invalidate();
					gotoPage(request,response,"/login.jsp");
				}else {
					request.setAttribute("message","すでにログアウトしています");
					gotoPage(request,response,"/errInput.jsp");
				}
			}


		}catch (DAOException e) {
			e.printStackTrace();
		}


	}

	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

}
