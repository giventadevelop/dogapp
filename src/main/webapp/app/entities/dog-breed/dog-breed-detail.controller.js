(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogBreedDetailController', DogBreedDetailController);

    DogBreedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DogBreed'];

    function DogBreedDetailController($scope, $rootScope, $stateParams, previousState, entity, DogBreed) {
        var vm = this;

        vm.dogBreed = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dogappApp:dogBreedUpdate', function(event, result) {
            vm.dogBreed = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
