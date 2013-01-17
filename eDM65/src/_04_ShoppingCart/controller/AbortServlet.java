package _04_ShoppingCart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _04_ShoppingCart.model.ShoppingCart;
// 當進行『結帳』時，如果按下『放棄購物』超連結，瀏覽器會要求此程式
public class AbortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
		if (cart != null) {
			//由session物件中移除ShoppingCart物件
			session.removeAttribute("ShoppingCart");
			//由session物件中移除已登入記號『LoginOK』			
			//session.removeAttribute("LoginOK");
		}
		//response.sendRedirect("../index.jsp");
		response.sendRedirect(response.encodeRedirectURL ("../index.jsp"));
		return;
	}
}
