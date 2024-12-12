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

    // 사용자 메시지 표시
    const messagesDiv = document.getElementById('messages');
    const userMessage = document.createElement('div');
    userMessage.classList.add('user-message');
    userMessage.textContent = message;
    messagesDiv.appendChild(userMessage);

    // 챗봇 응답
    const botMessage = document.createElement('div');
    botMessage.classList.add('bot-message');
    botMessage.textContent = "챗봇의 응답: " + message;  // 간단한 응답 예시
    messagesDiv.appendChild(botMessage);

    // 대화창 스크롤
    messagesDiv.scrollTop = messagesDiv.scrollHeight;

    // 입력창 초기화
    input.value = '';
}
