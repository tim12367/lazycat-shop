// 刷新access token並重試
function retryWithRefreshToken(ajaxObj) {
    if (ajaxObj == null) {
        console.error("必須傳入要ajax的資料!");
        return;
    }

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
    const refreshToken = localStorage.getItem("refresh_token");
    if (refreshToken == null) {
        console.log("沒有登入紀錄!!請重新登入!");
    }
    localStorage.setItem("access_token", "refresh");// TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}