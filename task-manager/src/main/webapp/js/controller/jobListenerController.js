 //控制层 
app.controller('jobListenerController' ,function($scope,$controller ,jobListenerService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
        jobListenerService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	

	
	//查询实体 
	$scope.findOne=function(id){				
		jobService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);
	}


	//转换一个到编辑
    $scope.toEdit = function(updateEntity){
	    var editEntity = JSON.parse(JSON.stringify(updateEntity));
        $scope.entity = editEntity;
    }
	
	//定时任务没有主键的,所以这个不管增加还是修改都是用保存的即可,在后端判断是否存在这个key
	$scope.save=function(){				
		var serviceObject;//服务层对象
        serviceObject=jobListenerService.addEmail($scope.entity );//增加
		serviceObject.success(
			function(response){
				$scope.reloadList();//重新加载
			}		
		).error(function(){
            alert("操作失败 !");
        });
	};



	$scope.entity = {
        "jobClassName": "com.snow.learn.umtask.job.SyncCoreTodoList",
        "jobGroup": "DEFAULT",
        "listenerName": "",
        "recivePersons": {
            "success": [
                {
                    "emailAddress": "1547756164@qq.com",
                    "personName": "snow",
                    "userid": 1
                }
            ],
            "error": [
                {
                    "emailAddress": "1547756164@qq.com",
                    "personName": "snow",
                    "userid": 1
                }
            ]
        },
		"sendPerson":{
            "emailAddress": "1547756164@qq.com",
            "personName": "snow",
            "userid": 1
		 }
    };

/*
	$scope.entity2 = {
        "jobClassName": "com.snow.learn.umtask.job.SyncITILTodoList",
        "jobGroup": "FINISH_GROUP",
        "listenerName": "",
        "recivePersons": {
            "success": [
                {
                    "emailAddress": "1547756164@qq.com",
                    "personName": "snow",
                    "userid": 1
                }
            ],
            "error": [
                {
                    "emailAddress": "1547756164@qq.com",
                    "personName": "snow",
                    "userid": 1
                }
            ]
        }
    };
*/

	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		jobService.dele( $scope.selectIds ).success(
			function(response){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
			}		
		).error(function(){
            alert("操作失败 !");
        });
	}
	
	$scope.searchEntity={};//定义搜索对象
});	
