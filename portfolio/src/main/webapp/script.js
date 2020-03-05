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

function getComments() {
  fetch('/data').then(response => response.json()).then((json) => {
    const commentsElement = document.getElementById('comments');
    commentsElement.innerText = '';
    const comments = json.comments;
    console.log('Received: ' + comments);
    for (let i = 0; i < comments.length; i++) {
      commentsElement.appendChild(
          createListElement(comments[i].userName + ': ' + comments[i].comment));
    }
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
