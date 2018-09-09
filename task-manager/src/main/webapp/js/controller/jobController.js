 //控制层 
 app.controller('jobController', function ($scope, $controller, jobService,jobListenerService) {

     $controller('baseController', {$scope: $scope});//继承

     //读取列表数据绑定到表单中
     $scope.findAll = function () {
         jobService.findAll().success(
             function (response) {
                 var jobGroupsMap = response.groups;
                 console.log(JSON.stringify(jobGroupsMap["DEFAULT"]));
                 $scope.list = [];
                 //遍历MAP集合
                 for (var key in jobGroupsMap) {
                     console.log("分组：" + key + "组员：" + jobGroupsMap[key]);
                     for (var i = 0; i < jobGroupsMap[key].length; i++) {
                         $scope.list.push(jobGroupsMap[key][i]);
                     }
                 }
             }
         );
     }






     //查询实体
     $scope.findOne = function (id) {
         jobService.findOne(id).success(
             function (response) {
                 $scope.entity = response;
             }
         );
     }


     //转换一个
     $scope.toEdit = function (updateEntity) {
         var editEntity = JSON.parse(JSON.stringify(updateEntity));
         $scope.entity = editEntity;
     }

     //定时任务没有主键的,所以这个不管增加还是修改都是用保存的即可,在后端判断是否存在这个key
     $scope.save = function () {
         var serviceObject;//服务层对象
         serviceObject = jobService.add($scope.entity);//增加
         serviceObject.success(
             function (response) {
                 $scope.reloadList();//重新加载
             }
         ).error(function () {
             alert("操作失败 !");
         });
     }


     $scope.pause = function (updateEntity) {
         var serviceObject = jobService.pause(updateEntity.jobClassName, updateEntity.jobGroup);//增加
         serviceObject.success(
             function (response) {
                 $scope.reloadList();//重新加载
             }
         ).error(function () {
             alert("操作失败 !");
         });
     }

     $scope.show = function (entity) {
         $scope.entity = entity;
         $("#show-view").click();
     };

     $scope.addMailListenerEntity ={
         "jobClassName": "",
         "jobGroup": "",
         "listenerName": "",
         "recivePersons": {
             "success": [

             ],
             "error": [

             ]
         },
         "sendPerson":{
             "emailAddress": "1547756164@qq.com",
             "personName": "snow"
         }
     };


     $scope.emptyEmailPerson= {
         "emailAddress": "",
         "personName": ""
     }

     $scope.addMailListener = function (){
         if ($scope.selectIds.length > 1) {
             alert("只能选择一条操作")
             return ;
         }
         $scope.addMailListenerEntity.jobClassName =    $scope.selectIds[0].jobClassName;
         $scope.addMailListenerEntity.jobGroup =    $scope.selectIds[0].jobGroup;
         $("#add-email-listener").click();
     };


     $scope.addSuccessPerson = function(){
         $scope.addMailListenerEntity.recivePersons.success.push(JSON.parse(JSON.stringify($scope.emptyEmailPerson)));
     }


     //删除规格选项行
     $scope.deleteSuccessPerson = function (index) {
         $scope.addMailListenerEntity.recivePersons.success.splice(index,1);
     };

     // 增加异常收件人
     $scope.addErrorPerson = function(){
         $scope.addMailListenerEntity.recivePersons.error.push(JSON.parse(JSON.stringify($scope.emptyEmailPerson)));
     };

     //删除异常收件人
     $scope.deleteErrorPerson = function (index) {
         $scope.addMailListenerEntity.recivePersons.error.splice(index,1);
     };



     //定时任务没有主键的,所以这个不管增加还是修改都是用保存的即可,在后端判断是否存在这个key
     $scope.saveEmailListener = function(){
         var serviceObject;//服务层对象
         serviceObject=jobListenerService.addEmail($scope.addMailListenerEntity );//增加
         serviceObject.success(
             function(response){
                 $scope.reloadList();//重新加载
             }
         ).error(function(){
             alert("操作失败 !");
         });
     };


     $scope.stateName = {
         "NONE":"无",
         "NORMAL":"正常状态",
         "PAUSED":"暂停状态",
         "COMPLETE":"完成",
         "BLOCKED":"堵塞",
         "ERROR":"错误"
     };

     $scope.getState = function(key){
         return $scope.stateName[key];
     }


     $scope.resume = function (updateEntity) {
         var serviceObject = jobService.resume(updateEntity.jobClassName, updateEntity.jobGroup);//增加
         serviceObject.success(
             function (response) {
                 $scope.reloadList();//重新加载
             }
         ).error(function () {
             alert("操作失败 !");
         });
     }


     $scope.start = function (updateEntity) {
         var serviceObject = jobService.start(updateEntity.jobClassName, updateEntity.jobGroup);//增加
         serviceObject.success(
             function (response) {
                 $scope.reloadList();//重新加载
             }
         ).error(function () {
             alert("操作失败 !");
         });
     }




     //批量删除
     $scope.dele = function () {
         if ($scope.selectIds.length > 1) {
             alert("只能选择一条操作")
             return ;
         }

         //获取选中的复选框
         jobService.dele($scope.selectIds[0].jobClassName,$scope.selectIds[0].jobGroup).success(
             function (response) {
                 $scope.reloadList();//刷新列表
                 $scope.selectIds = [];
             }
         ).error(function () {
             alert("操作失败 !");
         });
     }

     $scope.searchEntity = {};//定义搜索对象



     // 监控 允许发送正常邮件 按钮变化
     $scope.$watch('addMailListenerEntity.allowSendNormalEmail', function (newValue, oldValue) {
         if(newValue){
             $("#reciveNormalEmailPerson").show();
         }else{
             $("#reciveNormalEmailPerson").hide();
         }
     });


     // 监控 允许发送异常邮件 按钮变化
     $scope.$watch('addMailListenerEntity.allowSendExceptionEmail', function (newValue, oldValue) {
         if(newValue){
             $("#reciveExceptionEmailPerson").show();
         }else{
             $("#reciveExceptionEmailPerson").hide();
         }
     });
 });
