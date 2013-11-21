var app = angular.module("zBooks", []).
	config(function($routeProvider) {
		$routeProvider.when('/', {templateUrl:'resources/templates/list.html', controller:ListController});
        $routeProvider.when('/:id', {templateUrl:'resources/templates/detail.html', controller:DetailController});
	});

app.filter('img404safe', function() {
    return function(src) {
        if (src == undefined || src.length==0) {
            return "1px";
        }else{
            return src;
        }
    };
});