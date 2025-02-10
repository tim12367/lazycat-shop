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
        refreshAccessToken();
        // 2. retry
        $.ajax(ajaxObj);
        return;
    } else {
        alert("請求失敗!")
        return;
    }
}

// 使用refresh token 重新請求 access token
function refreshAccessToken() {
    const refreshToken = getRefreshToken();
    if (refreshToken == null) {
        console.log("沒有登入紀錄!!請重新登入!");
    }

    // 請求refresh token
    $.ajax({
        url: '/get-access-token',
        type: 'POST',
        data: "",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", ('Bearer ' + getRefreshToken())) // 送出前取token
        },
        processData: false, // 不將請求處理為query string ex:{'a':1,'b':2} -> a=1&b=2
        contentType: false,
        success: function (result) {
            console.log("刷新access token成功");
            setAccessToken(result);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("刷新access token失敗!\n" + XMLHttpRequest.status + ': ' + textStatus);
        }
    });
}

// 設定refresh token
function setRefreshToken(token) {
    localStorage.setItem('refresh_token', token);
}

function getRefreshToken() {
    return localStorage.getItem('refresh_token');
}

// 設定refresh token
function setAccessToken(token) {
    localStorage.setItem('access_token', token);
}

function getAccessToken() {
    return localStorage.getItem('access_token');
}