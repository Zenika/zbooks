function DetailController ($scope, $routeParams, $http) {
    $scope.book = {isbn:$routeParams.isbn,title:"Professional NoSQL	John Wiley & Sons Ltd",lang:"EN",cover:"./resources/image/unknown-cover.gif"};
}