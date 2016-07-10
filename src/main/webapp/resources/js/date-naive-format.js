/**
 * Created by Alexander Qi on 7/9/16.
 */

var prefixZero = function(s) {
    if (s.length === 1) return "0" + s;
    return s;
};

$.getTodayString = function() {
    var date = new Date();
    return date.getFullYear().toString() + "-" + prefixZero((date.getMonth() + 1).toString()) + "-" + prefixZero(date.getDate().toString());
};

$.getLastYearString = function() {
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month === 2 && day === 29) {
        day = 28;
    }
    return (date.getFullYear() - 1).toString() + "-" + prefixZero(month.toString()) + "-" + prefixZero(day.toString());
};