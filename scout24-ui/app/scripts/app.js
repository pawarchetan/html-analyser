'use strict';

/**
 * @ngdoc overview
 * @name scout24UiApp
 * @description
 * # scout24UiApp
 *
 * Main module of the application.
 */
angular
  .module('scout24UiApp', [
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .otherwise({
        redirectTo: '/'
      });
  }).constant("constant", {
    'JSONCONTENTTYPE' : {
      headers : {
        'Content-Type' : 'application/json;'
      }
    }
  });
