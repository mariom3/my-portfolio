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
    
    if (loggedIn)
      createCommentForm(json.logoutUrl, json.userEmail);
    else
      createLoginButton(json.loginUrl);
  });
}

function createCommentForm(logoutUrl, userEmail) {

  const addCommentElement = document.getElementById('login-logout');

  const pElement = document.createElement('p');
  pElement.textContent = "Logged in as: " + userEmail;

  const addCommentForm = document.createElement('form');
  addCommentForm.setAttribute('action', '/data');
  addCommentForm.setAttribute('method', 'POST');

  const commentTextArea = document.createElement('textarea');
  commentTextArea.setAttribute('id', 'comment');
  commentTextArea.setAttribute('name', 'comment');
  commentTextArea.setAttribute('rows', '5');
  commentTextArea.setAttribute('cols', '37');
  commentTextArea.innerText = 'Add a comment...';

  const logoutButton = document.createElement('button');
  logoutButton.innerText = 'LOGOUT';
  logoutButton.setAttribute('class', 'btn btn-outline-dark custom-btn title-font');

  const logoutButtonAnchor = document.createElement('a');
  logoutButtonAnchor.setAttribute('href', logoutUrl);
  logoutButtonAnchor.appendChild(logoutButton);

  const logoutButtonContainer = document.createElement('div');
  logoutButtonContainer.innerText = '';
  logoutButtonContainer.appendChild(logoutButtonAnchor);
  logoutButtonContainer.setAttribute('style', 'padding-right: .3em; display: inline;');

  const submitButton = document.createElement('input');
  submitButton.setAttribute('class', 'btn btn-outline-dark custom-btn title-font');
  submitButton.setAttribute('type', 'submit');
  submitButton.setAttribute('value', 'SUBMIT');

  const submitButtonContainer = document.createElement('div');
  submitButtonContainer.innerText = '';
  submitButtonContainer.appendChild(submitButton);
  submitButtonContainer.setAttribute('style', 'padding-right: .3em; display: inline;');

  addCommentForm.appendChild(commentTextArea);
  addCommentForm.appendChild(document.createElement('br'));
  addCommentForm.appendChild(logoutButtonContainer);
  addCommentForm.appendChild(submitButtonContainer);
  addCommentElement.appendChild(pElement);
  addCommentElement.appendChild(addCommentForm);
}

function createLoginButton(loginUrl) {
  const loginButton = document.createElement('button');
  loginButton.innerText = 'LOGIN TO ADD A COMMENT';
  loginButton.setAttribute('class', 'btn btn-outline-dark custom-btn title-font');
  
  const loginButtonAnchor = document.createElement('a');
  loginButtonAnchor.setAttribute('href', loginUrl);
  loginButtonAnchor.appendChild(loginButton);

  const addCommentElement = document.getElementById('login-logout');
  addCommentElement.appendChild(loginButtonAnchor);
}
