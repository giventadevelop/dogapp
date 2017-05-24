(function() {
    'use strict';

    angular
        .module('dogappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-dog', {
            parent: 'entity',
            url: '/user-dog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dogappApp.userDog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-dog/user-dogs.html',
                    controller: 'UserDogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userDog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-dog-detail', {
            parent: 'user-dog',
            url: '/user-dog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dogappApp.userDog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-dog/user-dog-detail.html',
                    controller: 'UserDogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userDog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserDog', function($stateParams, UserDog) {
                    return UserDog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-dog',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-dog-detail.edit', {
            parent: 'user-dog-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dog/user-dog-dialog.html',
                    controller: 'UserDogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDog', function(UserDog) {
                            return UserDog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-dog.new', {
            parent: 'user-dog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dog/user-dog-dialog.html',
                    controller: 'UserDogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-dog', null, { reload: 'user-dog' });
                }, function() {
                    $state.go('user-dog');
                });
            }]
        })
        .state('user-dog.edit', {
            parent: 'user-dog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dog/user-dog-dialog.html',
                    controller: 'UserDogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserDog', function(UserDog) {
                            return UserDog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-dog', null, { reload: 'user-dog' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-dog.delete', {
            parent: 'user-dog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-dog/user-dog-delete-dialog.html',
                    controller: 'UserDogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserDog', function(UserDog) {
                            return UserDog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-dog', null, { reload: 'user-dog' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
