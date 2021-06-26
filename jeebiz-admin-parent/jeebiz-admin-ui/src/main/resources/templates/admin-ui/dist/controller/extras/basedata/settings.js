/**

 @Name：layuiAdmin（iframe版） 设置
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL
    
 */
 
layui.define(['form', 'upload'], function(exports){
	var $ = layui.$
	,layer = layui.layer
	,laytpl = layui.laytpl
	,setter = layui.setter
	,view = layui.view
	,admin = layui.admin
	,form = layui.form
	,upload = layui.upload;

	var $body = $('body');
  
	//自定义验证
	form.verify({
		nickname: function(value, item){ //value：表单的值、item：表单的DOM对象
			if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
				return '用户名不能有特殊字符';
			}
			if(/(^\_)|(\__)|(\_+$)/.test(value)){
				return '用户名首尾不能出现下划线\'_\'';
			}
			if(/^\d+\d+\d$/.test(value)){
				return '用户名不能全为数字';
			}
		},
    	//我们既支持上述函数式的方式，也支持下述数组的形式
    	//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
    	pass: [
    		/^[\S]{6,12}$/,
    		'密码必须6到12位，且不能出现空格'
    	],
    	//确认密码
    	repass: function(value){
    		if(value !== $('#LAY_password').val()){
    			return '两次密码输入不一致';
    		}
    	}
	});
	
	//监听指定开关
	form.on('switch(set_switch)', function(data){
		$(this).attr("value", this.checked ? 'ON' : 'OFF')
		
		//layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
	    
		
	});
	
  	//系统参数设置：前端设置、后台设置、邮件服务
  	form.on('submit(set_basedata)', function(obj){
  		//layer.msg(JSON.stringify(obj));
  		var gkey = $(this).attr("data-gkey");
  		var items = [];
  		for( key in obj.field){
  			var id = $(":input[name='" + key + "']").attr("data-id");
  			items.push({
  				"id" 	: id,
  				"key" 	: key,
  				"value" : obj.field[key]
  			});
  		}
  		// 兼容switch组件数据无法获取的问题
  		$("input[lay-skin='switch']").each(function(){
  			// 关闭状态
  			if(!$(this).prop("checked")){
  				items.push({
  	  				"id" 	: $(this).attr("data-id"),
  	  				"key" 	: $(this).attr("name"),
  	  				"value" : $(this).val()
  	  			});
  			}
  		});
        console.log(items);
  		// 提交更新
  		admin.req({
	        url			: setter.prefix + 'extras/basedata/settings/batch/renew',
	        type 		: "post",
	        contentType	: "application/json",
	        dataType	: "json",
	        data		: JSON.stringify({
		   		"gkey" 	: gkey,
		   		"datas"	: items
			}),
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
	    return false;
  	});
  
  
  	//设置我的资料
  	form.on('submit(setmyinfo)', function(obj){
	    admin.req({
	        url			: setter.prefix + 'authz/user/reset/info',
	        type 		: "post",
	        contentType	: "application/json",
	        dataType	: "json",
            data: JSON.stringify(obj.field),
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
    	return false;
  	});

  //上传头像
  var avatarSrc = $('#LAY_avatarSrc');
  upload.render({
    url: '/api/upload/'
    ,elem: '#LAY_avatarUpload'
    ,done: function(res){
      if(res.status == 0){
        avatarSrc.val(res.url);
      } else {
        layer.msg(res.msg, {icon: 5});
      }
    }
  });
  
  //查看头像
  admin.events.avartatPreview = function(othis){
    var src = avatarSrc.val();
    layer.photos({
      photos: {
        "title": "查看头像" //相册标题
        ,"data": [{
          "src": src //原图地址
        }]
      }
      ,shade: 0.01
      ,closeBtn: 1
      ,anim: 5
    });
  };
  
  
  //设置密码
  form.on('submit(setmypass)', function(obj){
    // 重置密码
    admin.req({
        url		: setter.prefix + 'authz/user/reset/pwd',
        type 	: "post",
        data	: obj.field,
        success	: function(res){
        	if(res.status == 'success'){
        		layer.msg(res["msg"]||"", {
                   	icon: 2
            	});
        		// 执行退出接口
        		admin.req({
        			url : setter.prefix + 'logout',
        			type : 'post',
        			data : {},
        			success : function(res) { 
        				/* 这里要说明一下：done 是只有 response 的 code 正常才会执行。
        				 * 而 success 则是只要 http 为 200 就会执行
        				 */
        				// 清空本地记录的 token，并跳转到登入页
        				admin.exit(function() {
        					top.location.replace(setter.prefix + 'authz/login/stateful');
        				});
        			}
        		});
        	} else {
        		layer.msg(res["msg"]||"", {
                   	icon: 2
            	});
        	}
        }
    });
     
    return false;
    
  });
  
  //对外暴露的接口
  exports('settings', {});
});