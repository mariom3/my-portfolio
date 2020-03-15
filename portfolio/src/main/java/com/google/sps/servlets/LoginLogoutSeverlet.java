/*
  Mario Morales
  6 March 2020
*/
package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login-logout")
/** Servelet for serving login/logout URLs and other user data. */
public class LoginLogoutSeverlet extends HttpServlet {
  private static final String REDIRECT_URL_AFTER_LOGIN = "/aboutme.html";
  private static final String REDIRECT_URL_AFTER_LOGOUT = "/index.html";

  UserService userService = UserServiceFactory.getUserService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = getUserInfoAsJson();
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String getUserInfoAsJson() {
    int loggedIn = userService.isUserLoggedIn() ? 1 : 0;
    String json = "{ \"loggedIn\": " + loggedIn;

    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      json += ", \"userEmail\": \"" + userEmail + "\"";
      String logoutUrl = userService.createLogoutURL(REDIRECT_URL_AFTER_LOGOUT);
      json += ", \"logoutUrl\": \"" + logoutUrl + "\"";
    } else { 
      String loginUrl = userService.createLoginURL(REDIRECT_URL_AFTER_LOGIN);
      json += ", \"loginUrl\": \"" + loginUrl + "\"";
    }
    json += "}";
    return json;
  }

}