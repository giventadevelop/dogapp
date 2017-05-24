(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogDetailController', DogDetailController);

    DogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Dog', 'DogBreed'];

    function DogDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Dog, DogBreed) {
        var vm = this;

        vm.dog = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('dogappApp:dogUpdate', function(event, result) {
            vm.dog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
