/**
 * @Name：layui.utils 通用工具
 * @Author：vindell
 * @License：MIT
 */
layui.define('jquery', function(exports) {
	
	"use strict";
  	var $ = layui.$;
  	
  	//方便js调用中获得调用方法的event对象
	$.event.get = function(){
		if(!$.browser.msie){
			if (window.event) {
				return window.event;
			}
			var o = arguments.callee.caller;
			var e;
			var n = 0;
			while (o != null && n < 40) {
				e = o.arguments[0];
				if (e && (e.constructor == Event || e.constructor == MouseEvent)) {
					return $.event.fix(e);
				}

				n++;
				o = o.caller
			}
			return $.event.fix(e);

		}else{
			return $.event.fix(window.event);
		}
	};
	
	// 粘贴事件监控
	$.fn.pasteEvents = function( delay ) {
	    if (delay == undefined) delay = 10;
	    return $(this).each(function() {
	        var $el = $(this);
	        $el.on("paste", function() {
	            $el.trigger("prepaste");
	            setTimeout(function() { $el.trigger("postpaste"); }, delay);
	        });
	    });
	};
	
  	$.isHTMLElement = function(elem){
  		// 首先要对HTMLElement进行类型检查，因为即使在支持HTMLElement
  		// 的浏览器中，类型却是有差别的，在Chrome,Opera中HTMLElement的
  		// 类型为function，此时就不能用它来判断了
  		return (( typeof HTMLElement === 'object' ) ? function(){
	            return elem instanceof HTMLElement;
	        } :
	        function(){
	            return elem && typeof elem === 'object' && elem.nodeType === 1 && typeof elem.nodeName === 'string';
	        }).call(this);
  	};
  
  	$.fn.isHTMLElement = function(){
  		return $.isHTMLElement(this[0]);
  	};
  
  	$.extend({
		defined : function(obj) {
			return ( typeof obj != 'undefined' && obj != 'undefined' && obj != null && obj != 'null');
		},
		founded: function(obj) {
			if($.defined(obj)){
				// "boolean", "string", "number","object", "function" ,"undefined"
				if(typeof obj == 'boolean'){
					return obj;
				}else if(typeof obj == 'string'){
					return $.trim(obj).length > 0;
				}else if(jQuery.isArray(obj)){// "array",
					return jQuery.merge([],obj).length > 0;
				}else if($.isPlainObject(obj)){
					return !$.isEmptyObject(obj);
				}else{
					// number,object,function
					return true;
				}
			}else{ 
				return false;
			}
		},
		getTextLength:function(value){
			var length = value.length;
			for ( var i = 0; i < value.length; i += 1) {
				if (value.charCodeAt(i) > 127) {
					length += 1;
				}
			}
			return length;
		}
  	});
  
  	$.fn.extend({
		defined : function() {
			return $(this).size() != 0;
		},
		founded: function() {
			var val = $(this).is("select") ?  $(this).getSelected().val() : $(this).val();
			return $(this).defined() && $.founded(val);
		},
		// 禁用
		disabled: function() {
			return $(this).filter(":input").each(function(){
				$(this).prop("disabled",true).addClass("layui-disabled").data("disabled",true);
			});
		},
		// 是否禁用
		isDisabled: function() {
			return !$(this).isEnabled();
		},
		// 解除禁用
		enabled: function() {
			return $(this).filter(":input").each(function(){
				$(this).prop("disabled",false).removeClass("layui-disabled").removeData("disabled");
			});
		},
		// 是否可用
		isEnabled: function() {
			return ($(this).prop("disabled") == false && !$(this).hasClass("layui-disabled") && !$(this).data("disabled") === true)
		},
		attrs: function(){
			var attrs = $(this)[0].attributes;
			var attributes = {};
			for(var i = 0; i < attrs.length ; i+=1){   
				var att = attrs[i];    
				if(att.specified)  {  
					attributes[att.name] = att.value;
				}
			}
			return attributes;
		},
		// 禁止右键菜单
		disableContextMenu : function(){
			$(this).each(function(index,element){
				$(element).bind("contextmenu",function(){
			        return false;   
			    });
			});
		},
		getValueLength:function(){
			return $.getTextLength($(this).val());
		},
		getTextLength:function(value){
			$.getTextLength($(this).text())
		}
	});
  	
  	// 暴露接口
  	exports('utils', {
  		
  		
  	})
  
});