var Filters = angular.module("Filters", []);

Filters.filter('img404safe', function () {
    return function (src) {
        if (src == undefined || src.length == 0) {
            return "1px";
        } else {
            return src;
        }
    };
});

Filters.filter('coverFilter', function () {
    return function (src) {
        if (src == undefined || src.length == 0) {
            return "./resources/image/unknown-cover.gif";
        } else {
            return src;
        }
    };
});

Filters.filter('activityType', function () {
    return function (src) {
        if (src != undefined || src.length != 0) {
            if (src == 'BOOK') {
                return "Ajout d'un Livre";
            }
        }
        return src;
    };
});
