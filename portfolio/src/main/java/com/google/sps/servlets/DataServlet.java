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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  // IMPROVE: redundently storing display names
  private List<List<String>> comments = new ArrayList<List<String>>();

  @Override
  public void init() {
    comments.add(buildComment("Mario", "Hi!"));
    comments.add(buildComment("Mario's imaginary friend", "Hi! What are you going?"));
    comments.add(buildComment("Mario", "Just learning how to use JSON"));
  } 

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJson(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    comments.add(buildComment(request.getParameter("display-name"), request.getParameter("comment")));
    response.sendRedirect("/aboutme.html");
  }

  private String convertToJson(List<List<String>> comments) {
    String json = "{ \"comments\": [";
    for(List<String> comment : comments){
      json += "{\"userName\": \"" + comment.get(0)
        + "\", \"comment\": \"" + comment.get(1) + "\"},";
    }
    json = json.substring(0, json.length() - 1);
    json += "]}";
    return json;
  }

  private List<String> buildComment(String userName, String comment) {
      // Every comment structure contains the display name of the user that posted the comment
      List<String> commentStruct = new ArrayList<String>();
      commentStruct.add(userName);
      commentStruct.add(comment);
      return commentStruct;
  }

}