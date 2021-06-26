/**
 * 角色管理功能
 */
layui.define([ 'table', 'form' ], function(exports) {
					
	var $ = layui.$, setter = layui.setter, admin = layui.admin, table = layui.table, form = layui.form;
	
	//角色管理
	table.render({
		elem 		: '#LAY-role-list',
		url 		: setter.prefix + 'authz/role/list', // 数据查询接口
		contentType	: "application/json",
		page		: true,
	    height		: 'full-220',
	    limit		: 30,
	    method		: 'POST', //如果无需自定义HTTP类型，可不加该参数
	    request		: {
	    	pageName : 'pageNo', //页码的参数名称，默认：page
	    	limitName: 'limit' //每页数据量的参数名，默认：limit
	    },
	    response	: {
	    	countName: 'total', //规定数据总数的字段名称，默认：count
	    	dataName: 'rows', //规定数据列表的字段名称，默认：data
	        statusName: 'code', //规定数据状态的字段名称，默认：code
	        statusCode: 0, //规定成功的状态码，默认：0
	        msgName: 'message' //规定状态信息的字段名称，默认：msg
	    }, 
	    cols 		: [ [ 
			{ type : 'checkbox', fixed : 'left' },  
			{ field: 'name', title : '角色名', minWidth : 100 }, 
			{ field: 'type', title : '类型', width: 80, align : 'left', templet: '#typeTpl' }, 
			{ field: 'users', width: 80, title: '用户数', align : 'center'},
			{ field: 'time24', width: 170, align : 'center', title: '创建时间'},
	        { field: 'status', title : '角色状态', unresize: true, minWidth : 80, align : 'center', templet: '#switchTpl'}, 
			{ title: '操作', minWidth : 150, align : 'center', fixed : 'right', toolbar : '#table-opt-list'}
		]],
	    skin	: 'line'
	});
	
	//监听搜索
	form.on('submit(LAY-role-search)', function(data) {
		var field = data.field;
		//执行重载
		table.reload('LAY-role-list', {
			where : field
            , page: {
                curr: 1
            }
		});
	});
	
	//监听状态操作
	form.on('switch(statusKv)', function(obj){
		//layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
		var value = this.value, //得到修改后的值
			id = $(obj.elem).data("id");
	    // 提交更新
  		admin.req({
	        url			: setter.prefix + 'authz/role/status',
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
	table.on('tool(LAY-role-list)', function(obj){
	    var data = obj.data;
	    var id = data.id;
	    if(obj.event === 'detail'){
	    	//Ajax获取
	    	$.get(setter.prefix + 'authz/role/ui/detail/' + id, {}, function(str){
	    		layer.open({
	    			area: ['700px', '600px'],
	    			content: str, //注意，如果str是object，那么需要字符拼接。
	    			title: '角色详情',
	    			type: 1
	    		});
	    	});
	    } else if(obj.event === 'renew'){
	    	layer.open({ 
    			type	: 2,
    			title	: '编辑角色',
    			content	: setter.prefix + 'authz/role/ui/renew/' + id,
    			area	: ['700px', '600px'],
    			btn		: ['确定', '取消'],
    			yes		: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-role-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	field["perms"] = field["perms"].split(",");
		            	for(var key in field ){
		            		if(key.split("permsTemp[").length > 1){
		            			delete field[key];
		            		}
		            	}
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'authz/role/renew',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["msg"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-role-list'); //刷新表格
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
	    } else if(obj.event === 'delete'){
	    	layer.confirm('确定删除选中的角色吗？', function(index){
		      	admin.req({
		  	        url			: setter.prefix + 'authz/role/delete/' + id,
		  	        type 		: "get",
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["msg"]||"", {
		  	                   	icon: 1
		  	            	});
		  	        		table.reload('LAY-role-list'); //刷新表格
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
    	add: function(){
    		layer.open({ 
    			type	: 2,
    			title	: '添加角色',
    			content	: setter.prefix + 'authz/role/ui/new',
    			area	: ['600px', '500px'],
    			btn		: ['确定', '取消'],
    			yes		: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-role-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	field["perms"] = field["perms"].split(",");
		            	for(var key in field ){
		            		if(key.split("permsTemp[").length > 1){
		            			delete field[key];
		            		}
		            	}
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'authz/role/new',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["msg"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-role-list'); //刷新表格
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

	exports('role', {})
});