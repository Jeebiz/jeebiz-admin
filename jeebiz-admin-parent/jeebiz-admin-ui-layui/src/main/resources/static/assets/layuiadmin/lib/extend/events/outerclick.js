/**
 * 整合 jquery.fn.outerclick
 * @returns
 */
layui.define('jquery', function(exports) {
	"use strict";
  	var $ = layui.$;
  	
  	/*
  	 * The outerclick event is fired when an element outside of the target element is clicked.
  	 * 
  	 * Usage:
  	 * $(selector).bind("outerclick", fn);   // Bind the function fn to the outerClick event on each of the matched elements.
  	 * $(selector).outerclick(fn);           // Bind the function fn to the outerClick event on each of the matched elements.
  	 * $(selector).trigger("outerclick");    // Trigger the outerClick event on each of the matched elements.
  	 * $(selector).outerclick();             // Trigger the outerClick event on each of the matched elements.
  	 * $(selector).unbind("outerclick", fn); // Unbind the function fn from the outerClick event on each of the matched elements.
  	 * $(selector).unbind("outerclick");     // Unbind all outerClick events from each of the matched elements.
  	 */
  	
	var Outerclick,
		$html = $('html'),
		handler = typeof $().on ? 'on' : 'bind';

	Outerclick = function ($elementTarget, functionCall) {
		this.$elementTarget = $elementTarget;
		this.functionCall = functionCall;

		this.init();
	};
	Outerclick.prototype = {
		init: function () {
			this.bindEvent();
		},
		bindEvent: function () {
			$html[handler]('click', $.proxy(function (e) {
				this.checkOuterclick(e);
			}, this));
		},
		checkOuterclick: function (e) {
			var
				clickX = e.pageX,
				clickY = e.pageY,
				offsetElementTarget = this.$elementTarget.offset(),
				offsetTopElementTarget = offsetElementTarget.top,
				offsetBottomElementTarget = offsetTopElementTarget + this.$elementTarget.height(),
				offsetLeftElementTarget = offsetElementTarget.left,
				offsetRightElementTarget = offsetLeftElementTarget + this.$elementTarget.width();

			if (!(
				clickY > offsetTopElementTarget &&
				clickY < offsetBottomElementTarget &&
				clickX > offsetLeftElementTarget &&
				clickX < offsetRightElementTarget
			)) {
				this.functionCall(e);
			}
		}
	};
	
	$.fn.outerclick = function (functionCall) {
		this.each(function () {
			new Outerclick($(this), functionCall);
		});
	};
	exports('outerclick', {});
});