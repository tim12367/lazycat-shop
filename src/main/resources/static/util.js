// 刷新access token並重試
function retryWithRefreshToken(ajaxObj) {
    ajaxObj.tryCount++;
    if (ajaxObj.tryCount <= ajaxObj.retryLimit) {
        console.log("retry...");
        console.log(ajaxObj);
        // 1. Refresh token
        refreshToken();
        // 2. retry
        $.ajax(ajaxObj);
        return;
    } else {
        alert("請求失敗!")
        return;
    }
}

// 使用refresh token 重新請求 access token
function refreshToken() {
    localStorage.setItem("access_token", "refresh") // TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}