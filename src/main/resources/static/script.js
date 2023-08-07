var messageContainer = document.getElementById('message-container');
var messageInput = document.getElementById('message-input');
var sendButton = document.getElementById('send-button');
var websocket;

window.addEventListener('load', function() {
  connectWebSocket();

  sendButton.addEventListener('click', function() {
    var message = messageInput.value;

    if (message.trim() !== '') {
      sendMessage(message);
      addMessageToChat(message, 'right');
      messageInput.value = '';
    }
  });

  messageInput.addEventListener('keydown', function(event) {
    if (event.keyCode === 13 && event.shiftKey) {
      event.preventDefault();
      sendButton.click();
    }
    else if (event.keyCode === 13) {
      messageInput.value += '\n';
   }
  });
});

function connectWebSocket() {
  websocket = new WebSocket('ws://localhost:8080/test-one');

  websocket.onopen = function() {
    console.log('WebSocket连接已打开');
  };

  websocket.onmessage = function(event) {
    var message = event.data;
    addMessageToChat(message, 'left');
  };

  websocket.onclose = function() {
    console.log('WebSocket连接已关闭');
  };

  websocket.onerror = function() {
    console.error('WebSocket连接发生错误');
  };
}
function sendMessage(message) {
  if (websocket.readyState === WebSocket.OPEN) {
    websocket.send(message);
  } else {
    console.error('WebSocket连接尚未建立');
  }
}


function addMessageToChat(message, direction) {
  


  if (direction === 'left') {
    var bubbleContainer = document.createElement('div');
  bubbleContainer.className = 'bubble-container-left';

  var bubble = document.createElement('div');
  bubble.className = 'bubble ' + direction;
  bubble.textContent = message;
    var avatarContainer = document.createElement('div');
    avatarContainer.className = 'avatar-container-left';
  
    var avatar = document.createElement('img');
    avatar.className = 'avatar';
    avatar.src = 'image/left.png';
    bubbleContainer.appendChild(avatarContainer);
    bubbleContainer.appendChild(bubble);

    avatarContainer.appendChild(avatar);
    messageContainer.appendChild(bubbleContainer);
    messageContainer.scrollTop = messageContainer.scrollHeight;
  } else {
    var bubbleContainer = document.createElement('div');
    bubbleContainer.className = 'bubble-container-right';

    var bubble = document.createElement('div');
    bubble.className = 'bubble ' + direction;
    bubble.textContent = message;
    var avatarContainer = document.createElement('div');
    avatarContainer.className = 'avatar-container-right';
  
    var avatar = document.createElement('img');
    avatar.className = 'avatar';
    avatar.src = 'image/right.png';

    avatarContainer.appendChild(avatar);
    messageContainer.appendChild(bubbleContainer);
    messageContainer.scrollTop = messageContainer.scrollHeight;

    bubbleContainer.appendChild(avatarContainer);
    bubbleContainer.appendChild(bubble);
    
    
  }

  
}