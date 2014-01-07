function ListController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
	$scope.hasSpecialAccess = false;
	
	if ($routeParams.id != "new") {
        $http({method:'GET', url:'/api/book', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            $scope.bookList = data;
        });
    }
    
    $http({method:'GET', url:'/api/book/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.book = data;

        Breadcrumbs.setCrumbs([
            {label:"Liste", route:"/#/list" },
            {label:$scope.book.title, route:"/#/" + $routeParams.id}
        ]);
    });

    $http({method:'GET', url:'/api/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });
    
    $scope.new = function () {
        $location.path("/new/edit");
    }
    
    if (!User.firstName()) {
    	 $http({method:'GET', url:'/api/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
 			User.setFirstName(data);
 	    });
    }
    
    Breadcrumbs.setCrumbs([]);
}