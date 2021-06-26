/**

 @Name：layuiAdmin（iframe版） 消息中心
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['admin', 'table', 'util'], function(exports){
  var $ = layui.$,
  	admin = layui.admin,
  	setter = layui.setter,
  	table = layui.table,
  	element = layui.element;
  
  var DISABLED = 'layui-btn-disabled'
  
  //区分各选项卡中的表格
  ,tabs = {
    all: {
      text: '全部消息'
      ,id: 'LAY-app-message-all'
    }
    ,notice: {
      text: '通知'
      ,id: 'LAY-app-message-notice'
    }
    ,direct: {
      text: '私信'
      ,id: 'LAY-app-message-direct'
    }
  };
  
  var tplStatus = function(d){
	if(d.status == 1){
	  return "";
  	}
    return '<i class="layui-icon layui-icon-tips" title="未读"></i>';
  };
  
  //标题内容模板
  var tplTitle = function(d){
    return '<a href="detail/'+ d.id +'">'+ d.title;
  };
  
  //全部消息
  table.render({
	elem: '#LAY-app-message-all',
    url	: ctxPath + 'extras/inform/list', //模拟接口
    page: true,
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
    cols: [[
    	{type: 'checkbox', fixed: 'left'},
    	{field: 'status', title: '', width: 35, templet: tplStatus},
      	{field: 'title', title: '标题内容', minWidth: 300, templet: tplTitle},
      	{field: 'time', title: '时间', width: 170, templet: '<div>{{ layui.util.timeAgo(d.timestamp) }}</div>'}
    ]],
    skin: 'line'
  });
  
  //通知
  table.render({
    elem: '#LAY-app-message-notice',
    url	: ctxPath + 'extras/inform/list?type=notice', //模拟接口
    page: true,
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
    cols: [[
    	{type: 'checkbox', fixed: 'left'},
    	{field: 'status', title: '', width: 35, templet: tplStatus},
      	{field: 'title', title: '标题内容', minWidth: 300, templet: tplTitle},
      	{field: 'time', title: '时间', width: 170, templet: '<div>{{ layui.util.timeAgo(d.timestamp) }}</div>'}
    ]],
    skin: 'line'
  });
  
  //私信
  table.render({
    elem: '#LAY-app-message-direct',
    url	: ctxPath + 'extras/inform/list?type=direct', //模拟接口
    page: true,
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
    cols: [[
    	{type: 'checkbox', fixed: 'left'},
    	{field: 'status', title: '', width: 35, templet: tplStatus},
      	{field: 'title', title: '标题内容', minWidth: 300, templet: tplTitle},
      	{field: 'time', title: '时间', width: 170, templet: '<div>{{ layui.util.timeAgo(d.timestamp) }}</div>'}
    ]],
    skin: 'line'
  });
  
  
  //事件处理
  var events = {
    del: function(othis, type){
      var thisTabs = tabs[type],
      	checkStatus = table.checkStatus(thisTabs.id),
      	data = checkStatus.data; //获得选中的数据
      if(data.length === 0){
    	  return layer.msg('未选中行');
      }
	  
      layer.confirm('确定删除选中的数据吗？', function(){
    	  var ids = [];
    	  for( i in data){
    		  ids.push(data[i].id);
    	  }
    	  admin.req({
	        url			: setter.prefix + 'extras/inform/delete',
	        type 		: "post",
	        data		: {"ids" : ids.join(",")},
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        		table.reload(thisTabs.id); //刷新表格
	        	} else {
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
    	  });
      });
    }
    ,ready: function(othis, type){
    	var thisTabs = tabs[type],
	      	checkStatus = table.checkStatus(thisTabs.id),
	      	data = checkStatus.data; //获得选中的数据
	      if(data.length === 0){
	    	  return layer.msg('未选中行');
	      }
	      var ids = [];
		  for( i in data){
			  ids.push(data[i].id);
		  }
		  admin.req({
	        url			: setter.prefix + 'extras/inform/read',
	        type 		: "post",
	        data		: {"ids" : ids.join(",")},
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        		table.reload(thisTabs.id); //刷新表格
	        	} else {
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
		  });
    }
    ,readyAll: function(othis, type){
    	var thisTabs = tabs[type];
    	admin.req({
	        url			: setter.prefix + 'extras/inform/readall',
	        type 		: "post",
	        success		: function(res){
	        	/*layer.msg(thisTabs.text + '：全部已读', {
	                icon: 1
	              });
	        	*/
	        	if(res.status == 'success'){
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        		table.reload(thisTabs.id); //刷新表格
	        	} else {
	        		layer.msg(res["msg"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
		  });
    }
  };
  
  $('.LAY-app-message-btns .layui-btn').on('click', function(){
    var othis = $(this)
    ,thisEvent = othis.data('events')
    ,type = othis.data('type');
    events[thisEvent] && events[thisEvent].call(this, othis, type);
  });
  
  exports('message', {});
});