function LoginController ($scope, $location, $cookieStore, $http, Breadcrumbs, Authenticated, User) {
	$scope.user = new Object();

	$scope.message = "";
	$scope.messageType;

	$scope.ERROR_TYPE = "alert-danger";
	$scope.WARNING_TYPE = "alert-warning";
	$scope.INFO_TYPE = "alert-info";
	$scope.SUCCESS_TYPE = "alert-success";

	$scope.closeMessage = function () {
		$scope.message = "";
	}

	$scope.isMessage = function () {
		return $scope.message && $scope.message.length > 0;
	}
	
	$scope.authenticateUser = function (headers) {
		Authenticated.setAuthenticated(true);
		User.setUri(headers('Location'));
		$location.path("/news");
	}

	$http({method:'GET', url:'/authenticated', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
		if (data.toLowerCase()==="true") {
			$scope.authenticateUser(headers);
		}
	});

	$scope.logIn = function () {
		$http({method:'POST', url:'/login', data:{email: $scope.user.email, password: sha256_digest($scope.user.password)}, headers:{'Content-Type':'application/json'}}
		).
		success(function (data, status, headers, config) {
			if (data.toLowerCase()==="true") {
				$scope.authenticateUser(headers);
			} else {
				$scope.message = "Votre identifiant/mot de passe est faux. Veuillez réessayer.";
				$scope.messageType = $scope.INFO_TYPE;
			}
		}).
		error(function (data, status, headers, config) {
			$scope.message = "Une erreur est survenue lors de votre authentification."
				$scope.messageType = $scope.ERROR_TYPE;
		});
	}

	Breadcrumbs.setCrumbs([]);
	Authenticated.setAuthenticated(false);
	if (User.firstName()) {
		User.setFirstName("");
	}
}