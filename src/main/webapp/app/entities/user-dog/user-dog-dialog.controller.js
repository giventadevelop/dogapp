(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('UserDogDialogController', UserDogDialogController);

    UserDogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserDog', 'Dog'];

    function UserDogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserDog, Dog) {
        var vm = this;

        vm.userDog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.dogs = Dog.query({filter: 'userdog-is-null'});
        $q.all([vm.userDog.$promise, vm.dogs.$promise]).then(function() {
            if (!vm.userDog.dogId) {
                return $q.reject();
            }
            return Dog.get({id : vm.userDog.dogId}).$promise;
        }).then(function(dog) {
            vm.dogs.push(dog);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userDog.id !== null) {
                UserDog.update(vm.userDog, onSaveSuccess, onSaveError);
            } else {
                UserDog.save(vm.userDog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dogappApp:userDogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
