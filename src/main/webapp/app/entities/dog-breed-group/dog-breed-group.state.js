(function() {
    'use strict';

    angular
        .module('dogappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dogs-grouped-by-breed', {
            parent: 'entity',
            url: '/dogs-grouped-by-breed?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dogappApp.dog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dog-breed-group/dog-breed-group.html',
                    controller: 'DogBreedGroupByController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
       /* .state('dog-detail', {
            parent: 'dog',
            url: '/dog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dogappApp.dog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dog/dog-detail.html',
                    controller: 'DogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Dog', function($stateParams, Dog) {
                    return Dog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dog',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dog-detail.edit', {
            parent: 'dog-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog/dog-dialog.html',
                    controller: 'DogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dog', function(Dog) {
                            return Dog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dog.new', {
            parent: 'dog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog/dog-dialog.html',
                    controller: 'DogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dogName: null,
                                votes: null,
                                dogPicture: null,
                                dogPictureContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dog', null, { reload: 'dog' });
                }, function() {
                    $state.go('dog');
                });
            }]
        })
        .state('dog.edit', {
            parent: 'dog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog/dog-dialog.html',
                    controller: 'DogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dog', function(Dog) {
                            return Dog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dog', null, { reload: 'dog' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dog.delete', {
            parent: 'dog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog/dog-delete-dialog.html',
                    controller: 'DogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dog', function(Dog) {
                            return Dog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dog', null, { reload: 'dog' });
                }, function() {
                    $state.go('^');
                });
            }]
        });*/
    }

})();
