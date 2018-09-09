//服务层
app.service('jobListenerService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../job-listener/all-job-listener');
	}

	//查询实体
	this.findOne=function(id){
		return $http.get('../user/findOne.do?id='+id);
	}

	//增加 
	this.add=function(entity){
		return  $http.post('../job/add-execute-job-trigger',entity);
	}


    //增加
    this.addEmail =function(entity){
        return  $http.post('../job-listener/add-email-listener',entity);
    };


	//删除
	this.dele=function(ids){
		return $http.get('../user/delete.do?ids='+ids);
	}



	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../user/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
