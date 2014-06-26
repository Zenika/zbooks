var BotmControllers = angular.module('BotmControllers', ['Services', 'Filters'])

BotmControllers.controller('BotmController', function ($scope, $routeParams, $http, $location, Breadcrumbs, User) {
   $http({method:'GET', url:'/api/botm?sortBy=date&order=desc', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
        $scope.bookList = data;
    });

    $scope.new = function () {
        $location.path("/botm/submit");
    }

    $scope.star = function () {
        $http({method:'POST', url:'/api/botm/vote/' + $routeParams.id, headers:{'Content-Type':'application/json'}}).success(function (data, status, headers, config) {
            $location.path("/botm");
        });
    }

    if (!User.firstName()) {
        $http({method:'GET', url:'/api/old/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            User.setFirstName(data);
        });
    }

    Breadcrumbs.setCrumbs([]);

});

BotmControllers.controller('SubmitController', function ($scope, $routeParams, $http, $location, Breadcrumbs, User) {
    $scope.book = new Object();

    $scope.showDelete = false;

    $scope.message = "";
    $scope.messageType;

    $scope.languages = [
        {code:"FR"},
        {code:"EN"}
    ];

    $scope.collections = [
        {code:"BOTM", value:"Book of the Month"}
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
        return $scope.collections[0];
    }

    $scope.currentCollection = $scope.getCollection();

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

    $scope.update = function () {
        $scope.book.language = $scope.currentLanguage.code;
        $scope.book.zcollection=$scope.currentCollection.code;

        $http({method:'POST', url:'/api/botm', data:$scope.book, headers:{'Content-Type':'application/json'}}
            ).
                success(function (data, status, headers, config) {
                    $location.path("/botm");
                }).
                error(function (data, status, headers, config) {
                    $scope.message = "Une erreur est survenue lors de la création du livre.";
                    $scope.messageType = $scope.ERROR_TYPE;
                    scrollTo("bodyPanel");
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

    Breadcrumbs.setCrumbs([
        {label:"Livre du mois", route:"/#/botm" },
        {label:"Nouveau Livre", route:"/#/botm/submit" }
    ]);
    if (!User.firstName()) {
        $http({method:'GET', url:'/api/old/getFirstName', headers:{'Accept':'application/json'}}).success(function (data, status, headers, config) {
            User.setFirstName(data);
        });
    }
});