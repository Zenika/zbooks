var app = angular.module("zBooks", []).
    config(function ($routeProvider) {
        $routeProvider.when('/', {templateUrl:'resources/templates/list.html', controller:ListController});
        $routeProvider.when('/:id/edit', {templateUrl:'resources/templates/edit.html', controller:EditController});
        $routeProvider.when('/:id', {templateUrl:'resources/templates/detail.html', controller:DetailController});
    });

app.filter('img404safe', function () {
    return function (src) {
        if (src == undefined || src.length == 0) {
            return "1px";
        } else {
            return src;
        }
    };
});

app.filter('coverFilter', function () {
    return function (src) {
        if (src == undefined || src.length == 0) {
            return "./resources/image/unknown-cover.gif";
        } else {
            return src;
        }
    };
});

app.factory("Breadcrumbs", function () {
    var crumbs = new Array();
    return {
        crumbs:function () {
            return crumbs;
        },
        setCrumbs:function (newCrum) {
            crumbs = newCrum;
        },
        isEmpty:function () {
            return crumbs.length == 0;
        }
    };
});

function rootController($scope, $location, Breadcrumbs) {
    $scope.goHome = function () {
        $location.path("/");
    };

    $scope.breadcrumbs = function () {
        return Breadcrumbs.crumbs();
    }

    $scope.isBreadcrumbsEmpty = function () {
        return Breadcrumbs.isEmpty();
    }
}
