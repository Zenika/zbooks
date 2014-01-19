function ListController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
    $scope.hasSpecialAccess = false;

    if ($routeParams.id != "new") {
        if ($routeParams.page) {
            $http({method:'GET', url:'/api/books?page='+$routeParams.page, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
                $scope.bookList = data;
            });
        } else {
            $http({method:'GET', url:'/api/books', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
                $scope.bookList = data;
            });
        }
    }

    if ($routeParams.page == null) {
        $scope.currentPage = 0;
    } else {
        $scope.currentPage = +$routeParams.page; //convert to int
    }


    $http({method:'GET', url:'/api/users/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });

    $scope.new = function () {
        $location.path("/new/edit");
    }

    if (!User.firstName()) {
        $http({method:'GET', url:'/api/old/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            User.setFirstName(data);
        });
    }

    Breadcrumbs.setCrumbs([]);

    if ($scope.currentPage < 0) {
        $scope.currentPage = 0;
    }

    $scope.prevPage = function() {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
            console.info("--");
            $location.search('page',$scope.currentPage).path('/list');
        }
    }

    $scope.nextPage = function() {
        if ($scope.currentPage < $scope.consultantsList.numberOfPages) {
            $scope.currentPage++;
            console.info("++");
            $location.search('page',$scope.currentPage).path('/list');
        }
    }

    $scope.setPage = function() {
        $scope.currentPage = this.n;
        console.info("setPage : "+$scope.currentPage);
        $location.search('page',$scope.currentPage)

        console.info("setPage : "+$location.absUrl());
        $location.search('page',$scope.currentPage).path('/list');
    }

    $scope.range = function (start, end) {
        var ret = [];
        if (!end) {
            end = start;
            start = 0;
        }
        for (var i = start; i < end; i++) {
            ret.push(i);
        }
        return ret;
    };
}