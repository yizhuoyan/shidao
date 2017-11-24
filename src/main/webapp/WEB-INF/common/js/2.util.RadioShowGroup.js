;+function (window,document) {
    /**
     * 把多个view放入一组，进行批量或排他型处理（如一个view显示其他隐藏）
     * 一组view中还可以分为小组，小组第一个元素位小组长，小组内view同步处理
     * @param view 多个view，如果是小组，则传入数组或为数组，如[1,[2,3],4,...]
     * @constructor
     */
    var ViewGroup=function (view /*,view,...*/) {
        this.views=[];
        privateMethod.init.apply(this,arguments);
    };

    var privateMethod={
        init:function () {
                this.add.apply(this, arguments);
        },

        showView:function (view) {
            if(view){
                if(Array.isArray(view)){
                    for(var i=view.length;i-->0;){
                        privateMethod.showView(view[i]);
                    }
                }else {
                    if (view instanceof window["jQuery"]) {
                        view.show();
                    } else if (view.style.display === "none") {
                        if (!view._oldDisplay) {
                            view._oldDisplay = " ";
                        }
                        view.style.display = view._oldDisplay;
                    }
                }

            }

        },
        hideView:function (view) {
            if(view){
                if(Array.isArray(view)){
                    for(var i=view.length;i-->0;){
                        privateMethod.hideView(view[i]);
                    }
                }else {
                    if (view instanceof window["jQuery"]) {
                        view.hide();
                    } else {
                        if (view.style.display !== "none") {
                            view._oldDisplay = view.style.display;
                            view.style.display = "none";
                        }
                    }
                }
            }

        }
    };
    ViewGroup.prototype.add=function (view/*,views,view*/) {
        if(arguments.length>0) {
            //转化为数组
           var views=Array.prototype.slice.call(arguments).map(function (item) {
                //如果是jquery对象，转换为底层对象
                if(item===null)return null;
                if(Array.isArray(item)){
                    return item;
                }else{
                    return [item];
                }
            }).filter(function (item) {
                return item!==null;
            });

           for(var i=0,z=views.length;i<z;i++){
               this.views.push(views[i]);
           }
        }
        return this;
    };
    /**
     * 仅显示
     * @param target
     */
    ViewGroup.prototype.onlyShow=function () {
        var views=this.views;
        for(var i=arguments.length;i-->0;){
            for(var j=views.length,view;j-->0;){
                view=views[j];
                if (view[0]===arguments[i]) {//不是
                    privateMethod.showView(view);
                } else {
                    privateMethod.hideView(view);
                }
            }
        }
        return this;
    };

    //export
    window.ViewGroup=ViewGroup;

}(window,document);