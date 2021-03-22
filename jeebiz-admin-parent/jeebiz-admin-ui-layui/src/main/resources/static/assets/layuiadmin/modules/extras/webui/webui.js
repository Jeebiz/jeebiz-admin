/**
 * 自定义界面功能：用户自主创建标准增删改查功能、数据统计分析功能等
 */
layui.define([ 'laydate', 'element', 'table', 'form', 'jquery', 'treeGrid' ], function(exports) {
	
	var $ = layui.$, laytpl = layui.laytpl, laydate = layui.laydate, element = layui.element, treeGrid = layui.treeGrid, setter = layui.setter, 
		admin = layui.admin, table = layui.table, form = layui.form, layer=layui.layer;
	
	var editObj = null, tableId='LAY-webui-list';
	var ptable = treeGrid.render({
        id				: tableId,
        elem			: '#'+ tableId,
        url 			: setter.prefix + 'extras/webui/list2', // 数据查询接口
        contentType		: "application/json",
        idField			: 'id',//必須字段
        treeId			: 'id',//树形id字段名称
        treeUpId		: 'parent',//树形父id字段名称
        treeShowName	: 'name',//以树形式显示的字段
        heightRemove	: [".dHead", 10], //不计算的高度,表格设定的是固定高度，此项不生效
        height			: '100%',
        isFilter		: false,
        iconOpen		: false, //是否显示图标【默认显示】
        isOpenDefault	: true, //节点默认是展开还是折叠【默认展开】
        loading			: true,
        method			: 'POST', //如果无需自定义HTTP类型，可不加该参数
	    response		: {
	    	countName: 'total', //规定数据总数的字段名称，默认：count
	    	dataName: 'rows', //规定数据列表的字段名称，默认：data
	        statusName: 'code', //规定数据状态的字段名称，默认：code
	        statusCode: 200, //规定成功的状态码，默认：0
	        msgName: 'message' //规定状态信息的字段名称，默认：msg
	    }, 
        cols: [[
            {type :'numbers'},
            //{type:'radio'},
            //{type :'checkbox',sort:true},
            {field :'name', title: '界面名称', width:200, edit:'text', sort:true},
            {field : 'type', title : '界面类型', width : 100, unresize: true, templet: function(d){
    				var type = d.type;
    				$("#LAY-webuitype option").each(function(i,e){
    					if($(this).val() == d.type){
    						type = $(this).text();
    						return false;
    					}
    				});
    				return type;
    			}
    		},
    		{ field : 'time24', title : '创建时间', width : 170 , unresize: true}, 
    		{ field : 'editable', title : '编辑状态', width : 90, unresize: true, align : 'center', templet: '#switchTpl1'},  
    		{ field : 'release', title : '发布状态', width : 90, unresize: true, align : 'center', templet: '#switchTpl2'},
    		{ title : '操作', minWidth : 200, align : 'right', toolbar : '#table-opt-list'}
        ]],
        isPage			: false,
        parseData		: function (res) {//数据加载后回调
            return res;
        },
        onClickRow		: function (index, o) {
            console.log(index,o,"单击！");
        },
        onDblClickRow	: function (index, o) {
            console.log(index,o,"双击");
        }
    });
	
    function print() {
        console.log(treeGrid.cache[tableId]);
        var loadIndex=layer.msg("对象已打印，按F12，在控制台查看！", {
            time:3000
            ,offset: 'auto'//顶部
            ,shade: 0
        });
    }
    
    function openorclose() {
        var map=treeGrid.getDataMap(tableId);
        var o= map['102'];
        treeGrid.treeNodeOpen(tableId,o,!o[treeGrid.config.cols.isOpen]);
    }


    function openAll() {
        var treedata=treeGrid.getDataTreeList(tableId);
        treeGrid.treeOpenAll(tableId,!treedata[0][treeGrid.config.cols.isOpen]);
    }

    function getCheckData() {
        var checkStatus = treeGrid.checkStatus(tableId)
            ,data = checkStatus.data;
        layer.alert(JSON.stringify(data));
    }
    function radioStatus() {
        var data = treeGrid.radioStatus(tableId)
        layer.alert(JSON.stringify(data));
    }
    function getCheckLength() {
        var checkStatus = treeGrid.checkStatus(tableId)
            ,data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
    }

    function reload() {
        treeGrid.reload(tableId,{
            page:{
                curr:1
            }
        });
    }
    function query() {
        treeGrid.query(tableId,{
            where:{
                name:'sdfsdfsdf'
            }
        });
    }
    
    //监听Tab切换，以改变地址hash值
    element.on('tab(webui-tab)', function(){
    	if(this.getAttribute('lay-id') == '2'){
    		if( $("#LAY-webui-design").find("div.layui-card").size() == 0){
    			layer.msg('请选择要进行设计的功能界面！' );
    		}
    	} else {
    		$("#LAY-webui-design").html("");
    	}
    });
    
    //监听指定开关
	form.on('switch(status-switch)', function(data){
		$(this).attr("value", this.checked ? '1' : '0')
	});
    
	//监听搜索
	form.on('submit(LAY-webui-search)', function(data) {
		var field = data.field;
		treeGrid.reload('LAY-webui-list',{
            page:{
               curr : 1
            },
            where : field
        });
		//执行重载
		/*table.reload('LAY-webui-list', {
			where : field
		});*/
	});
	
	form.on('submit(LAY-webui-query-elems)', function(data) {
    	var formData = data.field;
    	layer.msg(JSON.stringify(formData));
    	// 同名元素检查
    	if($('#LAY-webui-form-query-items input[name='+formData["key"]+']').size() > 0){
    		layer.msg('已存在标签Name为"' +formData["key"]+ '"的元素！');
    		return;
    	}
    	var type = formData["type"];
    	// 判断是否使用日期组件
		var useDate = (["year","month","date","time","datetime"].indexOf(type) >= 0),
			needSource = (["checkbox","radio","select","switch"].indexOf(type) >= 0);
			// 日期组件元素类型是text [{"key":"1","value":"选项1"},{"key":"2","value":"选项2"}]
			formData["type"] =  useDate ? "text" : type;
			// 禁用状态获取不到数据问题
			formData["status"] =  formData["status"] || 0;
			formData["classname"] =  formData["classname"] || "";
			formData["style"] =  formData["style"] || "";
		// 日期格式
		var formats = {
			"year"		: "yyyy",
			"month"		: "yyyy-MM",
			"date"		: "yyyy-MM-dd",
			"time"		: "HH:mm:ss",
			"datetime"	: "yyyy-MM-dd HH:mm:ss"
		};
		if(useDate){
			formData["placeholder"] = formData["placeholder"] || formats[type];
		}
		if(needSource){
			if(!formData["source"]){
				layer.msg("当前使用的元素类型需要渲染数据，请完善数据来源!");
				return;
			}
			try {
				formData["sourcestr"] = formData["source"];
				// 需要把字符串转为JSON对象
				formData["source"] = JSON.parse(formData["source"]);
			} catch (e) {
				layer.msg('数据来源格式错误！格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]');
			}
		}
		// 判断样式
		formData["itemClass"] = (["checkbox","radio","textarea"].indexOf(type) >= 0) ? "layui-input-block" : "layui-input-inline";
		// 渲染lay模板
    	laytpl($("#layui-inline-tpl").html()).render(formData, function(html){
    		
    		var queryForm = $("#LAY-webui-form-query-items");
    		if(queryForm.find('div.layui-inline').size() == 0){
    			queryForm.html(html);
    			form.render();
    		} else {
    			queryForm.append(html);
    			form.render();
    		}
    		// laydate组件
    		if(useDate){
    			laydate.render({
    				format	: formats[type],
        		    elem	: '#LAY-webui-form-query-items input[name='+formData["key"]+']',
        		    type	: type
        		});
    		};
    		
		});
    });
	
	form.on('submit(LAY-webui-form-elems)', function(data) {
    	var formData = data.field;
    	layer.msg(JSON.stringify(formData));
    	// 同名元素检查
    	if($('#LAY-webui-elems-form input[name='+formData["key"]+']').size() > 0){
    		layer.msg('已存在标签Name为"' +formData["key"]+ '"的元素！');
    		return;
    	}
    	var type = formData["type"];
    	// 判断是否使用日期组件
		var useDate = (["year","month","date","time","datetime"].indexOf(type) >= 0),
			needSource = (["checkbox","radio","select","switch"].indexOf(type) >= 0);
			// 日期组件元素类型是text [{"key":"1","value":"选项1"},{"key":"2","value":"选项2"}]
			formData["type"] =  useDate ? "text" : type;
			// 禁用状态获取不到数据问题
			formData["status"] =  formData["status"] || 0;
			formData["classname"] =  formData["classname"] || "";
			formData["style"] =  formData["style"] || "";
		// 日期格式
		var formats = {
			"year"		: "yyyy",
			"month"		: "yyyy-MM",
			"date"		: "yyyy-MM-dd",
			"time"		: "HH:mm:ss",
			"datetime"	: "yyyy-MM-dd HH:mm:ss"
		};
		if(useDate){
			formData["placeholder"] = formData["placeholder"] || formats[type];
		}
		if(needSource){
			if(!formData["source"]){
				layer.msg("当前使用的元素类型需要渲染数据，请完善数据来源!");
				return;
			}
			try {
				formData["sourcestr"] = formData["source"];
				// 需要把字符串转为JSON对象
				formData["source"] = JSON.parse(formData["source"]);
			} catch (e) {
				layer.msg('数据来源格式错误！格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]');
			}
		}
		formData["cache"] =  JSON.stringify(formData);
		// 判断样式
		formData["itemClass"] = (["checkbox","radio","textarea"].indexOf(type) >= 0) ? "layui-input-block" : "layui-input-inline";
		// 渲染lay模板
    	laytpl($("#layui-form-item-tpl").html()).render(formData, function(html){
    		
    		var queryForm = $("#LAY-webui-elems-form");
    		if(queryForm.find('div.layui-form-item').size() == 0){
    			queryForm.html(html);
    			form.render();
    		} else {
    			queryForm.append(html);
    			form.render();
    		}
    		// laydate组件
    		if(useDate){
    			laydate.render({
    				format	: formats[type],
        		    elem	: '#LAY-webui-elems-form input[name='+formData["key"]+']',
        		    type	: type
        		});
    		};
    		
		});
    });
	
    form.on('submit(LAY-webui-form-elem-btns)', function(data) {
    	var formData = data.field;
    	layer.msg(JSON.stringify(formData));
    	// 同名按钮检查
    	if($('#LAY-webui-form-elem-btns button[title='+formData["label"]+']').size() > 0){
    		layer.msg('已存在标签名称为"' +formData["label"]+ '"的按钮！');
    		return;
    	}
		// 禁用状态获取不到数据问题
    	formData["status"] =  formData["status"] || 0;
		formData["classname"] =  formData["classname"] || "";
		formData["style"] =  formData["style"] || "";
		formData["event"] =  formData["status"] == 0 ? "" : (formData["event"] || "");
		formData["cache"] =  JSON.stringify(formData);
		// 渲染lay模板
    	laytpl($("#layui-form-btns-tpl").html()).render(formData, function(html){
    		var btnForm = $("#LAY-webui-form-elem-btns");
    		if(btnForm.find('button').size() == 0){
    			btnForm.html(html);
    		} else {
    			btnForm.append(html);
    		}
		});
    });
    
	form.on('submit(LAY-webui-detail-elems)', function(data) {
    	var field = data.field;
    	layer.msg(JSON.stringify(field));
    });
	
	// 组装自定义界面设计数据进行后台提交
	form.on('submit(design-ok)', function(data) {
		var field = data.field;
    	
		$("#LAY-webui-form-submit").click();
		
		var formRequest = {
			"uiId" 	: $("#uiId").val()
		};
		
		if($("#LAY-webui-form-query-items").size() > 0 ){
			formRequest["form"] = {
				"uiId" 		: $("#uiId").val(),
				"type" 		: 1, // 表单类型（1:查询条件,2:数据表单,3:详情表单）
				"name"		: $("#LAY-webui-form input[name='name']").val(),
				"classname"	: $("#LAY-webui-form input[name='classname']").val(),
				"style"		: $("#LAY-webui-form input[name='style']").val(),
				"url"		: $("#LAY-webui-form input[name='url']").val(),
				"sql"		: $("#LAY-webui-form textarea[name='sql']").val()
			};
			// 查询条件
			var items = [];
			$("#LAY-webui-form-query-items div.layui-inline").each(function(i, item){
				var label = $(item).find("label");
				items.push($.parseJSON(label.attr("lay-cache")));
			});
			formRequest["form"]["items"] = items;
		} else if($("#LAY-webui-elems-form").size() > 0 ){
			formRequest["form"] = {
				"uiId" 		: $("#uiId").val(),
				"type" 		: 2, // 表单类型（1:查询条件,2:数据表单,3:详情表单）
				"name"		: $("#LAY-webui-form input[name='name']").val(),
				"classname"	: $("#LAY-webui-form input[name='classname']").val(),
				"style"		: $("#LAY-webui-form input[name='style']").val(),
				"url"		: $("#LAY-webui-form input[name='url']").val(),
				"sql"		: $("#LAY-webui-form textarea[name='sql']").val()
			};
			// 表单元素
			var items = [];
			$("#LAY-webui-elems-form div.layui-form-item").each(function(i, item){
				var label = $(item).find("label");
				items.push($.parseJSON(label.attr("lay-cache")));
			});
			formRequest["form"]["items"] = items;
		} else {
			formRequest["form"] = {
				"uiId" 		: $("#uiId").val(),
				"type" 		: 3, // 表单类型（1:查询条件,2:数据表单,3:详情表单）
				"name"		: $("#LAY-webui-form input[name='name']").val(),
				"classname"	: $("#LAY-webui-form input[name='classname']").val(),
				"style"		: $("#LAY-webui-form input[name='style']").val(),
				"url"		: $("#LAY-webui-form input[name='url']").val(),
				"sql"		: $("#LAY-webui-form textarea[name='sql']").val()
			};
			// 表单元素
			var items = [];
			$("#LAY-webui-detail-elems div.layui-form-item").each(function(i, item){
				var label = $(item).find("label");
				items.push($.parseJSON(label.attr("lay-cache")));
			});
			formRequest["form"]["items"] = items;
		}
		
		// 操作按钮
		$("#LAY-webui-form-elem-btns button").each(function(i, item){
			
		});
		layer.msg(JSON.stringify(formRequest));
		// 提交界面设计信息
  		admin.req({
	        url			: setter.prefix + 'extras/webui/design',
	        type 		: "post",
	        data		: formRequest,
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
	
	form.on('submit(design-cancel)', function(data) {
		element.tabChange('webui-tab', 1);
    });
	
	$(document).on("click", "i.layui-inline-remove", function(){
		$(this).parent().parent().remove();
		var queryForm = $("#LAY-webui-form-query-items");
		if(queryForm.find('div.layui-inline').size() == 0){
			queryForm.html('<div class="layui-input-text layui-word-aux" style="text-align: center;">没有查询条件，可添加！</div>');
		}
	}).on("click", "i.layui-form-item-remove", function(){
		$(this).parent().parent().remove();
		var elmForm = $("#LAY-webui-elems-form");
		if(elmForm.find('div.layui-form-item').size() == 0){
			elmForm.html('<div class="layui-input-text layui-word-aux" style="text-align: center;">没有表单元素，可添加！</div>');
		}
	});
	
	//监听编辑状态操作
	form.on('switch(editable)', function(obj){
		//layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
		var value = this.value, //得到修改后的值
			id = $(obj.elem).data("id");
	    // 提交更新
  		admin.req({
	        url			: setter.prefix + 'extras/webui/editable',
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
	
	//监听发布状态操作
	form.on('switch(release)', function(obj){
		//layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
		var value = this.value, //得到修改后的值
			id = $(obj.elem).data("id");
	    // 提交更新
  		admin.req({
	        url			: setter.prefix + 'extras/webui/release',
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
	treeGrid.on('tool(LAY-webui-list)', function(obj){
	    var data = obj.data;
	    var id = data.id;
	    if(obj.event === 'child'){
	    	layer.open({ 
    			type: 2,
    			title: '创建子界面',
    			content: setter.prefix + 'extras/webui/ui/new?id=' + id,
    			area: ['500px', '400px'],
    			btn: ['确定', '取消'],
    			yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-webui-forum-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'extras/webui/new',
		        	        type 		: "POST",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		treeGrid.reload('LAY-webui-list'); //刷新表格
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
	    else if(obj.event === 'del'){
	    	
	    	//layer.msg(JSON.stringify(obj));
	    	layer.confirm('确定删除当前的数据吗？<br/>将会同时删除相关的表单、表格、图表等数据！', function(index){
		      	admin.req({
		  	        url			: setter.prefix + 'extras/webui/delete/' + id,
		  	        type 		: "post",
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["message"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        		treeGrid.reload('LAY-webui-list'); //刷新表格
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
	    else if(obj.event === 'renew'){
	    	layer.open({ 
    			type: 2,
    			title: '编辑界面信息',
    			content: setter.prefix + 'extras/webui/ui/renew/' + id,
    			area: ['500px', '400px'],
    			btn: ['确定', '取消'],
    			yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-webui-forum-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'extras/webui/renew',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		treeGrid.reload('LAY-webui-list'); //刷新表格
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
	    else if(obj.event === 'design'){
	    	//Ajax获取
	    	$.get(setter.prefix + 'extras/webui/ui/design/' + id, {}, function(str){
	    		$("#LAY-webui-design").html(str);
	    		element.tabChange('webui-tab', 2);
	    	});
	    }
	});
	
	//事件
    var active = {
    	add: function(){
    		layer.open({ 
    			type: 2,
    			title: '创建新界面（可关联菜单）',
    			content: setter.prefix + 'extras/webui/ui/new',
    			area: ['500px', '400px'],
    			btn: ['确定', '取消'],
    			yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-webui-forum-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'extras/webui/new',
		        	        type 		: "POST",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		treeGrid.reload('LAY-webui-list'); //刷新表格
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
    
   // view(this.id).render('system/about');
    

	exports('webui', {})
});