var messageContainer = document.getElementById('message-container');
var messageInput = document.getElementById('message-input');
var sendButton = document.getElementById('send-button');
var websocket;
var savedUsername;
var already_show_star_flag = 0;
window.addEventListener('load', function () {
    connectWebSocket();

    sendButton.addEventListener('click', function () {
        var message = messageInput.value;

        if (message.trim() !== '') {
            sendMessage(message);
            addMessageToChat(message, 'right');
            messageInput.value = '';
        }
    });

    messageInput.addEventListener('keydown', function (event) {
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

    websocket.onopen = function () {
        console.log('WebSocket连接已打开');
    };

    websocket.onmessage = function (event) {
        var message = event.data;
        var parsedData = null;
        try {
            parsedData = JSON.parse(message);
        } catch (error) {
            parsedData = null;
        }
        //判断是否需要弹出反馈框
        if (message == "#123") {
            if (already_show_star_flag == 0) {
                moveFirstRowToLast();
                showRatingModule();
                already_show_star_flag = 1;
                messageContainer.scrollTop = messageContainer.scrollHeight;
            }
        }
        else if (message.includes('#111')) {
            // 创建按钮容器
            var buttonContainer = document.createElement('div');
            buttonContainer.className = 'date-button-container';

            // 创建“是”按钮
            var yesButton = document.createElement('button');
            yesButton.className = 'date-button';
            yesButton.textContent = '是的，我需要修改';
            yesButton.onclick = function () {
                sendMessage('是');
                addMessageToChat('是的，我需要修改', 'right');
            };

            // 创建“否”按钮
            var noButton = document.createElement('button');
            noButton.className = 'date-button';
            noButton.textContent = '不必，表格内容无误';
            noButton.onclick = function () {
                sendMessage('否');
                addMessageToChat('不必，表格内容无误', 'right');
            };

            // 将按钮添加到容器
            buttonContainer.appendChild(yesButton);
            buttonContainer.appendChild(noButton);

            // 将按钮容器添加到聊天界面
            messageContainer.appendChild(buttonContainer);
            messageContainer.scrollTop = messageContainer.scrollHeight;
        }
        else if (message.startsWith('!')) {
            const messages = message.split('!'); // 按感叹号分割字符串
            var buttonContainer = document.createElement('div');
            buttonContainer.className = 'date-button-container';// 获取按钮容器

            for (const message_part of messages) {
                if (message_part.trim() !== '') { // 跳过空消息
                    const info = parseInfo(message_part); // 解析信息
                    if (info) {
                        const { moduleName, beginTime, endTime } = info;
                        const button = createButton(moduleName, beginTime, endTime); // 创建按钮
                        buttonContainer.appendChild(button); // 将按钮添加到容器
                    }
                }
            }
            messageContainer.appendChild(buttonContainer);
            messageContainer.scrollTop = messageContainer.scrollHeight;
        }
        else if (message.startsWith('%')) {
            message = message.substring(1);
            // message = "N.docx";
            var downloadButton = document.getElementById("downloadBtn"); // 获取按钮元素
            downloadButton.style.backgroundColor = "#44b2ed";
            downloadButton.style.color = "#fdfdfd"

            downloadButton.addEventListener("mouseover", function () {
                downloadButton.style.backgroundColor = "#70c7f6"; // 鼠标悬停时的背景颜色
                downloadButton.style.color = "#5e5d5d"
            });

            downloadButton.addEventListener("mouseout", function () {
                downloadButton.style.backgroundColor = "#44b2ed"; // 鼠标移出时恢复普通状态的背景颜色
                downloadButton.style.color = "#fdfdfd"
            });

            document.getElementById("downloadBtn").addEventListener("click", function () {
                var link = document.createElement("a");
                link.href = "http://localhost:8080/" + message;
                link.download = "SQLcode.sql";
                link.target = "_blank";
                document.getElementById("downloadBtn").appendChild(link);
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            });
        }
        else if (message.startsWith('$')) {
            var dates = message.split('$');
            dates.shift(); // 移除数组中的第一个空元素

            // 创建日期按钮容器
            var dateButtonContainer = document.createElement('div');
            dateButtonContainer.className = 'date-button-container';

            // 循环创建日期按钮
            dates.forEach(function (date) {
                var button = document.createElement('button');
                button.className = 'date-button';
                button.textContent = date;
                button.onclick = function () {
                    sendMessage(date); // 调用你的sendMessage函数发送日期
                };
                dateButtonContainer.appendChild(button);
            });

            // 将日期按钮容器添加到聊天界面
            messageContainer.appendChild(dateButtonContainer);
            messageContainer.scrollTop = messageContainer.scrollHeight;
        }
        //判断是否需要弹出
        else if (parsedData) {
            // 如果成功解析成 JSON，创建表格并添加到聊天界面
            var tableContainer = createTableFromJSON(parsedData);
            messageContainer.appendChild(tableContainer);
            messageContainer.scrollTop = messageContainer.scrollHeight;
        }
        else if (message.startsWith("新增还是修改")) {
            var buttonContainer = document.createElement('div');
            buttonContainer.className = 'date-button-container';

            // 创建“是”按钮
            var newButton = document.createElement('button');
            newButton.className = 'date-button';
            newButton.textContent = '新增';
            newButton.onclick = function () {
                sendMessage('新增');
            };

            // 创建“否”按钮
            var addButton = document.createElement('button');
            addButton.className = 'date-button';
            addButton.textContent = '修改';
            addButton.onclick = function () {
                sendMessage('修改');
            };

            // 将按钮添加到容器
            buttonContainer.appendChild(newButton);
            buttonContainer.appendChild(addButton);

            // 将按钮容器添加到聊天界面
            messageContainer.appendChild(buttonContainer);
            messageContainer.scrollTop = messageContainer.scrollHeight;
        }
        else {
            addMessageToChat(message, 'left');
        }
    };

    websocket.onclose = function () {
        console.log('WebSocket连接已关闭');
    };

    websocket.onerror = function () {
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
let lockedStars = 0;
let hoverStars = 0;

function toggleLock(stars) {
    lockedStars = stars;


    const allStars = document.querySelectorAll('.stars');
    allStars.forEach((star, index) => {
        if (index < lockedStars) {
            star.style.color = 'gold';
        } else {
            star.style.color = '#ccc';
        }
    });
}

function hoverRating(stars) {
    if (lockedStars === 0) {
        hoverStars = stars;
        const allStars = document.querySelectorAll('.stars');
        allStars.forEach((star, index) => {
            if (index < stars) {
                star.style.color = 'gold';
            } else {
                star.style.color = '#ccc';
            }
        });
    }
}
const ratingModule = document.getElementById('rating-module');
function showRatingModule() {
    ratingModule.style.display = 'block';
}

function moveFirstRowToLast() {
    const container = document.getElementById('message-container'); // 替换为你的容器ID
    const children = Array.from(container.children);

    const firstRowHeight = children[0].offsetHeight;

    // 计算需要移动的元素数量
    let elementsToMove = 0;
    let currentHeight = 0;

    for (let i = 0; i < children.length; i++) {
        if (currentHeight + children[i].offsetHeight > firstRowHeight) {
            elementsToMove = i;
            break;
        }
        currentHeight += children[i].offsetHeight;
    }

    // 移动元素
    if (elementsToMove > 0) {
        for (let i = 0; i < elementsToMove; i++) {
            container.appendChild(children[i]);
        }
    }
}

async function sendFeedback() {
    const url = 'http://localhost:8080/index/feedback';
    var selectedStars = lockedStars || hoverStars;
    var evaluateInput = document.getElementById('evaluate-input');
    const message = evaluateInput.value;
    const Conversation = {
        evaluation: selectedStars,
        feedback: message,
        username: savedUsername
    };
    sendMessage("#已提交反馈");
    const requestOptions = {
        method: 'POST',
        // mode: 'no-cors',
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        //body: Conversation
        body: JSON.stringify(Conversation)
    };
    console.log(JSON.stringify(Conversation));
    try {
        const response = await fetch(url, requestOptions);

        if (response.ok) {
            console.log('Feedback sent successfully!');
        } else {
            console.error('Failed to send feedback.');
        }
    } catch (error) {
        console.error('An error occurred:', error);
    }
}
function request_post() {
    var httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
    httpRequest.open('POST', 'application/json;charset=utf-8', true); //第二步：打开连接
    httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
    httpRequest.send('{"evaluation":3,"feedback":"123"}');//发送请求 将情头体写在send中
    /**
     * 获取数据后的处理程序
     */
    httpRequest.onreadystatechange = function () {//请求后的回调接口，可将请求成功后要执行的程序写在其中
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {//验证请求是否发送成功
            var json = httpRequest.responseText;//获取到服务端返回的数据
            console.log(json);
        }
    };
}
function createTableFromJSON(data) {
    var emphasizeIndicesLeft = [0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23]; // 需要加红色星号的属性在左侧表格
    var emphasizeIndicesRight = [24]; // 需要加红色星号的属性在右侧表格

    var tableContainer = document.createElement('div');
    tableContainer.className = 'table-container';

    var leftTable = document.createElement('table');
    leftTable.innerHTML = generateTableHTML(data, 0, 23, emphasizeIndicesLeft); // 填充左侧表格数据
    leftTable.className = 'left-table'; // 添加样式类名
    tableContainer.appendChild(leftTable);

    var spacingDiv = document.createElement('div');
    spacingDiv.className = 'table-spacing';
    tableContainer.appendChild(spacingDiv);

    var rightTable = document.createElement('table');
    rightTable.innerHTML = generateTableHTML(data, 24, 47, emphasizeIndicesRight); // 填充右侧表格数据
    rightTable.className = 'right-table'; // 添加样式类名
    tableContainer.appendChild(rightTable);

    return tableContainer;
}

function generateTableHTML(data, startIndex, endIndex, emphasizeIndices) {
    var tableHTML = '<table>';
    for (var i = startIndex; i <= endIndex; i++) {
        var key = Object.keys(data)[i];
        var value = data[key];

        var shouldEmphasize = emphasizeIndices.includes(i);
        var emphasizedKey = shouldEmphasize ? key + '<span class="emphasize">*</span>' : key;
        var valueCellContent = value !== 'null' ? value : '';

        tableHTML += '<tr><td class="attribute-cell">' + emphasizedKey + '</td><td class="value-cell">' + valueCellContent + '</td></tr>';
    }
    tableHTML += '</table>';
    return tableHTML;
}

document.addEventListener("DOMContentLoaded", function () {
    var urlParams = new URLSearchParams(window.location.search);
    savedUsername = urlParams.get("username");
    console.log("现在登录的人是：" + savedUsername);
    if (savedUsername) {
        console.log("Welcome, " + savedUsername + "!");
    }
});



function parseInfo(message) {
    const regex = /Module Name: ([^,]+), Begin Time: ([\d:]+), End Time: ([\d:]+)/;
    const matches = message.match(regex);

    if (matches && matches.length === 4) {
        const moduleName = matches[1];
        const beginTime = matches[2];
        const endTime = matches[3];
        return { moduleName, beginTime, endTime };
    }

    return null; // 无法解析的消息返回 null
}

function createButton(moduleName, beginTime, endTime) {
    const button = document.createElement('button');
    button.className = 'date-button';
    button.textContent = `${beginTime}-${endTime}`;
    button.onclick = function () {
        sendMessage(moduleName); // 向服务器发送模块名
    };
    return button;
}
