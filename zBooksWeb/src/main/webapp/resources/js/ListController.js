function ListController ($scope, $routeParams, $http) {
	$http({method: 'GET', url: '/api/book', headers:{'Accept' : 'application/json'}}).success(function(data, status, headers, config){
        $scope.bookList =data;
    });
}