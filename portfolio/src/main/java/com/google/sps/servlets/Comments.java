// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class Comments extends HttpServlet {
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  private static final String REDIRECT_URL_AFTER_LOGIN = "/aboutme.html";
  private static final String REDIRECT_URL_AFTER_LOGOUT = "/";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertCommentsToJson();
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // TODO: Use nickname instead
    UserService userService = UserServiceFactory.getUserService();
    saveComment(userService.getCurrentUser().getEmail(), request.getParameter("comment"));
    response.sendRedirect("/aboutme.html");
  }

  private String convertCommentsToJson() {
    Query query = new Query("Comment");
    PreparedQuery results = datastore.prepare(query);

    String json = "{ \"comments\": [";
    for (Entity comment : results.asIterable()) {
      json += "{\"userName\": \"" + comment.getProperty("userName") + "\", \"comment\": \""
          + comment.getProperty("comment") + "\"},";
    }
    json = json.substring(0, json.length() - 1);
    json += "]}";
    return json;
  }

  private void saveComment(String userName, String comment) {
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("userName", userName);
    commentEntity.setProperty("comment", comment);
    datastore.put(commentEntity);
  }
}