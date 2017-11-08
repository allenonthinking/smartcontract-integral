var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            username: null
        },
        showInfo: true,
        keystoreshow:false,
        title:null,
        roleList:{},
        integralshow:false,
        integral:{
        	address:null,
        	keystore:null    	
        },        
        newintegral:{
        	address:null,
        	keystore:null    	
        },
        inegralpassword:null,
        integralbalance:0,
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleName:null
        }
    },
    methods: {
        getOneSelf: function(){
            $.get(baseURL + "sys/user/info", function(r){
                vm.user = r.user;
                vm.user.password = null;
                vm.getDept();
            });
        },
        getRole: function(){
        	$.get(baseURL + "sys/role/oneself", function(r){
        		vm.user.roleName = r.role !=null ? r.role.roleName:"default";
        	});
        },
        getIntegral: function(){
        	$.get(baseURL + "integral/user/account", function(r){
        		if( r.integral !=null){
        			vm.integral= r.integral;
        			vm.integralshow = true;
        		}
        	});
        },
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);
                    vm.user.deptName = node.name;
                }
            })
        },
        binding: function(){
            vm.showInfo = false;
            vm.title = "绑定积分账户";
        },        
        showOneSelf: function () {
            vm.title = "个人信息";
            
            this.getOneSelf();
            //获取角色信息
            this.getRole();
            
            // 获取用户积分账户
            this.getIntegral();
        },
        queryintegral: function(){
        	$.get(baseURL + "token/integral/"+vm.integral.address, function(r){
        		vm.integralbalance=r.integralbalance;
    			layer.open({
    				type: 1,
    				skin: 'layui-layer-molv',
    				title: "福利积分",
    				area: ['450px', '150px'],
    				shadeClose: false,
    				content: jQuery("#integralLayer")
    			});   
			});
        },
        createkeystore: function () {
        	keystoreshow:true;
        	$.get(baseURL + "crypto/keystore/"+vm.inegralpassword, function(r){
        		vm.newintegral = r.integral;
        		console.log(r.integral.address);
        		console.log(r.integral.keystore);
        	});
        },
        
        bingdingintegralaccount: function () {
            var url = "integral/user/binding";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.newintegral),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;
                    layer.close(index);
                }
            });
        }        
    }
});

$(function () {
	vm.showOneSelf();
	vm.showInfo = true;
});