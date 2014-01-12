function ListController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
	$scope.hasSpecialAccess = false;
	
	if ($routeParams.id != "new") {
        $http({method:'GET', url:'/api/books', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            $scope.bookList = data;
        });
    }

    $http({method:'GET', url:'/api/users/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });
    
    $scope.new = function () {
        $location.path("/new/edit");
    }
    
    if (!User.firstName()) {
    	 $http({method:'GET', url:'/api/old/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
 			User.setFirstName(data);
 	    });
    }
    
    Breadcrumbs.setCrumbs([]);
}