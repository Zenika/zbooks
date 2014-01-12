function EditController($scope, $routeParams, $http, $location, Breadcrumbs, User) {
    $scope.book = new Object();

    $scope.hasSpecialAccess = false;
    $http({method:'GET', url:'/api/old/hasSpecialAccess', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.hasSpecialAccess = data;
    });

    $scope.couldDelete = false;
    $scope.confirmDeleteFlag = false;
    $scope.showDelete = false;

    $scope.message = "";
    $scope.messageType;

    $scope.languages = [
        {code:"FR"},
        {code:"EN"}
    ];
    
    $scope.collections = [
        {code:"SBR", value:"Sébastien Brousse"},
        {code:"NANTES", value:"Nantes"},
        {code:"RENNES", value:"Rennes"}
    ];

    $scope.ERROR_TYPE = "alert-danger";
    $scope.WARNING_TYPE = "alert-warning";
    $scope.INFO_TYPE = "alert-info";
    $scope.SUCCESS_TYPE = "alert-success";

    $scope.getLanguage = function (lang) {
        if (lang == "FR" || !lang)
            return $scope.languages[0];
        else
            return $scope.languages[1];
    }

    $scope.currentLanguage = $scope.getLanguage();
    
    $scope.getCollection = function (coll) {
        if (coll === "RENNES" || !coll)
            return $scope.collections[2];
        else if (coll === "NANTES")
            return $scope.collections[1];
        else
        	return $scope.collections[0];
    }
    
    $scope.currentCollection = $scope.getCollection();

    $scope.confirmDelete = function () {
        $scope.confirmDeleteFlag = true;
        $scope.showDelete = false;
    }

    $scope.closeMessage = function () {
        $scope.message = "";
    }

    $scope.isMessage = function () {
        return $scope.message && $scope.message.length > 0;
    }

    $scope.cancelDelete = function () {
        $scope.confirmDeleteFlag = false;
        $scope.showDelete = true;
    }

    $scope.delete = function () {
        $http({method:'DELETE', url:'/api/books/' + $routeParams.id}).
            success(function (data, status, headers, config) {
                $location.path("/list");
                $scope.confirmDeleteFlag = false;
            }).
            error(function (data, status, headers, config) {
                $scope.message = "Une erreur est survenue lors de la suppression du livre.";
                $scope.messageType = $scope.ERROR_TYPE;
                $scope.confirmDeleteFlag = false;
                scrollTo("bodyPanel");
            });
    }

    $scope.update = function () {
        $scope.book.language = $scope.currentLanguage.code;
        $scope.book.zcollection=$scope.currentCollection.code;

        if ($scope.book.id) {
            $http({method:'PUT', url:'/api/books/' + $routeParams.id, data:$scope.book, headers:{'Content-Type':'application/json'}}
            ).
                success(function (data, status, headers, config) {
                    $location.path("/" + data.id);
                }).
                error(function (data, status, headers, config) {
                    $scope.message = "Une erreur est survenue lors de la modification du livre.";
                    $scope.messageType = $scope.ERROR_TYPE;
                    scrollTo("bodyPanel");
                });
        } else {
            $http({method:'POST', url:'/api/books', data:$scope.book, headers:{'Content-Type':'application/json'}}
            ).
                success(function (data, status, headers, config) {
                    $location.path("/list");
                }).
                error(function (data, status, headers, config) {
                    $scope.message = "Une erreur est survenue lors de la création du livre.";
                    $scope.messageType = $scope.ERROR_TYPE;
                    scrollTo("bodyPanel");
                });

        }
    }

    $scope.getDataCallback = function (data) {
        $scope.book = data;

        $scope.currentLanguage = $scope.getLanguage($scope.book.language);

        $scope.couldDelete = true;
        $scope.showDelete = true;

        Breadcrumbs.setCrumbs([
            {label:"Liste", route:"/#/list" },
            {label:$scope.book.title, route:"/#/" + $routeParams.id},
            {label:"Modification", route:"/#/" + $routeParams.id + "/edit"}
        ]);
    }


    $scope.getData = function () {
        $http({method:'GET', url:'/api/books/' + $routeParams.id, headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            $scope.getDataCallback(data);
        }).error(function () {
                $scope.message = "Une erreur est survenue lors de la récupération des informations du livre.";
                $scope.messageType = $scope.ERROR_TYPE;
                $scope.confirmDeleteFlag = false;
            });
    }

    $scope.scrollTo = function (anchorId) {
        $('html, body').animate({scrollTop:$("#" + anchorId).offset().top}, 500);
    }

    $scope.importCallback = function (data) {
        if (data.totalItems >= 1) {
            var volumeInfo = data.items[0].volumeInfo;

            $scope.book.title = volumeInfo.title;
            if (volumeInfo.authors)
                $scope.book.authors = volumeInfo.authors[0];
            $scope.book.pagesNumber = volumeInfo.pageCount
            if (volumeInfo.imageLinks)
                $scope.book.cover = volumeInfo.imageLinks.smallThumbnail
            if (volumeInfo.language) {
                $scope.book.language = volumeInfo.language.toUpperCase();
                $scope.currentLanguage = $scope.getLanguage($scope.book.language);
            }
            $scope.book.releaseDate = volumeInfo.publishedDate;
            $scope.book.edition = volumeInfo.publisher;

            $scope.message = "Les données du livre ont été mise à jour avec les données importées";
            $scope.messageType = $scope.INFO_TYPE;
        } else {
            $scope.message = "Aucun livre ne correspond a cet ISBN chez Google...";
            $scope.messageType = $scope.WARNING_TYPE;
        }
    }
    $scope.cleanISBN = function () {
        if ($scope.book.isbn)
            $scope.book.isbn = $scope.book.isbn.replace(/\W/g, '');
    }
    $scope.import = function () {

        var url = 'https://www.googleapis.com/books/v1/volumes?callback=JSON_CALLBACK&q=isbn:' + $scope.book.isbn;

        $http.jsonp(url).success(function (data) {
            $scope.importCallback(data);
        }).error(function (data) {
                $scope.messageType = $scope.ERROR_TYPE;
                $scope.message = "Une erreur est survenue lors de l'import.";

            });

    }

    if ($routeParams.id != "new") {
        $scope.getData();
    }
    Breadcrumbs.setCrumbs([
        {label:"Liste", route:"/#/list" },
        {label:"Nouveau Livre", route:"/#/new/edit" }
    ]);
    if (!User.firstName()) {
   	 $http({method:'GET', url:'/api/old/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
			User.setFirstName(data);
	    });
   }
}