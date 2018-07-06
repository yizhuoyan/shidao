;+function ($) {
    $.fn.extend({
        checked: function() {
            if(this.length===0)return this;

            if(arguments.length===0){//getter
                return this[0].checked;
            }
            var yesno=arguments[0];
            return this.each(function() { this.checked = yesno; });
        },
        readonly:function () {
            if(this.length===0)return this;

            if(arguments.length===0){//getter
                return this[0].readonly;
            }
            var yesno=arguments[0];
            return this.each(function() { this.readonly = yesno; });
        },
        disabled:function () {
            if(this.length===0)return this;

            if(arguments.length===0){//getter
                return this[0].disabled;
            }
            var yesno=arguments[0];
            return this.each(function () {
                this.disabled=yesno;
            });
        }
    });
}(jQuery);