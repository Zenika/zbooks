var NewsControllers = angular.module('NewsControllers', ['Services', 'Filters'])

NewsControllers.controller('NewsController', function ($scope, $routeParams, $http, $location, Breadcrumbs, User) {
    $scope.hasSpecialAccess = false;

    $http({method:'GET', url:'/api/activities?sortBy=date&order=desc', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
//        for (var activityId in data) {
//            var activity = data[activityId];
//            activity.date = new Date (activity.date);
//        }
        $scope.activityList = data;
    });

    if (!User.firstName()) {
        $http({method:'GET', url:'/api/old/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            User.setFirstName(data);
        });
    }

    Breadcrumbs.setCrumbs([]);

    if ($scope.currentPage < 0) {
        $scope.currentPage = 0;
    }

});