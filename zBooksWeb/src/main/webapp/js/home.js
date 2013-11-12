var app = angular.module("zBooks", []).
	config(function($routeProvider) {
		$routeProvider.when('/user/:name', {templateURL:'/views/helloUser.html', controller:HomeController})
	});