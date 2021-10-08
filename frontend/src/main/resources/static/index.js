angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:12211/products';

    $scope.fillTable = function(pageIndex = 1) {
        $http({
            url: contextPath,
            method: 'GET'
        }).then(function (response) {
            $scope.Products = response.data;
        });
    };

    $scope.fillTable();
});