function ListController($scope, $routeParams, $http, $location, Breadcrumbs) {
    if ($routeParams.id != "new") {
        $http({method:'GET', url:'/api/book', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            $scope.bookList = data;
        });
    }

    $scope.new = function () {
        $location.path("/new/edit");
    }

    Breadcrumbs.setCrumbs([]);
}