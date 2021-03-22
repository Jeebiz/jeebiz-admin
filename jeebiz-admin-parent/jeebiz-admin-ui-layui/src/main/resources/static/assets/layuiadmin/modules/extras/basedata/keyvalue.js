/**
 * 基础数据功能
 */
layui.define([ 'table', 'form' ], function(exports) {
					
	var $ = layui.$, setter = layui.setter, admin = layui.admin, table = layui.table, form = layui.form;

	// 文章管理
	table.render({
		elem 	: '#LAY-keyvalue-list',
		url 	: setter.prefix + 'extras/basedata/keyvalue/list', // 数据查询接口
		contentType	: "application/json",
		page	: true,
	    method	: 'POST', //如果无需自定义HTTP类型，可不加该参数
	    request	: {
	    	pageName: 'pageNo', //页码的参数名称，默认：page
	    	limitName: 'limit' //每页数据量的参数名，默认：limit
	    },
	    response: {
	    	countName: 'total', //规定数据总数的字段名称，默认：count
	    	dataName: 'rows', //规定数据列表的字段名称，默认：data
	        statusName: 'code', //规定数据状态的字段名称，默认：code
	        statusCode: 200, //规定成功的状态码，默认：0
	        msgName: 'message' //规定状态信息的字段名称，默认：msg
	    }, 
		cols 	: [ [ 
			{ type : 'checkbox', fixed : 'left' },  
			{ field : 'group', title : '数据分组', minWidth : 100 }, 
			{ field : 'key', title : '数据键', width : 80 },
			{ field : 'value', title : '数据值（可编辑）', minWidth : 100 , edit  : 'text'}, 
			{ field : 'status',	title : '数据状态', templet: '#switchTpl', unresize: true, minWidth : 80, align : 'center' }, 
			{ field : 'order', title : '显示顺序', width : 100 }, 
			{ title : '操作', width : 80, align : 'center', fixed : 'right', toolbar : '#table-opt-list'} 
		]],
	    skin: 'line'
	});
	
	//监听搜索
	form.on('submit(LAY-keyvalue-search)', function(data) {
		var field = data.field;
		//执行重载
		table.reload('LAY-keyvalue-list', {
			where : field
		});
	});


	//监听单元格编辑
	table.on('edit(LAY-keyvalue-list)', function(obj){
		var value = obj.value, //得到修改后的值
			data = obj.data,   //得到所在行所有键值
			field = obj.field; //得到字段
		var rqData = {
        	"id" 	: data.id,
	   		"group" : data.group,
	   		"label" : field == "label" ? value : data.label,
	   		"key" 	: data.key,
	   		"value"	: field == "value" ? value : data.value,
			"status": data.status
		};
		//console.log(data);
		//console.log(rqData);
		// 提交更新
  		admin.req({
	        url			: setter.prefix + 'extras/basedata/keyvalue/update',
	        type 		: "POST",
	        contentType	: "application/json",
	        data		: JSON.stringify(rqData),
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["message"]||"", {
	                   	icon: 1
	            	});
	        	} else {
	        		layer.msg(res["message"]||"", {
	                   	icon: 2
	            	});
	        	}
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
	        url			: setter.prefix + 'extras/basedata/keyvalue/status',
	        type 		: "post",
	        data		: {
	        	"id" 	: id,
   				"status": obj.elem.checked ? "1" : "0"
			},
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["message"]||"", {
	                   	icon: 1
	            	});
	        	} else {
	        		layer.msg(res["message"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
	    });
	});
	
	//监听行工具事件
	table.on('tool(LAY-keyvalue-list)', function(obj){
	    var data = obj.data;
	    var id = data.id;
	    if(obj.event === 'del'){
	    	layer.confirm('确定删除当前的数据吗？', function(index){
		      	admin.req({
		  	        url			: setter.prefix + 'extras/basedata/keyvalue/delete',
		  	        type 		: "post",
		  	        data		: {"ids" : id},
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["message"]||"", {
		  	                   	icon: 1
		  	            	});
		  	        		table.reload('LAY-keyvalue-list'); //刷新表格
		  	        	} else {
		  	        		layer.msg(res["message"]||"", {
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
    			title	: '新建基础数据',
    			content	: setter.prefix + 'extras/basedata/keyvalue/ui/new',
    			area	: ['600px', '400px'],
    			btn		: ['确定', '取消'],
    			yes		: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-keyvalue-forum-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'extras/basedata/keyvalue/new',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-keyvalue-list'); //刷新表格
		    		              	layer.close(index); //关闭弹层
		        	        	} else {
		        	        		layer.msg(res["message"]||"", {
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
    }
	
	$('.layui-btn.layuiadmin-btn-admin').on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });

	exports('keyvalue', {})
});