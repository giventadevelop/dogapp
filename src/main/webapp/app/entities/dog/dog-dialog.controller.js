(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogDialogController', DogDialogController);

    DogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Dog', 'DogBreed'];

    function DogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Dog, DogBreed) {
        var vm = this;

        vm.dog = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.hostName = location;
        vm.URLObject = parseURL(location);
        vm.save = save;
        vm.dogbreeds = DogBreed.query({filter: 'dog-is-null'});
        $q.all([vm.dog.$promise, vm.dogbreeds.$promise]).then(function() {
            if (!vm.dog.dogbreedId) {
                return $q.reject();
            }
            return DogBreed.get({id : vm.dog.dogbreedId}).$promise;
        }).then(function(dogbreed) {
            vm.dogbreeds.push(dogbreed);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dog.id !== null) {
                Dog.update(vm.dog, onSaveSuccess, onSaveError);
            } else {
                Dog.save(vm.dog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dogappApp:dogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
        
        
        function parseURL(url) {
            var parser = document.createElement('a'),
                searchObject = {},
                queries, split, i;
            // Let the browser do the work
            parser.href = url;
            // Convert query string to object
            queries = parser.search.replace(/^\?/, '').split('&');
            for( i = 0; i < queries.length; i++ ) {
                split = queries[i].split('=');
                searchObject[split[0]] = split[1];
            }
            return {
                protocol: parser.protocol,
                host: parser.host,
                entityHref: parser.href,
                hostname: parser.hostname,
                port: parser.port,
                pathname: parser.pathname,
                search: parser.search,
                searchObject: searchObject,
                hash: parser.hash
            };
        }
        


        vm.setDogPicture = function ($file, dog) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        dog.dogPicture = base64Data;
                        dog.dogPictureContentType = $file.type;
                    });
                });
            }
        };

    }
})();
