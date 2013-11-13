var app = angular.module("zBooks", []).
	config(function($routeProvider) {
		$routeProvider.when('/', {templateUrl:'helloUser.html', controller:HomeController});
	});