(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('UserDogDetailController', UserDogDetailController);

    UserDogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserDog', 'Dog'];

    function UserDogDetailController($scope, $rootScope, $stateParams, previousState, entity, UserDog, Dog) {
        var vm = this;

        vm.userDog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dogappApp:userDogUpdate', function(event, result) {
            vm.userDog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
