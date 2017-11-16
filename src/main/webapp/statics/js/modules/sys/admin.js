$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/admin/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
            { label: '所属部门', name: 'deptName', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '积分账户', name: 'integralAddress',hidden:true , width: 95},
			{ label: '操作', name: 'integralAddressView', width: 55, formatter: function(value, options, row){
				return row.integralAddress === null ? 
						'<span class="label label-warning">未绑定</span>' :
						'<span class="btn btn-small btn-info pointer" onclick="vm.balance('+row.userId+')">余额</span>';
			}},	
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 85}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});
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
        showList: true,
        integralInfo:false,
        formInfo:false,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        },
        inegralaccount:{
        	userId:null,
        	password:null,
        	prikey:null}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.integralInfo=false;
            vm.formInfo=true;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};

            //获取系统角色信息
            this.getSystemRoleList();

            vm.getDept();
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
        update: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.integralInfo=false;
            vm.formInfo=true;
            vm.title = "修改";

            vm.getUser(userId);
            //获取系统角色信息
            this.getSystemRoleList();
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/admin/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/admin/save" : "sys/admin/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
        getUser: function(userId){
            $.get(baseURL + "sys/admin/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.password = null;

                vm.getDept();
            });
        },
        getSystemRoleList: function(){
            $.get(baseURL + "sys/role/select/system", function(r){
                vm.roleList = r.list;
            });
        },
        binding: function(){
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }
            vm.inegralaccount.userId = userId;
            vm.inegralaccount.prikey = null;
            vm.inegralaccount.password = null;
            vm.showList = false;
            vm.formInfo=false;
            vm.integralInfo=true;
            vm.title = "绑定积分账户";
            vm.getUser(userId);
        },
        createkeystorebyprikey: function () {
            var url = "sys/admin/binding";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.inegralaccount),
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
        balance: function (rowid) {
            var rowData = getRowData(rowid);
            console.log(rowData.integralAddress);
        	$.get(baseURL + "token/integral/"+rowData.integralAddress, function(r){
    			layer.open({
    				type: 1,
    				//skin: 'layui-layer-molv',
    				title: "福利积分",
    				area: ['450px', '150px'],
    				shadeClose: true,
    				content: '<label class="layui-form-label"  style="width:90px">积分余额:</label><label class="layui-form-label laber-account">'+ r.integralBalance +'</label>'
    				//content: jQuery("#integralLayer")
    			});   
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
        },
        reload: function () {
            vm.showList = true;
            vm.integralInfo=false;
            vm.formInfo=false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
        }
    }
});