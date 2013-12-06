function DetailController($scope, $routeParams, $http, $location, Breadcrumbs) {
    $scope.hasSpecialAccess = false;
	
	$http({method:'GET', url:'/api/book/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.book = data;

        Breadcrumbs.setCrumbs([
            {label:"Liste", route:"/#/list" },
            {label:$scope.book.title, route:"/#/" + $routeParams.id}
        ]);
    });
    $scope.edit = function () {
        $location.path("/" + $routeParams.id + "/edit");
    }

    Breadcrumbs.setCrumbs([
        {label:"Liste", route:"/#/list" }
    ]);

    $http({method:'GET', url:'/api/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });
}