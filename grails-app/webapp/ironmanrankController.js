var app = angular.module('myapp', ['ngMaterial']);
      app.controller('leaderboardcontroller',DemoCtrl);
  var obj=[];
  var obj1=[];
     function ccontries() {
    var fad1= new XMLHttpRequest();
                fad1.open('GET',"http://localhost:8080/result_pool/countries",true);
               
             fad1.send();                     
                fad1.addEventListener("readystatechange", processRequest1, true);
                fad1.onreadystatechange= processRequest1;
                function processRequest1(e) {
                    if(fad1.readyState == 4 && fad1.status == 200) {
                     var response1= JSON.parse(fad1.responseText);
                     for(var i = 0; i < response1.length&&obj1.length<response1.length; i++) {
                      obj1.push(response1[i]['name']).toString;
                      }           
                    }  
                }
              return obj1;
    }  
           function DemoCtrl ($timeout, $q, $log,$scope,$rootScope) {
    var self=this;
    $rootScope.racelist={};   
    $scope.Country="India";
  $scope.init=function($rootScope){
var fad= new XMLHttpRequest();
                fad.open('GET',"http://localhost:8080/result_pool/index",true);
              
             /*  In case of other header requirements in request
               fad.setRequestHeader('Access-Control-Allow-Origin','*');
                fad.setRequestHeader('Access-Control-Allow-Methods','POST, PUT, GET, OPTIONS, PATCH');
                fad.setRequestHeader('Access-Control-Allow-Credentials','true');
                fad.setRequestHeader('Content-Type','application/json');
                fad.setRequestHeader('Access-Control-Allow-Headers','Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers')
               */    
                fad.send();
                fad.addEventListener("readystatechange", processRequest, true);
                fad.onreadystatechange= processRequest;
                function processRequest(e) {
                    if(fad.readyState == 4 && fad.status == 200) {
                     var response= JSON.parse(fad.responseText);
                     $rootScope.racelist=response;
                     console.log($rootScope.racelist);
                    }  
                } 
           };
           $scope.getRes= function()
            {   
               var fad= new XMLHttpRequest();
                fad.open('GET',"http://localhost:8080/result_pool/giveresult",true);          
                fad.send();
                fad.addEventListener("readystatechange", processRequest, true);
                fad.onreadystatechange= processRequest;
                function processRequest(e) {
                    if(fad.readyState == 4 && fad.status == 200) {
                     var response= JSON.parse(fad.responseText);
                     for(var i=0;i<response.length;i++)
                     {
                      var genli=response[i].gender;
                      if(genli==0)
                      {
                        response[i].gender="M";
                      }
                      else
                        response[i].gender="F";
                     }
                     
                     $rootScope.resultlist=response;
                    }  
                    $rootScope.$apply();
                }   
            };    
            $scope.init($rootScope);         
    $scope.contries=[];
   $scope.contries=ccontries();
   
         $scope.groups={
          1:"18 - 24",
          2:"25 - 29",
          3:"30 - 34",
          4:"35 - 39",
          5:"40 - 44",
          6:"45 - 49",
          7:"50 - 54",
          8:"55 - 59",
          9:"60 - 64",
          10:"65 - 69",
          11:"70 - 74",
          12:"75 - 79",
          13:"80+",
                   };
     
         $scope.gender='';
         $scope.genders={"M":"Male","F":"Female"};
         $scope.contery=[];
         console.log($scope.contries);
         console.log($scope.races1);
     $scope.years=[2019];
    self.simulateQuery = false;
    self.isDisabled    = false;

    // list of `state` value/display objects
    self.events       = loadAll();
    $scope.eventss=self.events;
    self.querySearch   = querySearch;
    self.selectedItemChange = selectedItemChange;
    self.searchTextChange   = searchTextChange;

    self.newState = newState;

    function newState(event) {
      alert("Sorry! You'll need to create an Ironman event for " + event + " first!");
    }
    function querySearch (query) {
      var results = query ? self.events.filter(createFilterFor(query)) : self.events,
          deferred;
      if (self.simulateQuery) {
        deferred = $q.defer();
        $timeout(function () { deferred.resolve(results); }, Math.random() * 1000, false);
        return deferred.promise;
      } else {
        return results;
      }
    }

    function searchTextChange(text) {
      $log.info('Text changed to ' + text);
    }

    function selectedItemChange(item) {
      $log.info('Item changed to ' + JSON.stringify(item));
      if(item!=null)
      console.log(item.value);
    }
    function loadAll() {
       var allEvents='Ironman 70.3 Goa ';
       var faj='';
      for(var key in $rootScope.racelist)
      {
        if($rootScope.racelist.hasOwnProperty(key)){

          console.log($rootScope.racelist[key]);
        }
      }
       
      
      return allEvents.split(/, +/g).map(function (event) {
        return {
          value: event.toLowerCase(),
          display: event.toUpperCase()
        };
      });
    }
    function createFilterFor(query) {
      var lowercaseQuery = query.toLowerCase();

      return function filterFn(state) {
        return (state.value.indexOf(lowercaseQuery) === 0);
      };

    }
  }


  

