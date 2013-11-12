function HomeController ($scope, $routeParams, $http) {
	$scope.message = $http.get("/user/coucou");
}