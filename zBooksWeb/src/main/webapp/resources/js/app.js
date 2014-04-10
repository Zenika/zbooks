var app = angular.module("zBooks", ['ngRoute', 'ngCookies', 'Controllers', 'Services', 'Filters', 'BooksControllers', 'LoginControllers', 'NewsControllers', 'UsersControllers']).
    config(function ($routeProvider) {
        $routeProvider.when('/', {templateUrl:'resources/templates/login.html', controller:'LoginController'});
        $routeProvider.when('/news', {templateUrl:'resources/templates/news.html', controller:'NewsController'});
        $routeProvider.when('/list', {templateUrl:'resources/templates/list.html', controller:'ListController'});
        $routeProvider.when('/:id/edit', {templateUrl:'resources/templates/edit.html', controller:'EditController'});
        $routeProvider.when('/:id', {templateUrl:'resources/templates/detail.html', controller:'DetailController'});
        $routeProvider.when('/user/profile', {templateUrl:'resources/templates/users/profile.html', controller:'UserController'});

    });

app.run(['$rootScope','$location', '$routeParams', function($rootScope, $location, $routeParams) {
        $rootScope.$on('$routeChangeSuccess', function(e, current, pre) {
            console.log('Current route name: ' + $location.path());
            // Get all URL parameter
            console.log($routeParams);
        })
    }]);

app.factory("authentificationInterceptor", function($q, $location){
	return {
	 
	      // On response failture
	      responseError: function (rejection) {
	        // console.log(rejection); // Contains the data about the error.
	        if (rejection.status === 401){
	        	$location.path("/");
	        } else if (rejection.status === 403) {
	        	$location.path("/list");
	        }
	    	  
	        // Return the promise rejection.
	        return $q.reject(rejection);
	      }
	};
});

app.config(function($httpProvider) {
	$httpProvider.interceptors.push("authentificationInterceptor");
});