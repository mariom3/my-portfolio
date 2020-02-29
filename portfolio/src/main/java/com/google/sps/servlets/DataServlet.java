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
    comments.add(new ArrayList<String>());
    comments.add(new ArrayList<String>());
    comments.add(new ArrayList<String>());
    comments.get(0).add("Mario");
    comments.get(0).add("Hi!");
    comments.get(1).add("Mario's imaginary friend");
    comments.get(1).add("Hi! What are you going?");
    comments.get(2).add("Mario");
    comments.get(2).add("Just learning how to use JSON");
  } 

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertToJson(comments);
    System.out.println("Sending string: " + json);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    comments.add(new ArrayList<String>());
    comments.get(comments.size()-1).add(request.getParameter("display-name"));
    comments.get(comments.size()-1).add(request.getParameter("comment"));
    response.sendRedirect("/aboutme.html");
  }

  public String convertToJson(List<List<String>> comments) {
    String json = "{ \"comments\": [";
    for(List<String> comment : comments){
      json += "{\"userName\": \"" + comment.get(0)
        + "\", \"comment\": \"" + comment.get(1) + "\"},";
    }
    json = json.substring(0, json.length() - 1);
    json += "]}";
    return json;
  }

}