var app = angular.module('formSubmit', []);

app.controller('FormSubmitController', ['$scope', '$http', function ($scope, $http) {


// Get data 
        var response = $http.get('restpettycash/findall');
        response.success(function (data, status, headers, config) {
            $scope.list = data;
        });
        response.error(function (data, status, headers, config) {
            alert("Exception details: " + JSON.stringify({data: data}));
        });

// Submit Form
        $scope.submit = function () {

            var formData = {
                "detailHeader": $scope.detailHeader,
                "remarkHeader": $scope.remarkHeader
            };

            var response = $http.post('restpettycash/save', formData);
            response.success(function (data, status, headers, config) {
                $scope.list = data;
            });
            response.error(function (data, status, headers, config) {
                alert("Exception details: " + JSON.stringify({data: data}));
            });

        };
        
        $scope.delete = function (id){
            var response = $http.delete('restpettycash/delete-'+id);
            response.success(function (data, status, headers, config) {
                $scope.list = data;
            });
            response.error(function (data, status, headers, config) {
                alert("Exception details: " + JSON.stringify({data: data}));
            });
        };
    }]);
//
//app.controller('customersCtrl', function ($scope, $http) {
//var response = $http.get('restpettycash/findall');
//    response.success(function (data, status, headers, config) {
//        $scope.list = data;
//    });
//    response.error(function (data, status, headers, config) {
//        alert("Exception details: " + JSON.stringify({data: data}));
//    });
//});