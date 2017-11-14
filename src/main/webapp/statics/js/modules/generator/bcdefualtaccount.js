$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bcdefualtaccount/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'accountId', index: 'account_id', width: 20, key: true },
			{ label: '账户名', name: 'accountName', index: 'account_name', width: 40 }, 			
			{ label: '账户地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '状态 ', name: 'status', index: 'status', width: 40,formatter: function(value, options, row){
				return value === 0 ? 
						'<span class="label label-danger">禁用</span>' : 
						'<span class="label label-success">正常</span>';
			} }, 			
			{ label: '类型  ', name: 'type', index: 'type', width: 40,formatter: function(value, options, row){
				if(row.type === 1){
					return '<span class="btn btn-small btn-warning">基础发放</span>';
				}				
				if(row.type === 2){
					return '<span class="btn btn-small btn-warning">积分发放</span>';
				}
			} }, 			
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
		updateflag:false,
		bcDefualtAccount: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.updateflag = false,
			vm.bcDefualtAccount = {status:1,type:2};
		},
		update: function (event) {
			var accountId = getSelectedRow();
			if(accountId == null){
				return ;
			}
			vm.showList = false;
			vm.updateflag = true,
            vm.title = "修改";
            
            vm.getInfo(accountId)
		},
		saveOrUpdate: function (event) {
			var url = vm.bcDefualtAccount.accountId == null ? "bcdefualtaccount/save" : "bcdefualtaccount/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.bcDefualtAccount),
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
				    url: baseURL + "bcdefualtaccount/delete",
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
			$.get(baseURL + "bcdefualtaccount/info/"+accountId, function(r){
                vm.bcDefualtAccount = r.bcDefualtAccount;
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