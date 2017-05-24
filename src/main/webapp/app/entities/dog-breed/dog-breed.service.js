(function() {
    'use strict';
    angular
        .module('dogappApp')
        .factory('DogBreed', DogBreed);

    DogBreed.$inject = ['$resource'];

    function DogBreed ($resource) {
        var resourceUrl =  'api/dog-breeds/:id';

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
