$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bcadminaccount/list',
        datatype: "json",
        colModel: [			
			{ label: 'accountId', name: 'accountId', index: 'account_id', width: 50, key: true },
			{ label: '账户地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: 'keystore', name: 'keystore', index: 'keystore', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		bcAdminAccount: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bcAdminAccount = {};
		},
		update: function (event) {
			var accountId = getSelectedRow();
			if(accountId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(accountId)
		},
		saveOrUpdate: function (event) {
			var url = vm.bcAdminAccount.accountId == null ? "bcadminaccount/save" : "bcadminaccount/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.bcAdminAccount),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var accountIds = getSelectedRows();
			if(accountIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "bcadminaccount/delete",
				    contentType: "application/json",
				    data: JSON.stringify(accountIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(accountId){
			$.get(baseURL + "bcadminaccount/info/"+accountId, function(r){
                vm.bcAdminAccount = r.bcAdminAccount;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});