var app = angular.module("zBooks", []).
	config(function($routeProvider) {
		$routeProvider.when('/', {templateUrl:'resources/templates/helloUser.html', controller:HomeController});
	});