;+function (window, document, undefined) {
    "use strict";
    var defaultSetting = {
        dom: null,
        activeListener: function () {

        }
    };

    var TabLayout = function (setting) {
        this.setting = $.extend({}, defaultSetting, setting);
        //the dom
        this.dom;
        //the tabs map
        this.tabsMap;

        this._activeTab = null;

        privateMethod.init.call(this);

    };
    var privateMethod = {
        init: function () {
            var setting = this.setting;
            var dom = setting.dom;
            if (typeof dom === "string") {
                dom = document.querySelector(dom);
            }

            var tabContainerEL = dom.querySelector(".tabs");
            //add click listener
            tabContainerEL.addEventListener("click", privateMethod.handleTabClick);

            var tabELs = tabContainerEL.getElementsByTagName("a");
            var tabsMap = {};
            var activeTab;
            for (var i = tabELs.length, tab; i-- > 0;) {
                tab = privateMethod.createTab.call(this, tabELs[i]);
                tabsMap[tab.id] = tab;
                if (tabELs[i].classList.contains("active")) {
                    activeTab = tab;
                }
            }
            this.tabsMap = tabsMap;
            this.dom = dom;
            //active Tab
            this.activeTab(activeTab);
        },
        createTab: function (tabEL) {
            var tab = new Tab(this, tabEL);
            var href = tabEL.href;
            tab.id = href.substr(href.lastIndexOf("#") + 1);
            tab.name = tabEL.innerHTML;
            var tabViewEL = document.getElementById(tab.id);
            if (tabViewEL) {
                this.tabView = tab.createCardView(tabViewEL);
            } else {
                console.error("can not find the tab view of id " + tab.id);
                return;
            }
            return tab;
        },
        handleTabClick: function (evt) {
            evt.preventDefault();

            var target = evt.target;
            if (target.tagName === "A") {
                var a = target;
                var tab = a.model;
                var tabLayout = tab.tabLayout;
                tabLayout.activeTab(tab);
            }
        }
    };
    //publicMethod
    TabLayout.prototype = {
        active: function (tabId) {
            var tab = this.tabsMap[tabId];
            if (tab) {
                this.activeTab(tab);
            } else {
                console.log("can't find the tab " + tabId);
            }
        },
        activeTab: function (tab) {
            if (arguments.length === 0)return this._activeTab;
            else {
                if (!tab)return;
                if (this._activeTab) {
                    this._activeTab.active(false);

                }
                tab.active(true);
                this._activeTab = tab;
                this.setting.activeListener.call(this, tab.dom, tab.cardView.dom);
            }
        }
    };

    var Tab = function (tabLayout, dom) {
        this.tabLayout = tabLayout;
        this.dom = dom;
        this.dom.model = this;
        this.id = null;
        this.name = null;
        this.cardView = null;
        this._active = false;
        //init

    };

    Tab.prototype = {
        active: function () {
            if (arguments.length === 0)return this._active;
            if (arguments[0] === true) {
                this.dom.classList.add("active");
                this.cardView.active(true);
                this._active = true;
            } else {
                this.dom.classList.remove("active");
                this.cardView.active(false);
                this._active = false;
            }
        },
        createCardView: function (dom) {
            var view = new CardView(this, dom);
            this.cardView = view;
            return view;
        }
    };
    var CardView = function (tab, dom) {
        this.tab = tab;
        this.dom = dom;
        this.tabLayout = tab.tabLayout;
    };
    CardView.prototype.active = function () {
        if (arguments[0] === true) {
            this.dom.classList.add("active");
        } else {
            this.dom.classList.remove("active");
        }
    };


    //export

    window.TabLayout = TabLayout;

}(window, document);