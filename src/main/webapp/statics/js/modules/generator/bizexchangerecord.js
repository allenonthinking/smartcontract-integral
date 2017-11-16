$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bizexchangerecord/list',
        datatype: "json",
        colModel: [			
			//{ label: 'id', name: 'id', index: 'id', width: 10, key: true },
			{ label: '交易ID', name: 'txId', index: 'tx_id', width: 80,formatter: function(value, options, row){
				return value === null ? '<span class="label label-danger">积分未转账</span>' : value;
			} }, 			
			{ label: '转账数额', name: 'transferValue', index: 'transfer_value', width: 10 }, 			
			{ label: '兑换数量', name: 'amount', index: 'amount', width:10 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 40 }, 			
			{ label: '账户地址', name: 'address', index: 'address', width: 60 },
			{ label: '操作', name: 'controller', width: 35, formatter: function(value, options, row){
				return row.txId === null ? 
						'<span class="label label-warning">积分未转账</span>' :
						'<span class="btn btn-small btn-info pointer" onclick="vm.status('+row.id+')">查看状态</span>';
			}}				
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
		bizExchangeRecord: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
	    status: function (rowid) {
	        var rowData = getRowData(rowid);
	    	$.get(baseURL + "bctransaction/info/"+rowid, function(r){
	    			var statusView = "未知";
	    			if(r.bcTransaction.status == 0){
	    				statusView =  '<span class="label label-default">未知</span>';
	    			}
	    			if(r.bcTransaction.status == -1){
	    				statusView =  '<span class="label label-dange">失败</span>';
	    			}
	    			if(r.bcTransaction.status < 12 ){
	    				statusView =  '<span class="label label-info">'+r.bcTransaction.status+'/12</span>';
	    			}
	    			if(r.bcTransaction.status >= 12){
	    				statusView =  '<span class="label label-success">成功</span>';
	    			}
				layer.open({
					type: 1,
					//skin: 'layui-layer-molv',
					title: "兑换状态",
					area: ['450px', '110px'],
					shadeClose: true,
					content: '<div class="sysNotice col"><table class="layui-table"><colgroup><col width="150"><col></colgroup><tbody><tr><td>兑换状态:</td><td class="status">'+ statusView +'</td></tr></tbody></table></div>'
				});   
			});	            
	    },		
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bizExchangeRecord = {};
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
			var url = vm.bizExchangeRecord.id == null ? "bizexchangerecord/save" : "bizexchangerecord/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.bizExchangeRecord),
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
				    url: baseURL + "bizexchangerecord/delete",
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
			$.get(baseURL + "bizexchangerecord/info/"+id, function(r){
                vm.bizExchangeRecord = r.bizExchangeRecord;
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