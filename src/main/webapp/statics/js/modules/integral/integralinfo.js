var vm = new Vue({
    el:'#rrapp',
    data:{
        showInfo: true,
        title:null,
        integralinfo:{
        	name:null,
        	symbol:null,
        	address:null,
        	total:null
        }
    },
    methods: {
        getIntegralInfo: function(){
            $.get(baseURL + "integral/manager/integralinfo", function(r){
            	vm.title="积分详情";
                vm.integralinfo = r.integralInfo;
            });
        }    
    }
});

$(function () {
	vm.getIntegralInfo();
	vm.showInfo = true;
});