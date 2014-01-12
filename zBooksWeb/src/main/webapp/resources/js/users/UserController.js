function UserController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
	$scope.hasSpecialAccess = false;

;
    $http({method:'GET', url:User.uri(), headers:{'Accept':'application/json'}})
        .success(function (data, status, headers, config) {
            $scope.profile = data.profile;
        });


    Breadcrumbs.setCrumbs([
        {label:"Profile", route:"/#/user/profile" }
    ]);
}