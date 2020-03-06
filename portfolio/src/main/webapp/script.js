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


// TODO: Separate into 'getComments' and 'getLoggedInStatus'
function getComments() {
  fetch('/data').then(response => response.json()).then((json) => {
    const commentsElement = document.getElementById('comments');
    commentsElement.innerText = '';
    const comments = json.comments;
    for (let i = 0; i < comments.length; i++) {
      commentsElement.appendChild(
          createCommentElement(comments[i].userName, comments[i].comment));
    }
  });
}

function createCommentElement(userName, comment) {
  const pElement = document.createElement('p');
  pElement.textContent = comment;
  pElement.setAttribute('class', 'comment');
  const divElement = document.createElement('div');
  divElement.innerText = userName + ': ';
  divElement.appendChild(pElement);
  return divElement;
}

function getLoggedInStatus() {
  fetch('/data').then(response => response.json()).then((json) => {
    const loggedIn = json.loggedInStatus;

    if (loggedIn){
      const pElement = document.getElementById('display-user');
      pElement.style.display = "block";
      pElement.textContent = "Logged in as: " + json.userEmail;
      document.getElementById('logout-link').href = json.logoutUrl;
      document.getElementById('comment-form').style.display = "block";
    }else
      createLoginButton(json.loginUrl);
  });
}

function createLoginButton(loginUrl) {
  document.getElementById('display-user').style.display = "none";
  document.getElementById('comment-form').style.display = "none";

  const loginButton = document.createElement('button');
  loginButton.innerText = 'LOGIN TO ADD A COMMENT';
  loginButton.setAttribute('class', 'btn btn-outline-dark custom-btn title-font');

  const loginButtonAnchor = document.createElement('a');
  loginButtonAnchor.setAttribute('href', loginUrl);
  loginButtonAnchor.appendChild(loginButton);

  const addCommentElement = document.getElementById('login-logout');
  addCommentElement.appendChild(loginButtonAnchor);
}
