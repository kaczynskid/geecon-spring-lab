(function() {
  'use strict';

  var app = angular.module('application', [
    'ui.router',
    'ngAnimate',
    'ngResource',
    'checklist-model',
    'angularPayments',

    //foundation
    'foundation',
    'foundation.dynamicRouting',
    'foundation.dynamicRouting.animations'
  ]);

  app.config(config)
    .run(run);

  config.$inject = ['$urlRouterProvider', '$locationProvider'];
  function config($urlProvider, $locationProvider) {
    $urlProvider.otherwise('/');

    $locationProvider.html5Mode({
      enabled: false,
      requireBase: false
    });

    $locationProvider.hashPrefix('!');
  }

  function run() {
    FastClick.attach(document.body);
  }

  // Common

  app.filter('capitalize', function() {
    return function(txt) {
      return (!!txt) ? txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase() : '';
    }
  });

  app.filter('creditCardFormat', function() {
    return function(txt) {
      return (!!txt) ? txt.match(/..../g).join(' ') : '';
    }
  });

  // Customers

  app.factory('Customer', function($resource) {
    return $resource('/customer-service/customers/:id', { id: '@id' }, {
      update: {
        method: 'PUT'
      }
    });
  });

  app.controller('ListCustomersCtrl', function ($scope, Customer) {

    $scope.customers = Customer.query();
    console.log('Customers list loaded.');
  });

  app.controller('AddCustomerCtrl', function ($scope, $state, Customer) {

    $scope.customer = new Customer();
    $scope.customer.creditCards = [];

    $scope.addCustomer = function() {
      $scope.customer.$save(function() {
        console.log('Customer created.');
        $state.go('customers');
      })
    }
  });

  app.controller('EditCustomerCtrl', function ($scope, $state, $stateParams, Customer, Payback) {

    $scope.customer = Customer.get({ id: $stateParams.id });
    $scope.payback = Payback.summary({ customerId: $stateParams.id },
      function(paybackSummary) {
        $scope.paybackSummary = paybackSummary;
      },
      function() {
        $scope.paybackSummary = {
          amount: 0,
          merchants: []
        };
      });

    $scope.saveCustomer = function() {
      $scope.customer.$update(function() {
        console.log('Customer updated.');
        $state.go('customers');
      })
    };
  });

  // Merchants

  app.factory('Merchant', function($resource) {
    return $resource('/merchant-service/merchants/:id', { }, {
      page: {
        method: 'GET'
      },
      update: {
        method: 'PUT'
      }
    });
  });

  app.controller('ListMerchantsCtrl', function ($scope, Merchant) {

    $scope.page = Merchant.page();
    console.log('Merchants list loaded.');
  });

  app.controller('AddMerchantCtrl', function ($scope, $state, Merchant) {

    $scope.daysOfWeek = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
    $scope.merchant = new Merchant();

    $scope.addMerchant = function() {
      if ($scope.merchant.paybackPolicy.percentage) {
        $scope.merchant.paybackPolicy.percentage /= 100
      }
      $scope.merchant.$save(function() {
        console.log('Merchant created.');
        $state.go('merchants');
      }, function() {
        if ($scope.merchant.paybackPolicy.percentage) {
          $scope.merchant.paybackPolicy.percentage *= 100
        }
      })
    }
  });

  app.controller('EditMerchantCtrl', function ($scope, $state, $stateParams, Merchant) {

    $scope.daysOfWeek = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
    $scope.merchant = Merchant.get({ id: $stateParams.id }, function() {
      if ($scope.merchant.paybackPolicy.percentage) {
        $scope.merchant.paybackPolicy.percentage *= 100
      }
    });

    $scope.saveMerchant = function() {
      if ($scope.merchant.paybackPolicy.percentage) {
        $scope.merchant.paybackPolicy.percentage /= 100
      }
      $scope.merchant.$save(function() {
        console.log('Merchant updated.');
        $state.go('merchants');
      }, function() {
        if ($scope.merchant.paybackPolicy.percentage) {
          $scope.merchant.paybackPolicy.percentage *= 100
        }
      })
    }
  });

  // Paybacks

  app.factory('Payback', function($resource) {
    return $resource('/payback-service/paybacks/:id', { id: '@id' }, {
      summary: {
        url: '/payback-service/paybacks/summary/:customerId',
        method: 'GET'
      }
    });
  });

  app.controller('AddPaybackCtrl', function ($scope, $state, $stateParams, Customer, Merchant, Payback) {

    $scope.customer = Customer.get({ id: $stateParams.id });
    $scope.merchants = Merchant.page();
    $scope.payback = new Payback();

    $scope.addPayback = function() {
      $scope.payback.$save(function() {
        console.log('Payback added.');
        $state.go('edit-customer', {id: $scope.customer.id });
      });
    }
  });

})();
