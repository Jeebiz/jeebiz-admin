/**
 * 基础数据功能
 */
layui.define([ 'table', 'form' ], function(exports) {
					
	var $ = layui.$, setter = layui.setter, admin = layui.admin, table = layui.table, form = layui.form;

	// 文章管理
	table.render({
		elem 	: '#LAY-datasource-list',
		url 	: setter.prefix + 'extras/datasource/list', // 数据源信息查询接口
		contentType	: "application/json",
		page	: true,
	    method: 'POST', //如果无需自定义HTTP类型，可不加该参数
	    request: {
	    	pageName: 'pageNo', //页码的参数名称，默认：page
	    	limitName: 'limit' //每页数据量的参数名，默认：limit
	    },
	    response: {
	    	countName: 'total', //规定数据总数的字段名称，默认：count
	    	dataName: 'rows', //规定数据列表的字段名称，默认：data
	        statusName: 'code', //规定数据状态的字段名称，默认：code
	        statusCode: 0, //规定成功的状态码，默认：0
	        msgName: 'message' //规定状态信息的字段名称，默认：msg
	    }, 
		cols 	: [ [ {
			type : 'checkbox',
			fixed : 'left'
		}, {
			field : 'name',
			title : '数据源标题'
		}, {
			field : 'dbtype',
			title : '数据源类型',
			templet: function(d){
				var dbtype = d.dbtype;
				$("#LAY-dbtype option").each(function(i,e){
					if($(this).val() == d.dbtype){
						dbtype = $(this).text();
						//console.log("dbtype:" + dbtype);
						return false;
					}
				});
				return dbtype;
			}, 
		}, {
			field : 'time24',
			title : '注册时间',
			width : 170,
			edit  : 'text'
		}, {
			field : 'status',
			title : '数据源状态',
			templet: '#switchTpl', 
			unresize: true,
			width : 100,
			align : 'center'
		}, {
			title : '操作',
			minWidth : 80,
			align : 'center',
			fixed : 'right',
			toolbar : '#table-opt-list'
		} ] ],
	    skin: 'line'
	});
	
	//监听搜索
	form.on('submit(LAY-datasource-search)', function(data) {
		var field = data.field;
		//执行重载
		table.reload('LAY-datasource-list', {
			where : field
            , page: {
                curr: 1
            }
		});
	});
	
	//监听状态操作
	form.on('switch(statusDs)', function(obj){
		//layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
		var value = this.value, //得到修改后的值
			id = $(obj.elem).data("id");
	    // 提交更新
  		admin.req({
	        url			: setter.prefix + 'extras/datasource/status',
	        type 		: "post",
	        data		: {
	        	"id" 	: id,
   				"status": obj.elem.checked ? "1" : "0"
			},
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["msg"]||"", {
	                   	icon: 1
	            	});
	        	} else {
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
	    });
	});
	
	//监听行工具事件
	table.on('tool(LAY-datasource-list)', function(obj){
	    var data = obj.data;
	    var id = data.id;
	    if(obj.event === 'detail'){
	    	//Ajax获取
	    	$.get(setter.prefix + 'extras/datasource/ui/detail/' + id, {}, function(str){
	    		layer.open({
	    			area: ['700px', '600px'],
	    			content: str, //注意，如果str是object，那么需要字符拼接。
	    			title: '数据源详情',
	    			type: 1
	    		});
	    	});
	    } else if(obj.event === 'edit'){
	    	layer.open({ 
    			type: 2,
    			title: '编辑数据源',
    			content: setter.prefix + 'extras/datasource/ui/edit/' + id,
    			area: ['700px', '600px'],
    			btn: ['确定', '取消'],
    			yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-datasource-forum-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'extras/datasource/edit',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["msg"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-datasource-list'); //刷新表格
		    		              	layer.close(index); //关闭弹层
		        	        	} else {
		        	        		layer.msg(res["msg"]||"", {
		        	                   	icon: 2
		        	            	});
		        	        	}
		        	        }
		        	    });
		            });  
		            
		            submit.trigger('click');
    			}
	        }); 
	    } else if(obj.event === 'del'){
	    	// obj.del();
	    	//layer.msg(JSON.stringify(obj));
	    	layer.confirm('确定删除当前的数据吗？', function(index){
		      	admin.req({
		  	        url			: setter.prefix + 'extras/datasource/delete',
		  	        type 		: "post",
		  	        data		: {"ids" : id},
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["msg"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        		table.reload('LAY-datasource-list'); //刷新表格
		  	        	} else {
		  	        		layer.msg(res["msg"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        	}
		  	        	layer.close(index);
		  	        }
		      	});
	        });
	    	 
	    }  
	});
	
	//事件
    var active = {
    	batchdel: function(){
    		var checkStatus = table.checkStatus('LAY-datasource-list'),
    			checkData = checkStatus.data; //得到选中的数据
	        if(checkData.length === 0){
	        	return layer.msg('请选择数据');
	        }
	        layer.confirm('确定删除选中的数据吗？', function(){
	      	  	var ids = [];
	      	  	for( i in checkData){
	      	  		ids.push(checkData[i].id);
	      	  	}
		      	admin.req({
		  	        url			: setter.prefix + 'extras/datasource/delete',
		  	        type 		: "post",
		  	        data		: {"ids" : ids.join(",")},
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["msg"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        		table.reload('LAY-datasource-list'); //刷新表格
		  	        	} else {
		  	        		layer.msg(res["msg"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        	}
		  	        }
		      	});
	        });
	        
    	},
    	add: function(){
    		layer.open({ 
    			type: 2,
    			title: '添加数据源',
    			content: setter.prefix + 'extras/datasource/ui/new',
    			area: ['700px', '600px'],
    			btn: ['确定', '取消'],
    			yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-datasource-forum-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'extras/datasource/new',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["msg"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-datasource-list'); //刷新表格
		    		              	layer.close(index); //关闭弹层
		        	        	} else {
		        	        		layer.msg(res["msg"]||"", {
		        	                   	icon: 2
		        	            	});
		        	        	}
		        	        }
		        	    });
		            });  
		            
		            submit.trigger('click');
    			}
	        }); 
    	}
    };
	
	$('.layui-btn.layuiadmin-btn-admin').on('click', function(){
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });

	exports('datasource', {})
});