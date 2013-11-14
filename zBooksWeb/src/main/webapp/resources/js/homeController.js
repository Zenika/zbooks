function HomeController ($scope, $routeParams, $http) {
	$scope.message = $http.get("/user/coucou");
    $scope.bookList = [
    {isbn:"047094224X",title:"Professional NoSQL	John Wiley & Sons Ltd",lang:"EN"},
    {isbn:"0596518846",title:"SQL in a Nutshell	O'Reilly",lang:"EN"},
    {isbn:"2212136382",title:"HTML 5 : Une référence pour le développeur web	Eyrolles",lang:"FR"},
    {isbn:"2744025828",title:"Javascript - Les bons éléments",lang:"FR"},
    {isbn:"1449344852",title:"AngularJS	O'Reilly",lang:"EN"},
    {isbn:"0131872486",title:"Thinking in Java	Prentice Hall",lang:"EN"},
    {isbn:"210059446X",title:"Scrum - Le guide pratique de la méthode agile la plus populaire	Dunod",lang:"FR"}
    ]
}