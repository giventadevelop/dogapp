(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('UserDogDeleteController',UserDogDeleteController);

    UserDogDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserDog'];

    function UserDogDeleteController($uibModalInstance, entity, UserDog) {
        var vm = this;

        vm.userDog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserDog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
