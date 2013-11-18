var app = angular.module("zBooks", []).
	config(function($routeProvider) {
		$routeProvider.when('/', {templateUrl:'resources/templates/list.html', controller:ListController});
        $routeProvider.when('/:isbn', {templateUrl:'resources/templates/detail.html', controller:DetailController});
	});