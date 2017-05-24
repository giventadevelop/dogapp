(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogBreedController', DogBreedController);

    DogBreedController.$inject = ['DogBreed'];

    function DogBreedController(DogBreed) {

        var vm = this;

        vm.dogBreeds = [];

        loadAll();

        function loadAll() {
            DogBreed.query(function(result) {
                vm.dogBreeds = result;
                vm.searchQuery = null;
            });
        }
    }
})();
