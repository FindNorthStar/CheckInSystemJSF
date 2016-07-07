var notHavingCookie = function(cookieName) {
    return typeof $.cookie(cookieName) === 'undefined';
};

$(document).ready(function () {
    if (notHavingCookie("token") || notHavingCookie("teacherName")) {
        window.location = 'login';
    } else {
        $("#teacherName").text($.cookie("teacherName"));
    }
});