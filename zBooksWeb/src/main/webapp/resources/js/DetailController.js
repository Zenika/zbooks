function DetailController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
    $scope.hasSpecialAccess = false;
    $scope.canBeReturned = false;
    
    $scope.ERROR_TYPE = "alert-danger";
    $scope.WARNING_TYPE = "alert-warning";
    $scope.INFO_TYPE = "alert-info";
    $scope.SUCCESS_TYPE = "alert-success";
    $scope.messageType;
    
    $scope.canReturnBook = function () {
    	$http({method:'GET', url:'/api/canReturnBook/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
	 		  $scope.canBeReturned = data;
	 	}); 	   
    }
    
    $scope.refreshBook = function () {
		$http({method:'GET', url:'/api/book/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
	        $scope.book = data;
	        $scope.canReturnBook();
	
	        Breadcrumbs.setCrumbs([
	            {label:"Liste", route:"/#/list" },
	            {label:$scope.book.title, route:"/#/" + $routeParams.id}
	        ]);
	    });
    }
    $scope.edit = function () {
        $location.path("/" + $routeParams.id + "/edit");
    }

    Breadcrumbs.setCrumbs([
        {label:"Liste", route:"/#/list" }
    ]);

    $http({method:'GET', url:'/api/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });
    
    $scope.borrow = function () {
    	$http({method:'PUT', url:'/api/borrow/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            if (data) {
	    		$scope.message = "Vous avez emprunté ce livre. Bonne lecture !";
	            $scope.messageType = $scope.SUCCESS_TYPE;
	            $scope.refreshBook();
            } else {
            	$scope.message = "Vous n'avez pas pu emprunter le livre.";
            	$scope.messageType = $scope.WARNING_TYPE;
            }
            	
        });
    }
    
    $scope.return = function () {
    	$http({method:'PUT', url:'/api/return/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            if (data) {
            	$scope.message = "Merci de l'avoir rendu !";
            	$scope.messageType = $scope.SUCCESS_TYPE;
            	$scope.refreshBook();
            } else {
            	$scope.message = "Vous n'avez pas pu rendre le livre.";
            	$scope.messageType = $scope.WARNING_TYPE;
            }
            
        });
    }
    
    $scope.isMessage = function () {
        return $scope.message && $scope.message.length > 0;
    }
    
    $scope.closeMessage = function () {
        $scope.message = "";
    }
    
    $scope.refreshBook();
    
    if (!User.firstName()) {
   	 $http({method:'GET', url:'/api/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
			User.setFirstName(data);
	    });
   }
}