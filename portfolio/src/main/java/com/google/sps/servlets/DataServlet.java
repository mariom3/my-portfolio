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
import java.util.HashMap;
import java.util.Map.Entry;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private HashMap<String, String> comments;

    @Override
    public void init() {
        comments = new HashMap<String, String>();
        comments.put("Mario", "Hi!");
        comments.put("Mario's imaginary friend", "Hi! What are you going?");
        comments.put("Mario", "Just learning how to use JSON");
        comments.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());  
        });
    } 

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = convertToJson(comments);
        System.out.println("Sending string: " + json);
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    public String convertToJson(HashMap<String, String> comments) {
        String json = "{ \"comments\": [";
        for(Entry<String, String> entry : comments.entrySet()){
            json += "{\"userName\": \"" + entry.getKey()
                + "\", \"comment\": \"" + entry.getValue() + "\"},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]}";
        return json;
    }
}