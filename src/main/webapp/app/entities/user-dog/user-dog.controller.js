(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('UserDogController', UserDogController);

    UserDogController.$inject = ['UserDog'];

    function UserDogController(UserDog) {

        var vm = this;

        vm.userDogs = [];

        loadAll();

        function loadAll() {
            UserDog.query(function(result) {
                vm.userDogs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
