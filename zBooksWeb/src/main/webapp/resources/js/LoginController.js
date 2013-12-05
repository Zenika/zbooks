function LoginController ($scope, $location, $cookieStore, $http, Breadcrumbs) {
	$scope.user = new Object();
	
	$scope.message = "";
	$scope.messageType;
	
    $scope.closeMessage = function () {
        $scope.message = "";
    }
    
    $scope.isMessage = function () {
        return $scope.message && $scope.message.length > 0;
    }
	
	$scope.logIn = function () {
		$http({method:'POST', url:'/login', data:{userName: $scope.user.userName, password: sha256_digest($scope.user.password)}, headers:{'Content-Type':'application/json'}}
        ).
            success(function (data, status, headers, config) {
            	$location.path("/list");
            }).
            error(function (data, status, headers, config) {
            	$scope.message = "Une erreur est survenue lors de votre authentification."
                $scope.messageType = $scope.ERROR_TYPE;
            });
	}
	
    Breadcrumbs.setCrumbs([]);
}