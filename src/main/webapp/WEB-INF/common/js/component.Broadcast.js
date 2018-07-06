//顶层窗口作为中转站
if (window === window.top) {
    window.children = [];
    window.registerBroadcast = function (w) {
        if(window.children.indexOf(w)===-1) {
            window.children.push(w);
        }
    }
} else {
    window.top.registerBroadcast(window);
}
//所有窗口监听广播
window.addEventListener("message", function (evt) {
    var origin = evt.origin || evt.originalEvent.origin;
    //是同源页面才处理
    if (origin !== window.location.origin) {
        return;
    }
    var data = evt.data;
    var from = evt.source;
    var messageType = data.type;
    var args = data.args;
    if (window.top === window) {
        //顶层窗口广播给其他子window
        window.children.forEach(function (w) {
            w.postMessage(data, "/");
        });
    }

    //广播回调
    var handlersMap=window.onbroadcast.handlersMap;
    if(handlersMap){
        var handlers=handlersMap[messageType];
        if(handlers){
            for(var i=0,z=handlers.length,handler;i<z;i++){
                try {
                    handlers[i].apply(window, args);
                }catch(e){
                    console.log(e);
                }
            }
        }
    }

}, false);
/**
 * 所有窗口广播注册
 */
window.onbroadcast=function (messageType,handler) {
    var handlersMap=window.onbroadcast.handlersMap;
    if(!handlersMap){
        handlersMap=window.onbroadcast.handlersMap={};
    }
    var handlers=handlersMap[messageType];
    if(handlers){
        handlers.push(handler);
    }else{
        handlers=handlersMap[messageType]=[handler];
    }
}
/**
 * 发送广播
 * @param type
 * @param args
 */
window.broadcast = function (type, args) {
    window.top.postMessage({
        type: type,
        args: Array.prototype.slice.call(arguments, 1)
    }, "/");
};