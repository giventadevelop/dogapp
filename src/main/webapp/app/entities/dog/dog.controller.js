(function() {
    'use strict';

    angular
        .module('dogappApp')
        .controller('DogController', DogController);

    DogController.$inject = ['$scope','$timeout', '$state', 'DataUtils', 'Dog', 'UserDog', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function DogController($scope,$timeout,$state, DataUtils, Dog, UserDog, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
          $scope.loadingSpinner=false;
       // $scope.loadingSpinner = true;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll () {
            Dog.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.dogs = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        $scope.saveUserDog=function(dogIdVal,loggedUserNameVal) {
        	$scope.loadingSpinner=true;
        	console.log('dogId is'+dogIdVal);
        	var userDog = {
        			dogId:dogIdVal ,
        			username: loggedUserNameVal,
        			
        		}
        	
        	
			$timeout(callAtTimeout(userDog), 5000);
        	
        	  //time
           
        	 $scope.test1 = "test1";
        	 vm.test1 = "test1";
        	vm.selectedDogId='dogId_'+dogIdVal;
        	//angular.element(document.getElementById(vm.selectedDogId)).disabled = true;
        	angular.element(document.getElementById( vm.selectedDogId))[0].disabled = true;
          /*  vm.userDog.dogId=dogId;
            vm.userDog.username=loggedUserName;
            if (vm.userDog.id !== null) {
                UserDog.update(userDog, onUserDogSaveSuccess, onUserDogSaveError);
            } else {
                UserDog.save(userDogg, onUserDogSaveSuccess, onUserDogSaveError);
            }*/
            
          
            
        }

        function onUserDogSaveSuccess (result) {
        	//$timeout(callAtTimeout, 15000);
        	console.log("inside onUserDogSaveSuccess");
        	$scope.loadingSpinner = false;
        
        }

        function onUserDogSaveError (error) {
        	$scope.loadingSpinner = false;
        	 console.log(error);
        	
   }
        
        function callAtTimeout(userDog) {
		    console.log("Timeout occurred");
		    UserDog.save(userDog, onUserDogSaveSuccess, onUserDogSaveError);
		}

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
