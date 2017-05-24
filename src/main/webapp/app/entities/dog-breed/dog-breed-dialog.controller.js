(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogBreedDialogController', DogBreedDialogController);

    DogBreedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DogBreed'];

    function DogBreedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DogBreed) {
        var vm = this;

        vm.dogBreed = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dogBreed.id !== null) {
                DogBreed.update(vm.dogBreed, onSaveSuccess, onSaveError);
            } else {
                DogBreed.save(vm.dogBreed, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dogappApp:dogBreedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
