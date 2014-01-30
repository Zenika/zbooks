var app = angular.module("zBooks", ['ngRoute', 'ngCookies']).
    config(function ($routeProvider) {
        $routeProvider.when('/', {templateUrl:'resources/templates/login.html', controller:LoginController});
        $routeProvider.when('/news', {templateUrl:'resources/templates/news.html', controller:NewsController});
        $routeProvider.when('/list', {templateUrl:'resources/templates/list.html', controller:ListController});
        $routeProvider.when('/:id/edit', {templateUrl:'resources/templates/edit.html', controller:EditController});
        $routeProvider.when('/:id', {templateUrl:'resources/templates/detail.html', controller:DetailController});
        $routeProvider.when('/user/profile', {templateUrl:'resources/templates/users/profile.html', controller:UserController});

    });

app.run(['$rootScope','$location', '$routeParams', function($rootScope, $location, $routeParams) {
        $rootScope.$on('$routeChangeSuccess', function(e, current, pre) {
            console.log('Current route name: ' + $location.path());
            // Get all URL parameter
            console.log($routeParams);
        })
    }]);

app.filter('img404safe', function () {
    return function (src) {
        if (src == undefined || src.length == 0) {
            return "1px";
        } else {
            return src;
        }
    };
});

app.filter('coverFilter', function () {
    return function (src) {
        if (src == undefined || src.length == 0) {
            return "./resources/image/unknown-cover.gif";
        } else {
            return src;
        }
    };
});

app.factory("Breadcrumbs", function () {
    var crumbs = new Array();
    return {
        crumbs:function () {
            return crumbs;
        },
        setCrumbs:function (newCrum) {
            crumbs = newCrum;
        },
        isEmpty:function () {
            return crumbs.length == 0;
        }
    };
});

app.service("User", function () {
    var firstName = "";
    var uri = "";
    return {
        firstName:function () {
            return firstName;
        },
        setFirstName:function (newFirstName) {
        	firstName = newFirstName;
        },
        uri:function () {
            return uri;
        },
        setUri: function (newUri) {
            uri = newUri;
        }
    };
});

app.factory("Authenticated", function () {
    var isAuthenticated = true;
    return {
        isAuthenticated:function () {
            return isAuthenticated;
        },
        setAuthenticated:function (authenticated) {
        	isAuthenticated = authenticated;
        }
    };
});

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

function rootController($scope, $location, $http, Breadcrumbs, Authenticated, User) {
    $scope.goHome = function () {
        $location.path("/");
    };

    $scope.breadcrumbs = function () {
        return Breadcrumbs.crumbs();
    }

    $scope.isBreadcrumbsEmpty = function () {
        return Breadcrumbs.isEmpty();
    }
    
    $scope.isZUserConnected = function () {
    	return Authenticated.isAuthenticated();
    }

    $scope.disconnectZUser = function () {
    	 $http({method:'PUT', url:'/disconnect', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
    	    });
    }

    $scope.firstname = function () {
    	return User.firstName();
    }
}
