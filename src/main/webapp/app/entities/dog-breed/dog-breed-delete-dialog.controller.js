(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogBreedDeleteController',DogBreedDeleteController);

    DogBreedDeleteController.$inject = ['$uibModalInstance', 'entity', 'DogBreed'];

    function DogBreedDeleteController($uibModalInstance, entity, DogBreed) {
        var vm = this;

        vm.dogBreed = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DogBreed.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
