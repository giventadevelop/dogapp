(function() {
    'use strict';
    angular
        .module('dogappApp')
        .factory('UserDog', UserDog);

    UserDog.$inject = ['$resource'];

    function UserDog ($resource) {
        var resourceUrl =  'api/user-dogs/:id';

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