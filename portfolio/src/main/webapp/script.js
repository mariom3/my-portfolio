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
    console.log('Received: ' + comments + ", status: " + loggedInStatus);
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
    const loginButton = document.getElementById('login-logout');
    const loggedIn = json.loggedInStatus;
    if (loggedIn) {
      // add 'logged in as __, [logout]'
      // add form
      createCommentForm();
    }else {
      // login to comment
      const loginButton = document.createElement('button');
      loginButton.innerText = 'LOGIN TO ADD A COMMENT';
      loginButton.setAttribute('class', 'btn btn-outline-dark custom-btn title-font');
      addCommentElement.appendChild(loginButton);
    }
  });
}

function createCommentForm() {
  const email = "mmorales@sps-program.com";

  const addCommentElement = document.getElementById('login-logout');
  addCommentElement.innerText = '';

  const pElement = document.createElement('p');
  pElement.textContent = "Logged in as: " + email;

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

  const logoutButtonContainer = document.createElement('div');
  logoutButtonContainer.innerText = '';
  logoutButtonContainer.appendChild(logoutButton);
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
