'use strict';

var scalaChat = angular.module("scalaChat", [])

scalaChat.filter('reverse', function() {
  return function(items) {
    return items.slice().reverse();
  };
});
