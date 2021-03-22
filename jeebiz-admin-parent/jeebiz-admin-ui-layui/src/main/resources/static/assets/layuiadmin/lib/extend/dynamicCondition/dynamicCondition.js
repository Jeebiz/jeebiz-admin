
/**
 @Name： dynamicCondition 动态添加查询条件
 @Author：肖朋林
 @version:  2.0.2 2019-01-04 新增查询条件无弹窗模式。
 	码云地址：https://gitee.com/pelin0963/layuiExtend/releases
 	在线demo：https://pelin0963.gitee.io/layuiextend/layui_exts/dynamicCondition/demo/index.html
 	qq:305916516
 */
layui.define(['jquery','table', 'form','laydate'], function (exports) {
    "use strict";

    var MOD_NAME = 'dynamicCondition',
        $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        form = layui.form;
    var tempVal = {value:"",text:""};
    //创建实例
    var createInstance = function(_config){
    	var instance = {
    		data:[]
    		,config:_config
    		,requestData:{}
    		,cacheCondition:[] //缓存查询条件
    		,conditionContainerId:(Math.random()+"").substr(2)//随机日期id
    		,conditionFieldWidth:_config.conditionFieldWidth || 120 //第一列默认宽度
    		,conditionOptionWidth:_config.conditionOptionWidth || 100//第二列默认宽度
    		,conditionValueWidth:_config.conditionValueWidth || 150 //第三列默认宽度
    		,height:380 //弹出窗口默认高度
    		,width:610 //弹出窗口默认宽度
    	};
    	if(instance.config.type != "complex"){
    		instance.width = instance.width - instance.conditionOptionWidth;
    	}
    	/**设置显示模式
    	 * displayModel,取值：'popup'/'unpopup'
    	 * */
    	instance.setDisplayModel = function(displayModel){
    		instance.config.displayModel = displayModel;
    		if(instance.config.displayModel == "unpopup"){
    			instance.conditionValueWidth = 100;
    			instance.buildConditionHtmlForUnpopup();
    			if(instance.openPageIndex){
    				layer.close(instance.openPageIndex); 
    			}
    		}else{
    			instance.conditionValueWidth = 150;
    			instance.buildConditionHtml();
    		}
    	}
    	/**根据字段名称获取对应的配置项*/
    	instance.getObjByField=function(conditionFieldVal){
    		for(var i=0;i<instance.data.length;i++){
    			if(instance.data[i].field == conditionFieldVal){
    				return instance.data[i];
    			}
    		}
    		return null;
    	}
    	/**根据字段名称获取行div，返回一个dom类型的数组*/
    	instance.getRowDivs=function(conditionFieldVal){
    		var rs = [];
    		var allRowDivJqs = $("#" + instance.conditionContainerId).find(".conditionRow");
    		allRowDivJqs.each(function(){
    			var selVal = $(this).find(".conditionField option:selected").val();
    			if(conditionFieldVal == selVal){
    				rs.push($(this)[0]);
    			}
    		})
    		return rs;
    	}
    	/**根据字段名称获对应的值，如果该字段条件有多个，则只取第一行对应的值。*/
    	instance.getVal=function(conditionFieldVal){
    		var rs = instance.getRowDivs(conditionFieldVal);
    		if(rs.length == 0){
    			//没有对应的的值
    			return null;
    		}
    		var rowDiv = rs[0];
    		var eleJq = rowDiv.eleJq;
    		var eleLeftJq = rowDiv.eleLeftJq;
    		var eleRightJq = rowDiv.eleRightJq;
    		if(eleJq){
    			return rowDiv.curEditor.getRequestValue(eleJq);
    		}
    		//操作符是between时返回2个值
    		else if(eleLeftJq){
    			return {left:rowDiv.curEditor.getRequestValue(eleLeftJq),right:rowDiv.curEditor.getRequestValue(eleRightJq)};
    		}
    		//当操作符是empty时返回null
    		return null;
    	}
    	/**设置初始条件
    	 * conditionArr,例：
    	 * */
    	instance.setCondition = function(conditionArr){
    		//缓存查询条件
    		var cacheCondition = [];
    		for(var i=0;i<conditionArr.length;i++){
    			var curCondition = conditionArr[i];
    			var conditionObj = {};
    			conditionObj.conditionFieldVal = curCondition[0];
    			if(!curCondition[1]  || instance.config.type=="simple"){
    				curCondition[1] = "equal";
    			}
    			conditionObj.conditionOptionVal = curCondition[1];

    			curCondition[2] = curCondition[2] || "";
    			curCondition[3] = curCondition[3] || "";
    			conditionObj.conditionValueVal = tempVal
    			conditionObj.conditionValueLeftVal = tempVal
    			conditionObj.conditionValueRightVal = tempVal
    			if(typeof curCondition[2] == "object"){
    				if(curCondition[1] == "between"){
    					conditionObj.conditionValueLeftVal = curCondition[2];
    				}else{
    					conditionObj.conditionValueVal = curCondition[2];
    				}
    			}else{
    				if(curCondition[1] == "between"){
    					conditionObj.conditionValueLeftVal = {value:curCondition[2],text:curCondition[2]};
    				}else{
    					conditionObj.conditionValueVal = {value:curCondition[2],text:curCondition[2]};
    				}
    			}
    			if(typeof curCondition[3] == "object"){
    				conditionObj.conditionValueRightVal = curCondition[3];
    			}else{
    				conditionObj.conditionValueRightVal = {value:curCondition[3],text:curCondition[3]};
    			}
    			cacheCondition.push(conditionObj);
    		}
    		instance.cacheCondition = cacheCondition;
    		instance.setDisplayModel(instance.config.displayModel);
    	}
    	/**新增条件*/
    	instance.addRow = function(){
    		  var conditionContainerJq = $("#"+instance.conditionContainerId);
			  var conditionRowJq = $('<div class="conditionRow"></div>');
			  if(instance.config.type == "complex"){
				  var width = instance.conditionFieldWidth + instance.conditionOptionWidth + 2 * instance.conditionValueWidth + 20 + 60;
				  conditionRowJq.css("width", width);
			  }else{
				  var width = instance.conditionFieldWidth  + 2 * instance.conditionValueWidth + 20 + 60;
				  conditionRowJq.css("width", width);
			  }
			  //字段
			  var conditionField=$('<div class="layui-inline conditionField"></div>');
			  conditionField.width(instance.conditionFieldWidth);
			  conditionField.append($(instance.conditionFieldHtml));
			  //操作
			  var conditionOption=$('<div class="layui-inline conditionOption"></div>');
			  conditionOption.width(instance.conditionOptionWidth);
			  conditionOption.append($(instance.conditionOptionHtml));
			  //值
			  var conditionValue=$('<div class="layui-inline conditionValue"></div>');
			  conditionValue.width(instance.conditionValueWidth*2+20);
			  var conditionDel=$('<div class="layui-inline conditionDel"></div>');
//			  conditionDel.width(30);
			  //删除按钮
			  var delJq=$('<a href="javascript:void(0);" class="delRowBtn"><i class="layui-icon layui-icon-close" style="font-size: 30px; color: red;"></i></a>');
			  conditionDel.append(delJq);
			  conditionRowJq.append(conditionField);
			  conditionRowJq.append(conditionOption);
			  conditionRowJq.append(conditionValue);
			  conditionRowJq.append(conditionDel);
			  conditionContainerJq.find(".conditionDiv").append(conditionRowJq);
			  
			  //删除事件
			  delJq.on("click",function(){
				  conditionRowJq.remove();
			  });
			  
			  if(instance.config.type == "simple"){
				  conditionOption.hide();
			  }
			  return conditionRowJq;
//			  form.render();
		  }
    	  /**更新conditionValue*/
    	  instance.updateConditionValue = function(conditionRowJq, conditionObj){
    		  conditionObj = conditionObj || {conditionValueVal:{},conditionValueLeftVal:{},conditionValueRightVal:{}}
			  var conditionValueJq = conditionRowJq.find(".conditionValue");
			  var conditionFieldVal = conditionRowJq.find("select[name='conditionField']").val();
			  var conditionOptionVal = conditionRowJq.find("select[name='conditionOption']").val();
			  var obj = instance.getObjByField(conditionFieldVal);
			  //没有对应的obj，则不用更新conditionValue
			  if(!obj){
				  return ;
			  }
			  //指定操作选项
			  if(obj.allowDel === "true"){
				  //允许删除的条件，下拉字段选项排除掉不能删除条件字段。
				  removeOption(conditionRowJq.find("select[name='conditionField']"), instance.fieldList);
			  }
			  //指定操作选项
			  if(obj.ops){
				  removeOption(conditionRowJq.find("select[name='conditionOption']"), obj.ops);
			  }
			  //conditionValueJq div中缓存对应的edit和conditionOptionVal，以后优化只有当edit和conditionOptionVal改变时才考虑更新conditionValueJq
			  var old_field = conditionValueJq.attr("field");
			  var old_edit = conditionValueJq.attr("edit");
			  var old_conditionOptionVal = conditionValueJq.attr("conditionOptionVal");
			  conditionValueJq.attr("field", obj.field);
			  conditionValueJq.attr("edit", obj.edit);
			  conditionValueJq.attr("conditionOptionVal", conditionOptionVal);
			  var curEditor = dynamicCondition.editor[obj.edit];
			  conditionRowJq[0].curEditor = curEditor;
			  if(conditionOptionVal == "empty"){
				  conditionValueJq.html("");
				  return ;
			  }
			  if(curEditor){
				  if(conditionOptionVal == "between"){
					  var eleLeftJq = $(curEditor.createElement(obj));
					  var eleRightJq = $(curEditor.createElement(obj));
					  var divLeft = $("<div style='display:inline-block'></div>");
					  var divRight = $("<div style='display:inline-block'></div>");
					  
					  divLeft.append(eleLeftJq);
					  divLeft.attr("name","conditionValueLeft");
					  divLeft.width(instance.conditionValueWidth);
					  
					  divRight.append(eleRightJq);
					  divRight.attr("name","conditionValueRight");
					  divRight.width(instance.conditionValueWidth);
					  
					  //更新conditionValueJq
					  conditionValueJq.html("");
					  conditionValueJq.append(divLeft);
					  conditionValueJq.append("<span style='margin:auto 3px;'>至</span>");
					  conditionValueJq.append(divRight);
					  //必须将jq对象转换为dom对象才能绑定对象属性。
					  conditionRowJq[0].eleJq = null;
					  conditionRowJq[0].eleLeftJq = eleLeftJq;
					  conditionRowJq[0].eleRightJq = eleRightJq;
					  curEditor.fillElement(eleLeftJq,conditionObj.conditionValueLeftVal);
					  curEditor.fillElement(eleRightJq,conditionObj.conditionValueRightVal);
					  curEditor.render(eleLeftJq);
					  curEditor.render(eleRightJq);
				  }else{
					  var eleJq = $(curEditor.createElement(obj));
					  var divJq = $("<div></div>");
					  divJq.append(eleJq);
					  divJq.attr("name","conditionValue");
					  conditionValueJq.html("");
					  conditionValueJq.append(divJq);
//					  divJq.attr("xpl-dc-val",conditionObj.conditionValueVal);
					  //必须将jq对象转换为dom对象才能绑定对象属性。
					  conditionRowJq[0].eleJq = eleJq;
					  conditionRowJq[0].eleLeftJq = null;
					  conditionRowJq[0].eleRightJq = null;
					  curEditor.fillElement(eleJq,conditionObj.conditionValueVal);
					  curEditor.render(eleJq);
				  }
				  form.render(null, 'conditionDiv'+instance.conditionContainerId);
				  return ;
			  }
		  }
    	/**校验表单*/
    	instance.verifyForm = function(){
			var verifySuccess = true;
			var conditionContainerJq = $("#" + instance.conditionContainerId);
		    var verify = form.config.verify
		    ,DANGER = 'layui-form-danger'
		    ,verifyElem = conditionContainerJq.find('*[lay-verify]') //获取需要校验的元素
		    
		    //开始校验
		    for(var i=0;i<verifyElem.length;i++){
		    	var item = verifyElem[i];
		    	var othis = $(item)
			      ,vers = othis.attr('lay-verify').split('|')
			      ,verType = othis.attr('lay-verType') //提示方式
			      ,value = othis.val();
		    	othis.removeClass(DANGER);
		    	var errorText;
		    	//是否允许空值
		    	var allowBlank = true;
		    	layui.each(vers, function(_, thisVer){
			    	if(thisVer.indexOf("required") >= 0){
			    		//不允许为空值
			    		allowBlank = false;
			    	}
		    	})
			    //允许为空值
			    if(allowBlank){
			    	if(value == ""){
			    		//校验通过，如果还有其他的pass，number等也不用校验了。
			    		continue;
			    	}
			    }
		    	//不允许为空值，继续校验
		    	for(var j=0;j<vers.length;j++){
		    		var isTrue=null //是否命中校验
			    	  	,thisVer = vers[j]  //校验name，如：required，pass 等
				        ,errorText = '' //错误提示文本
				        ,isFn = typeof verify[thisVer] === 'function';
		    	  	//匹配验证规则
			        if(verify[thisVer]){
			        	isTrue = isFn ? errorText = verify[thisVer](value, item) : !verify[thisVer][0].test(value);
			        	errorText = errorText || verify[thisVer][1];
			        	//isTrue为true，则验证不通过
			        	if(isTrue){
			        		verifySuccess = false;
			        		//提示
			        		layer.tips(errorText, function(){
				                if(typeof othis.attr('lay-ignore') !== 'string'){
				                  if(item.tagName.toLowerCase() === 'select' || /^checkbox|radio$/.test(item.type)){
				                    return othis.next();
				                  }
				                }
				                return othis;
				              }(), {tips: [1, '#FF0000']});
			        		othis.addClass(DANGER);
			        	 	return verifySuccess;
			        	}
			        }
		    	}
		    }
		    return verifySuccess;
		  }
    	/**根据动态查询条件构造缓存对应的请求条件*/
    	instance.buildCacheCondition = function(){
    		var conditionContainerJq = $("#" + instance.conditionContainerId);
    		var conditionRowJqs = conditionContainerJq.find(".conditionRow");
    		var rowLength = conditionRowJqs.size();
    		//缓存查询条件
    		var cacheCondition = [];
    		for(var i=0;i<rowLength;i++){
    			var conditionRowJq = conditionRowJqs.eq(i);
    			var valJq = conditionRowJq.find("[name='conditionValue']");
    			var valLeftJq = conditionRowJq.find("[name='conditionValueLeft']");
    			var valRightJq = conditionRowJq.find("[name='conditionValueRight']");
    			
    			var conditionObj = {};
    			conditionObj.conditionFieldVal = conditionRowJq.find("select[name='conditionField']").val();
    			conditionObj.conditionOptionVal = conditionRowJq.find("select[name='conditionOption']").val();
    			var item = instance.getObjByField(conditionObj.conditionFieldVal);
    			var curEditor = dynamicCondition.editor[item.edit];
	  			if(curEditor){
	  				var conditionRowDOM = conditionRowJq[0];
	  				var tempjq = conditionRowDOM.eleJq;
	  				conditionObj.conditionValueVal = tempjq ? {value:curEditor.getRequestValue(tempjq),text:curEditor.getRequestText(tempjq)} : tempVal;
	  				tempjq = conditionRowDOM.eleLeftJq;
	    			conditionObj.conditionValueLeftVal = tempjq ? {value:curEditor.getRequestValue(tempjq),text:curEditor.getRequestText(tempjq)} : tempVal;
	    			tempjq = conditionRowDOM.eleRightJq;
	    			conditionObj.conditionValueRightVal = tempjq ? {value:curEditor.getRequestValue(tempjq),text:curEditor.getRequestText(tempjq)} : tempVal;
	  			}
    			cacheCondition.push(conditionObj);
    		}
    		instance.cacheCondition = cacheCondition;
    		return cacheCondition;
    	}
    	/**根据动态查询条件构造对应的请求参数.*/
    	instance.buildRequestData = function(cacheCondition){
    		var cacheCondition = cacheCondition || instance.cacheCondition;
    		var requestData = {};
    		var rowLength = cacheCondition.length;
    		//简单模式
    		if(instance.config.type == "simple"){
    			for(var i=0;i<rowLength;i++){
    				var conditionObj = cacheCondition[i];
    				requestData[conditionObj.conditionFieldVal] = conditionObj.conditionValueVal.value;
    			}
    			instance.requestData = requestData;
    			return requestData;
    		}
    		//复杂模式
    		if(instance.config.requestDataType == "json"){//结果为json字符串模式
    			instance.requestData = {jsonStr: JSON.stringify(cacheCondition)};
    			return instance.requestData;
    		}
    		requestData.rowLength = rowLength;
    		for(var i=0;i<rowLength;i++){
    			var conditionObj = cacheCondition[i];
    			requestData["QueryCondition["+i+"].conditionField"] = conditionObj.conditionFieldVal;
    			requestData["QueryCondition["+i+"].conditionOption"] = conditionObj.conditionOptionVal;
    			requestData["QueryCondition["+i+"].conditionValue"] = conditionObj.conditionValueVal.value;
    			requestData["QueryCondition["+i+"].conditionValueLeft"] = conditionObj.conditionValueLeftVal.value;
    			requestData["QueryCondition["+i+"].conditionValueRight"] = conditionObj.conditionValueRightVal.value;
    		}
    		//设置请求参数
    		instance.requestData = requestData;
    		return requestData;
    	}
    	/**根据动态查询条件构造对应的显示文本*/
    	instance.buildConditionHtml = function(){
    		
    		var cacheCondition = instance.cacheCondition;
    		var conditionHtml = "";
    		var fieldSelectJq = $(instance.conditionFieldHtml);
    		var optionSelectJq = $(instance.conditionOptionHtml);
    		var blankStr = "&nbsp;&nbsp;";
    		for(var i=0;i<cacheCondition.length;i++){
    			var conditionObj = cacheCondition[i];
    			var fieldText = fieldSelectJq.find("option[value='"+conditionObj.conditionFieldVal+"']").text();
    			var OptionText = optionSelectJq.find("option[value='"+conditionObj.conditionOptionVal+"']").text();
    			var ValueText = conditionObj.conditionValueVal.text;
    			var ValueLeftText = conditionObj.conditionValueLeftVal.text;
    			var ValueRightText = conditionObj.conditionValueRightVal.text;
    			
    			var rsValueText="";
    			if(conditionObj.conditionOptionVal == "between"){
    				rsValueText = ValueLeftText + blankStr+"至"+ blankStr + ValueRightText;
				}else{
					rsValueText = ValueText;
				}
    			rsValueText = rsValueText || "";
    			//简单模式
    			var spanJq = $("<span class='layui-xpl-dc-circle' index="+i+"></span>");
    			if(instance.config.type == "simple"){
    				spanJq.html(fieldText + blankStr + ":" + blankStr + rsValueText);
    				//conditionHtml += fieldText + blankStr + ":" + blankStr + rsValueText;
        		}
    			//复杂模式
    			else{
    				spanJq.html(fieldText + blankStr + OptionText + blankStr + rsValueText);
//    				conditionHtml += fieldText + blankStr + OptionText + blankStr + rsValueText;
        		}
    			var iJq = $('<i class="layui-icon layui-icon-close layui-xpl-dc-delete"></i>');
    			var item = instance.getObjByField(conditionObj.conditionFieldVal);
    			iJq.attr("allowDel", item.allowDel);
    			spanJq.append(iJq);
    			conditionHtml += spanJq.prop("outerHTML");
    		}
    		var styleHtml = "<style type='text/css'>";
    		styleHtml += "\n.layui-xpl-dc-circle {border-radius: 90px;display:inline-block;text-align:center;height: 25px;line-height: 25px;background-color:#1E9FFF;color:#fff;margin:0px 5px;padding-top:0px;padding-bottom:0px;padding-left:8px;padding-right:18px;position:relative;}";
    		styleHtml += "\n.layui-xpl-dc-delete{ width:20px;height:20px;line-height: 20px;cursor:pointer;border-radius:60%;position:absolute;top:2px;right:-1px;color:#c2c2c2;}";
    		styleHtml += "\n.layui-xpl-dc-delete:hover{border-radius: 12px;color: #fff;}";
    		styleHtml += "\n</style>";
    		instance.conditionHtml = styleHtml + "<div>查询条件："+ conditionHtml +"</div>";
    		if(instance.config.conditionTextId){
				  $(instance.config.conditionTextId).html(instance.conditionHtml);
				  $(".layui-xpl-dc-delete").on("click",function(){
					  instance.delete(this);
				  })
				  $(".layui-xpl-dc-delete[allowDel='false']").hide();
			  }
    	}
    	/**针对无弹窗的显示模式根据动态查询条件构造对应的显示界面*/
    	instance.buildConditionHtmlForUnpopup = function(){
    		if(instance.config.conditionTextId){
    			var msgJq = $(instance.config.conditionTextId);
    			msgJq.html("");
    			instance.render(msgJq);
    			var styleHtml = "<style type='text/css'>";
        		styleHtml += "\n.layui-xpl-dc-msg {min-height:38px;}";
        		styleHtml += "\n.clear {clear:both;}";
        		styleHtml += "\n</style>";
        		msgJq.append($(styleHtml));
        		$(".conditionRow").css("float", "left");
        		msgJq.addClass("layui-xpl-dc-msg");
        		msgJq.find(".layui-xpl-dc-top-btns").hide();
        		msgJq.find(".conditionDel").hide();
        		msgJq.append($("<div style='float:left;'><a class='layui-btn xpl-unpopup-query'))>查询</a></div>"));
        		msgJq.append($("<div class='clear'></div>"));
        		$(".xpl-unpopup-query").on("click",function(){
    			  if(instance.verifyForm()){
//    				  layer.msg('校验通过');
    				  instance.buildCacheCondition();
    				  instance.query();
    			  }else{
//    				  layer.msg('校验失败');
    			  }
    		  });
    		}
    	}
    	/**删除条件*/
    	instance.delete = function(ele){
    		var index = $(ele).parent().attr("index");
    		instance.cacheCondition.splice(index, 1);
    		instance.buildConditionHtml();
    		instance.query();
    	}
    	/**查询*/
    	instance.query = function(){
//    		instance.buildConditionHtml();
    		instance.buildRequestData();
			//ajax请求，重载数据
			 if(instance.config.queryCallBack){
				  instance.config.queryCallBack(instance.requestData);
			  }
			  if(instance.config.tableId){
				  //查看是否有排序
				  if(instance.config.sortObj){
					  var sortObj = instance.config.sortObj;
					  instance.requestData["sortField"] = sortObj.field; //排序字段
					  instance.requestData["sortOrder"] = sortObj.type; //排序方式
				  }
//				  var curPage = $(".layui-laypage-next").attr("data-page") - 1;
				  table.reload(instance.config.tableId, {
						page: {
						  curr: 1 //重新加载当前页
						}
						,where: instance.requestData
				  });
			  }
    	}
    	/**打开窗口，动态添加查询条件*/
    	instance.open = function(){
    		if(instance.config.displayModel == 'unpopup'){
    			//如果是非弹窗模式，则直接查询，不弹出窗口。
    			$(".xpl-unpopup-query").click();
    			return ;
    		}
    		if($("#"+instance.conditionContainerId).length > 0){
    			//已经打开了，不能重复打开。
    			return ;
    		}
    		
    		//页面层-自定义
    		instance.openPageIndex = layer.open({ 
    		  type: 1,
    		  id: 'dynamicCondition' + instance.conditionContainerId,//防止重复弹出
    		  offset: '50px',
    		  title: "查询条件",
    		  //closeBtn: 0,
    		  shade: 0, //不显示遮罩
    		  area: [instance.width + 'px', instance.height +'px'], //宽高
    		  maxmin:true,
//    		  content: conditionContainerHtml
    		  content: "<div id='query"+instance.conditionContainerId+"'></div>"
    		});
    		instance.render($("#query"+instance.conditionContainerId));
    	}
    	/**渲染弹出界面*/
    	instance.render = function(divJq){
    		divJq = divJq || $("#query"+instance.conditionContainerId);
    		var conditionContainerHtml = '<div id="'+instance.conditionContainerId+'" class="conditionContainer" lay-filter="conditionContainer"><div class="layui-xpl-dc-top-btns"><a href="javascript:void(0);" style="margin-left:10px;" class="addRowBtn"><i class="layui-icon layui-icon-add-circle-fine" style="font-size: 30px; color: &#xe608;"></i> </a> <a href="javascript:void(0);" style="margin-left:10px;" class="queryBtn" ><i class="layui-icon layui-icon-search" style="font-size: 30px; color: &#xe615;"></i> </a> </div><div class="conditionDiv layui-form" lay-filter="conditionDiv'+instance.conditionContainerId+'"></div></div>';
    		divJq.append($(conditionContainerHtml));
    		var cacheCondition = instance.cacheCondition;
    		for(var i=0;i<cacheCondition.length;i++){
    			var conditionObj = cacheCondition[i]; 
    			var conditionRowJq = instance.addRow();
    			conditionRowJq.find("select[name='conditionField']").val(conditionObj.conditionFieldVal);
    			conditionRowJq.find("select[name='conditionOption']").val(conditionObj.conditionOptionVal);
    			instance.updateConditionValue(conditionRowJq, conditionObj);
    			
    			var conditionFieldVal = conditionRowJq.find("select[name='conditionField']").val();
	  			var item = instance.getObjByField(conditionObj.conditionFieldVal);
	  			if(item.allowDel === "false"){
	  				//移除删除按钮
	  				conditionRowJq.find(".delRowBtn").hide();
	  				//移除其他下拉选项
//		  			conditionRowJq.find("select[name='conditionField']").attr("disabled","disabled");
		  			removeOption(conditionRowJq.find("select[name='conditionField']"),conditionObj.conditionFieldVal);
	  			}
    		}
    		instance.afterOpen();
    		var conditionContainerJq = $("#"+instance.conditionContainerId);
    		conditionContainerJq.css("margin","10px");
    		  //监听事件
    		  form.on('select(conditionField)', function(data){
    			  if($(data.elem).find("option").length > 1){
    				  var conditionRowJq = $(data.elem).parents(".conditionRow");
    				  //更新操作选项
    				  var conditionOption= conditionRowJq.find(".conditionOption");
    				  conditionOption.html(instance.conditionOptionHtml);
        			  instance.updateConditionValue(conditionRowJq);
    			  }
    			});    
    		  form.on('select(conditionOption)', function(data){
    			  if($(data.elem).find("option").length > 1){
    				  var conditionRowJq = $(data.elem).parents(".conditionRow");
        			  instance.updateConditionValue(conditionRowJq);
    			  }
    			});
    		  //新增
    		  conditionContainerJq.find(".addRowBtn").on("click",function(){
    			  var rowJq = instance.addRow();
    			  instance.updateConditionValue(rowJq);
    		  });
    		  var verify = form.config.verify
    		  //查询
    		  conditionContainerJq.find(".queryBtn").on("click",function(){
    			  if(instance.verifyForm(conditionContainerJq)){
//    				  layer.msg('校验通过');
    				  instance.buildCacheCondition();
    				  instance.buildConditionHtml();
    				  instance.query();
    				  layer.close(instance.openPageIndex); 
    				  instance.openPageIndex  = null;
    			  }else{
//    				  layer.msg('校验失败');
    			  }
    		  });
    		  form.render(null, 'conditionDiv');
    		  
    	}
    	/**渲染后执行*/
    	instance.afterOpen = function(){
    		if(instance.config.afterOpen){
    			instance.config.afterOpen(instance);
    		}
    	}
        return instance;
    }
    var dynamicCondition = {
    	version:'2.0.1'
    	//编辑器：自带3个默认编辑器：文本text，下拉框select，日期date
    	,editor:{}
    	//缓存创建的实例
    	,cacheInstance:{}
    	/***
    	 * 获取实例
    	 * instanceName:实例名称。非必须。默认为'instanceName'.当一个页面只创建一个实例时，可以不用该参数
    	 */
    	,getInstance:function(instanceName){
    		instanceName = instanceName || 'instanceName';
    		return this.cacheInstance[instanceName];
    	}
		/***
		 * elem/fields/fieldsJsonStr：三选一.
		 * tableId/queryCallBack: 二选一。tableId对应table.render(config)的config.id参数.自动重载表格。queryCallBack(requestData)则自定义回调
		 * type: 取值：'simple'/'complex'.默认为复杂模式。区别1.显示界面不一样，2.构造的requestData格式不一样。
		 * requestDataType: 取值：'array'/'json'.请求参数类型。默认array。
		 * conditionTextId: 显示查询条件的面板选择器或DOM。非必须。例子："#frm"
		 * sortObj:排序。非必须。例子：{field:'name',type:'desc'}
		 * instanceName: 创建的实例名称。非必须。默认为'instanceName'。当一个页面只创建一个实例时，可以不用该参数
		 * displayModel: 显示模型。取值：'popup'/'unpopup'。 默认"popup"点击后提出查询条件设置窗口。unpopup模式则不弹出窗口，直接在界面上设置条件。
		 */
    	,create:function(config){
    		config.type = config.type || 'complex';
    		config.requestDataType = config.requestDataType || 'array';
    		config.instanceName = config.instanceName || 'instanceName';
    		config.displayModel = config.displayModel || 'popup';
    		var instance = createInstance(config);
    		//初始化instance.data
    		if(config.fields){
    			instance.data = config.fields;
    		}
    		else if(config.fieldsJsonStr){ 
    			instance.data = $.parseJSON(config.fieldsJsonStr);
    		}
    		else if(config.elem){ //指定容器的选择器或 DOM，方法渲染方式必填.示例"#dcDemo"，或者DOM
    			var liObjs = $(config.elem).find("li");
				liObjs.each(function(){
					var item = {
							field: $(this).attr("field") 
							,title:$(this).attr("title")
							,edit: $(this).attr("edit") 
							,allowDel: $(this).attr("allowDel") 
							,templet: $(this).attr("templet")
							,layVerify: $(this).attr("layVerify")
							,show: $(this).attr("show")
							,ops: $(this).attr("ops")
						};
					instance.data.push(item);
				})
    		}
    		//设定默认值
    		for(var i = 0;i<instance.data.length;i++){
    			var item = instance.data[i];
    			item.edit = item.edit || "text";
    			item.allowDel = typeof item.allowDel == "undefined" ? "true" : ""+item.allowDel;
    		}
    		//字段 下拉框html
    		var selectConditionField = $('<select name="conditionField" lay-filter="conditionField"></select>');
			var items = instance.data;
			instance.fieldList = "";
			for(var i=0;i< items.length;i++){
				  if(items[i].show != "false"){
					  selectConditionField.append("<option value='"+items[i].field+"'>"+items[i].title+"</option>");
					  if(items[i].allowDel != "false"){
						  instance.fieldList += "," + items[i].field;
					  }
				  }
			}
    		instance.conditionFieldHtml = selectConditionField.prop("outerHTML");
    		//操作 下拉框html
    		var selectconditionOption = $('<select name="conditionOption" lay-filter="conditionOption"></select>');
    		selectconditionOption.append("<option value='equal'>等于</option>");
    		selectconditionOption.append("<option value='like'>包含</option>");
    		selectconditionOption.append("<option value='between'>范围</option>");
    		selectconditionOption.append("<option value='start'>开头字符</option>");
    		selectconditionOption.append("<option value='end'>结尾字符</option>");
    		selectconditionOption.append("<option value='unequal'>不等于</option>");
    		selectconditionOption.append("<option value='empty'>为空</option>");
    		instance.conditionOptionHtml = selectconditionOption.prop("outerHTML");
    		//缓存实例
    		this.cacheInstance[config.instanceName] = instance;
    		instance.setCondition([]);
    		return instance;
    	}
    };
    var editor = {
    		/***
    		 * 生成DOM对象ele,jquery封装返回$(ele)
    		 * return ele,或者$(ele)
    		 */
    		createElement:function(item){
				return $("<div></div>");
    		}
    		/***
    		 * 初始值填充DOM
    		 * ele createElement 生成的对象
    		 * val 值
    		 */
    		,fillElement:function(ele, val){
    			$(ele).val(val.value);
    			$(ele).attr("oldVal",val.value);
    		}
    		/***
    		 * 用于ajax请求提交的参数值
    		 */
    		,getRequestValue:function(ele){
    			return $(ele).val();
    		}
    		/***
    		 * 查询条件中显示的值,默认与getRequestValue一致
    		 */
    		,getRequestText:function(ele){
    			return this.getRequestValue(ele);
    		}
    		/***
    		 * ele创建后渲染
    		 */
    		,render:function(ele){
    		}
    };
    /***
     * 创建一个编辑器。
     * editorName 编辑器名称
     */
    dynamicCondition.createEditor = function(editorName){
    	dynamicCondition.editor[editorName] = {};
    	$.extend( dynamicCondition.editor[editorName] , editor);
    	return dynamicCondition.editor[editorName];
    }
    //定义文本编辑器
    var editorText = dynamicCondition.createEditor("text");
    $.extend( editorText ,{
		    createElement:function(item){
		    	var inputJq = $('<input type="text" class="layui-input" placeholder="" />');
				  if(item.layVerify){
					  inputJq.attr("lay-verify", item.layVerify);
				  }
				return inputJq;
			}
    });
    
    //定义下拉框编辑器
    var editorSelect = dynamicCondition.createEditor("select");
    $.extend( editorSelect ,{
    		createElement:function(item){
    			var selectHtml;
				if($(item.templet).is("select")){
					selectHtml = $(item.templet).prop("outerHTML");
				}else{
					selectHtml = $(item.templet).html();
				}
				return $(selectHtml);
    		}
		    ,getRequestText:function(ele){
				return  ele.find("option:selected").text();
			}
    });
    //定义日期编辑器
    var editorDate = dynamicCondition.createEditor("date");
    $.extend( editorDate ,{
    		createElement:function(item){
    			var dateType = item.dateType || "date";
    			var dateJq = $('<input type="text" class="layui-input" placeholder="" />');
    			dateJq.attr("date-type",dateType);
				  if(item.layVerify){
					  dateJq.attr("lay-verify",item.layVerify);
				  }
				  laydate.render({
					    elem: dateJq[0]
					    ,type: dateType
				  });
				return dateJq;
    		}
    });
    
	function removeOption(selDom,valList){
		valList = ","+valList + ",";
		var opJqs = $(selDom).find("option").each(function() {
			if(valList.indexOf(","+ $(this).val() + ",") == -1){
				$(this).remove();
			}
		});
	}
    exports(MOD_NAME, dynamicCondition);
})