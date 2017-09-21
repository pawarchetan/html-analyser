'use strict';

/**
 * @ngdoc function
 * @name scout24UiApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the scout24UiApp
 */
angular.module('scout24UiApp')
  .controller('MainCtrl', function ($http, $log, $scope, constant) {
      $scope.errors = {};

      $scope.submit = function(uri) {
        $scope.webDocument = {};
        $scope.webDocument.uri = uri;

        $http.post('http://localhost:8080/api/analyser',$scope.webDocument, constant.JSONCONTENTTYPE).then(
          function(response) {
            $scope.errors = {};
            $scope.webDocument = response.data;
        		console.log(response.data);
          },
          function(data) {

          }, function(value) {

          })
        }

  });
