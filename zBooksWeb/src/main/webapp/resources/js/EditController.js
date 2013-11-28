function EditController($scope, $routeParams, $http, $location, Breadcrumbs) {
    $scope.book = new Object();
    $scope.couldDelete = false;
    $scope.confirmDeleteFlag = false;
    $scope.showDelete = false;
    $scope.errorMessage = "Une erreur est survenue.";
    $scope.isSuccessMessage = false;
    $scope.isError = false;
    $scope.languages = [
        {code:"FR"},
        {code:"EN"}
    ];
    $scope.currentLanguage = $scope.languages[0];

    $scope.confirmDelete = function () {
        $scope.confirmDeleteFlag = true;
        $scope.showDelete = false;
    }

    $scope.closeErrorMessage = function () {
        $scope.isError = false;
    }
    $scope.closeSuccessMessage = function () {
        $scope.isSuccessMessage = false;
    }

    $scope.cancelDelete = function () {
        $scope.confirmDeleteFlag = false;
        $scope.showDelete = true;
    }

    $scope.delete = function () {
        $http({method:'DELETE', url:'/api/book/' + $routeParams.id}).
            success(function (data, status, headers, config) {
                $location.path("/");
                $scope.confirmDeleteFlag = false;
            }).
            error(function (data, status, headers, config) {
                $scope.isError = true;
                $scope.errorMessage = "Une erreur est survenue lors de la suppression du livre.";
                $scope.confirmDeleteFlag = false;
                scrollTo("bodyPanel");
            });
    }

    $scope.update = function () {
        $scope.book.language = $scope.currentLanguage.code;
        $http({method:'POST', url:'/api/book/' + $routeParams.id, data:$scope.book, headers:{'Content-Type':'application/json'}}
        ).
            success(function (data, status, headers, config) {
                $scope.isSuccessMessage = true;
            }).
            error(function (data, status, headers, config) {
                $scope.isError = true;
                $scope.errorMessage = "Une erreur est survenue lors de la modification du livre.";
                scrollTo("bodyPanel");
            });
    }

    $scope.getDataCallback = function (data) {
        $scope.book = data;

        if ($scope.book.language == "FR")
            $scope.currentLanguage = $scope.languages[0];
        else
            $scope.currentLanguage = $scope.languages[1];

        $scope.couldDelete = true;
        $scope.showDelete = true;

        Breadcrumbs.setCrumbs([
            {label:"Liste", route:"/" },
            {label:$scope.book.title, route:"/#/" + $routeParams.id},
            {label:"Modification", route:"/#/" + $routeParams.id + "/edit"}
        ]);
    }

    $scope.getData = function () {
        $http({method:'GET', url:'/api/book/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            $scope.getDataCallback(data);
        }).error(function () {
                $scope.errorMessage = "Une erreur est survenue lors de la récupération des informations du livre.";
                $scope.confirmDeleteFlag = false;
            });
    }

    $scope.scrollTo = function (anchorId) {
        $('html, body').animate({scrollTop:$("#" + anchorId).offset().top}, 500);
    }

    $scope.importCallback = function (data) {
        if (data.totalItems == 1) {
            $scope.book.title = data.items[0].volumeInfo.title;
            $scope.book.authors = data.items[0].volumeInfo.authors[0];
            $scope.book.pagesNumber = data.items[0].volumeInfo.pageCount
            $scope.book.cover = data.items[0].volumeInfo.imageLinks.smallThumbnail
            $scope.book.language = data.items[0].volumeInfo.language;
            $scope.book.releaseDate = data.items[0].volumeInfo.publishedDate;
            $scope.book.edition = data.items[0].volumeInfo.publisher
        }
    }
    $scope.import = function () {

        var url = 'https://www.googleapis.com/books/v1/volumes?callback=JSON_CALLBACK&q=isbn' + $scope.book.isbn;

        $http.jsonp(url).success(function (data) {
            $scope.importCallback(data);
        }).error(function (data) {
                $scope.isError = true;
                $scope.errorMessage = "Une erreur est survenue lors de l'import.";

            });

    }

    if ($routeParams.id != "new") {
        $scope.getData();
    }
    Breadcrumbs.setCrumbs([
        {label:"Liste", route:"/" },
        {label:"Nouveau Livre", route:"/new/edit" }
    ]);
}