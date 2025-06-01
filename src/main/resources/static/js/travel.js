// 탭 전환 기능
function showTab(tabId) {
    const allTabs = document.querySelectorAll('.tab-content');
    allTabs.forEach(tab => tab.classList.remove('active'));
    document.getElementById(tabId).classList.add('active');
}

// 챗봇 메시지 전송 기능
function sendMessage() {
    const input = document.getElementById('chat-input');
    const message = input.value.trim();
    if (message === "") return;

    const messagesDiv = document.getElementById('messages');

    // 사용자 메시지
    const userMessage = document.createElement('div');
    userMessage.classList.add('user-message');
    userMessage.style.cssText = `
        background-color: #3b5998;
        color: white;
        padding: 10px;
        border-radius: 15px;
        margin-bottom: 8px;
        max-width: 80%;
        align-self: flex-end;
        text-align: right;
    `;
    userMessage.textContent = message;
    messagesDiv.appendChild(userMessage);

    // 챗봇 응답
    const botMessage = document.createElement('div');
    botMessage.classList.add('bot-message');
    botMessage.style.cssText = `
        background-color: #e0e0e0;
        color: #333;
        padding: 10px;
        border-radius: 15px;
        margin-bottom: 8px;
        max-width: 80%;
        align-self: flex-start;
        text-align: left;
    `;
    botMessage.textContent = "챗봇의 응답: " + message;
    messagesDiv.appendChild(botMessage);

    // 스크롤 최하단 이동
    messagesDiv.scrollTop = messagesDiv.scrollHeight;

    // 입력창 초기화
    input.value = '';
}

// 엔터 키로 메시지 전송
document.getElementById('chat-input').addEventListener('keydown', function (e) {
    if (e.key === 'Enter') {
        e.preventDefault();
        sendMessage();
    }
});
