;+function (window) {

    var saveUserData=function (data) {
        for(var key in data){
            if(data.hasOwnProperty(key)){
                putSessionData("user."+key,data[key]);
            }
        }
    };
    var getUserData=function (key) {
       var s=window.top.sessionStorage;
       var storageKey="user.";
       var user={};
       for(var i=s.length;i-->0;){
           var key=s.key(i);
           if(startWith(key,"user.")){
                user[key.substr(5)]=s[key];
           }
       }
       return user;
    };
    var startWith=function (a,b) {
        for(var i=0,z=b.length;i<z;i++){
            if(a.charAt(i)!==b.charAt(i)){
                return false;
            }
        }
        return true;
    }

    var getSessionData=function (key) {
        return window.top.sessionStorage.getItem(key);
    }
    var putSessionData=function (key,value) {
        window.top.sessionStorage.setItem(key,value);
    }



    //export
    window.session={
        put:putSessionData,
        get:getSessionData,
        saveCurrentUser:saveUserData,
        getCurrentUser:getUserData,
        getUserId:function () {
            return getSessionData("user.id");
        },
        getUserName:function () {
            return getSessionData("user.name");
        }
    };

}(window);