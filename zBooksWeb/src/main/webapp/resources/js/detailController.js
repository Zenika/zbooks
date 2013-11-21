function DetailController ($scope, $routeParams, $http) {
    $http({method: 'GET', url: '/api/book/'+$routeParams.id, headers:{'Accept' : 'application/json'}}).success(function(data, status, headers, config){
        $scope.book =data;
    });
}