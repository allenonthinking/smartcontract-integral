$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bctransaction/list',
        datatype: "json",
        colModel: [			
			//{ label: 'id', name: 'id', index: 'id', width: 10, key: true },
			{ label: '交易ID', name: 'txId', index: 'tx_id', width: 80 }, 			
			{ label: '状态 ', name: 'status', index: 'status', width: 20 ,formatter: function(value, options, row){
				if(row.status === 0){
					return '<span class="label label-default">未知</span>';
				}				
				if(row.status === -1){
					return '<span class="label label-dange">失败</span>';
				}
				
				if(row.status < 12 && row.status >0 ){
					return '<span class="label label-info">'+row.status+'/12</span>';
				}
				
				if(row.status >= 12 ){
					return '<span class="label label-success">成功</span>';
				}
			}}, 			
			{ label: '发起人地址', name: 'fromAddress', index: 'from_address', width: 80 }, 			
			{ label: '接收人地址', name: 'toAddress', index: 'to_address', width: 80 }, 			
			{ label: '合约地址', name: 'contractAddress', index: 'contract_address', width: 80 }, 			
			{ label: '金额', name: 'amount', index: 'amount', width: 30 }, 			
			{ label: '类型 ', name: 'type', index: 'type', width: 20,formatter: function(value, options, row){
				if(row.type === 1){
					return '<span class="label label-primary">普通转账</span>';
				}				
				if(row.type === 2){
					return '<span class="label label-info">积分转账</span>';
				}
				if(row.type === 3){
					return '<span class="label label-success">积分兑换</span>';
				}
			} }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        //multiselect: true,
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
		bcTransaction: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bcTransaction = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.bcTransaction.id == null ? "bctransaction/save" : "bctransaction/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.bcTransaction),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "bctransaction/delete",
				    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
			$.get(baseURL + "bctransaction/info/"+id, function(r){
                vm.bcTransaction = r.bcTransaction;
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