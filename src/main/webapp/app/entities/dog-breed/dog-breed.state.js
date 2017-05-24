(function() {
    'use strict';

    angular
        .module('dogappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dog-breed', {
            parent: 'entity',
            url: '/dog-breed',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dogappApp.dogBreed.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dog-breed/dog-breeds.html',
                    controller: 'DogBreedController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dogBreed');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dog-breed-detail', {
            parent: 'dog-breed',
            url: '/dog-breed/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dogappApp.dogBreed.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dog-breed/dog-breed-detail.html',
                    controller: 'DogBreedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dogBreed');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DogBreed', function($stateParams, DogBreed) {
                    return DogBreed.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dog-breed',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dog-breed-detail.edit', {
            parent: 'dog-breed-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog-breed/dog-breed-dialog.html',
                    controller: 'DogBreedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DogBreed', function(DogBreed) {
                            return DogBreed.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dog-breed.new', {
            parent: 'dog-breed',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog-breed/dog-breed-dialog.html',
                    controller: 'DogBreedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                breedName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dog-breed', null, { reload: 'dog-breed' });
                }, function() {
                    $state.go('dog-breed');
                });
            }]
        })
        .state('dog-breed.edit', {
            parent: 'dog-breed',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog-breed/dog-breed-dialog.html',
                    controller: 'DogBreedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DogBreed', function(DogBreed) {
                            return DogBreed.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dog-breed', null, { reload: 'dog-breed' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dog-breed.delete', {
            parent: 'dog-breed',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dog-breed/dog-breed-delete-dialog.html',
                    controller: 'DogBreedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DogBreed', function(DogBreed) {
                            return DogBreed.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dog-breed', null, { reload: 'dog-breed' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
