package com.cloud.akshay;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginAction
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.akshay.VerifyRecaptcha;

/**
 * @author Akshay
 */
@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginAction" }, initParams = {
		@WebInitParam(name = "user", value = "akshay"),
		@WebInitParam(name = "password", value = "123") })
public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = -6506682026701304964L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		// get reCAPTCHA request param
		String gRecaptchaResponse = request
				.getParameter("g-recaptcha-response");
		System.out.println(gRecaptchaResponse);
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		
		//to be removed
		//boolean verify=true;

		// get servlet config init params
		String userID = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		// logging example
		System.out.println("User=" + user + "::password=" + pwd + "::Captcha Verify"+verify);

		if (userID.equals(user) && password.equals(pwd) && verify) {
			
			response.sendRedirect("Graphs.html");
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/LoginPage.html");
			PrintWriter out = response.getWriter();
			if (verify) {
				out.println("<font color=red>Either user name or password is wrong.</font>");
			} else {
				out.println("<font color=red>You missed the Captcha.</font>");
			}

			rd.include(request, response);

		}

	}

}
