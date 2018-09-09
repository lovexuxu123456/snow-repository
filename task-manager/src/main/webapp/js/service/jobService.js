//服务层
app.service('jobService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../job/all-job');
	}

	//增加 
	this.add=function(entity){
		return  $http.post('../job/add-execute-job-trigger',entity);
	}




	//删除
	this.dele=function(jobClassName,jobGroup){
		//delete-job/{jobClassName:.+}/{jobGroup}
       return $http.delete('../job/delete-job/'+jobClassName+'/'+jobGroup+'');
		
//		return $http.get('../job/delete-job/'+jobClassName+'/'+jobGroup+'');
		
		
	}


    /**
     * 暂停定时任务
     * @param jobClassName
     * @param jobGroup
     */
    this.pause = function(jobClassName,jobGroup){
      return  $http.get('../job/pause-job/' + jobClassName  + '/' + jobGroup);
    }

    /**
     * 恢复
     * @param jobClassName
     * @param jobGroup
     */
    this.resume = function(jobClassName,jobGroup){
      return  $http.get('../job/resume-job/' + jobClassName  + '/' + jobGroup);
    }



    /**
     * 立即执行
     * @param jobClassName
     * @param jobGroup
     */
    this.start = function(jobClassName,jobGroup){
        return  $http.get('../job/start-job/' + jobClassName  + '/' + jobGroup);
    }


	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../user/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
