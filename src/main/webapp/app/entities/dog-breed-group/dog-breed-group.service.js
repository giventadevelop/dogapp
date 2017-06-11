(function() {
    'use strict';
    angular
        .module('dogappApp')
        .factory('DogBreedGroupService', DogBreedGroupService);

    DogBreedGroupService.$inject = ['$resource'];

    function DogBreedGroupService ($resource) {
        var resourceUrl =  'api/dogs/breed/groupby/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
