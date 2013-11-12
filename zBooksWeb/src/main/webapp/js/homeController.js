function HomeController ($scope, $routeParams, Hello) {
	$scope.message = Hello.get();
}