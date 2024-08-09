const express = require('express');
const app = express();
const path = require('path');

// KakaoMap.html 파일을 서빙하는 라우트 설정
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'KakaoMap.html'));
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
