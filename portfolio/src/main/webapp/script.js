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


// TODO: Add logout button
function getComments() {
  fetch('/data').then(response => response.json()).then((json) => {
    const commentsElement = document.getElementById('comments');
    commentsElement.innerText = '';
    const loggedInStatus = json.loggedInStatus;
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