var Services = angular.module("Services", []);

Services.factory("Breadcrumbs", function () {
    var crumbs = new Array();
    return {
        crumbs:function () {
            return crumbs;
        },
        setCrumbs:function (newCrum) {
            crumbs = newCrum;
        },
        isEmpty:function () {
            return crumbs.length == 0;
        }
    };
});

Services.service("User", function () {
    var firstName = "";
    var uri = "";
    return {
        firstName:function () {
            return firstName;
        },
        setFirstName:function (newFirstName) {
            firstName = newFirstName;
        },
        uri:function () {
            return uri;
        },
        setUri: function (newUri) {
            uri = newUri;
        }
    };
});

Services.factory("Authenticated", function () {
    var isAuthenticated = true;
    return {
        isAuthenticated:function () {
            return isAuthenticated;
        },
        setAuthenticated:function (authenticated) {
            isAuthenticated = authenticated;
        }
    };
});