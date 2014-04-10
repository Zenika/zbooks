var UsersControllers = angular.module('UsersControllers', ['Services', 'Filters'])

UsersControllers.controller('UserController', function UserController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
	$scope.hasSpecialAccess = false;
    $scope.message = "";
    $scope.messageType;
    $scope.ERROR_TYPE = "alert-danger";
    $scope.WARNING_TYPE = "alert-warning";
    $scope.INFO_TYPE = "alert-info";
    $scope.SUCCESS_TYPE = "alert-success";

    $scope.closeMessage = function () {
        $scope.message = "";
    }

    $scope.isMessage = function () {
        return $scope.message && $scope.message.length > 0;
    }

    $http({method:'GET', url:'/api/users/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });

    $http({method:'GET', url:User.uri(), headers:{'Accept':'application/json'}})
        .success(function (data, status, headers, config) {
            $scope.user = data;
        });

    $scope.edit = function () {
        $location.path("/user/" + $routeParams.id + "/edit");
    }

    Breadcrumbs.setCrumbs([
        {label:"Profile", route:"/#/user/profile" }
    ]);
});