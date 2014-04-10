var Controllers = angular.module('Controllers', ['Services', 'Filters'])

Controllers.controller('RootController', function ($scope, $location, $http, Breadcrumbs, Authenticated, User) {
    $scope.goHome = function () {
        $location.path("/");
    };

    $scope.breadcrumbs = function () {
        return Breadcrumbs.crumbs();
    }

    $scope.isBreadcrumbsEmpty = function () {
        return Breadcrumbs.isEmpty();
    }
    
    $scope.isZUserConnected = function () {
    	return Authenticated.isAuthenticated();
    }

    $scope.disconnectZUser = function () {
    	 $http({method:'PUT', url:'/disconnect', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
    	    });
    }

    $scope.firstname = function () {
    	return User.firstName();
    }
});
