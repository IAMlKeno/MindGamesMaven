/*!
 * jQuery UI Touch Punch 0.2.3
 *
 * Copyright 2011–2014, Dave Furfero
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Depends:
 *  jquery.ui.widget.js
 *  jquery.ui.mouse.js
 */
!function(t){function o(t){t.preventDefault(),t.stopPropagation()}function e(t,o){if(!(t.originalEvent.touches.length>1)){t.preventDefault();var e=t.originalEvent.changedTouches[0],u=document.createEvent("MouseEvents");u.initMouseEvent(o,!0,!0,window,1,e.screenX,e.screenY,e.clientX,e.clientY,!1,!1,!1,!1,0,null),t.target.dispatchEvent(u)}}if(t.support.touch="ontouchend"in document,t.support.touch){var u,n=t.ui.mouse.prototype,c=n._mouseInit,i=n._mouseDestroy;n._touchStart=function(t){var n=this;!u&&n._mouseCapture(t.originalEvent.changedTouches[0])&&(n._touchMoved=!1,window.addEventListener("contextmenu",o),n._touchStartTimeout=setTimeout(function(){u=!0,n._touchStartTimeout=null,n._touchMoved||(e(t,"mouseover"),e(t,"mousemove"),e(t,"mousedown"))},window.touchPunchDelay))},n._touchMove=function(t){this._touchMoved=!0,u&&e(t,"mousemove")},n._touchEnd=function(t){this._touchStartTimeout&&(clearTimeout(this._touchStartTimeout),this._touchStartTimeout=null),window.removeEventListener("contextmenu",o),u&&(e(t,"mouseup"),e(t,"mouseout"),this._touchMoved||e(t,"click"),u=!1)},n._mouseInit=function(){var o=this;o.element.bind({touchstart:t.proxy(o,"_touchStart"),touchmove:t.proxy(o,"_touchMove"),touchend:t.proxy(o,"_touchEnd")}),c.call(o)},n._mouseDestroy=function(){var o=this;o.element.unbind({touchstart:t.proxy(o,"_touchStart"),touchmove:t.proxy(o,"_touchMove"),touchend:t.proxy(o,"_touchEnd")}),i.call(o)}}}(jQuery);