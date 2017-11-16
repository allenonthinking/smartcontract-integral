$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bizgift/exchange/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'giftId', index: 'gift_id', width: 50, key: true },
			{ label: '礼品名称', name: 'giftName', index: 'gift_name', width: 80 }, 			
			{ label: '积分兑换价格', name: 'exchangePrice', index: 'exchange_price', width: 80 }, 			
			{ label: '数量', name: 'total', index: 'total', width: 80 }, 			
			{ label: '简要描述', name: 'simpleDescribe', index: 'simple_describe', width: 80 }, 			
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
        multiboxonly:true,  
        oneselect :true,
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
        },
        beforeSelectRow: function(rowid, e)
        {
        	$("#jqGrid").jqGrid('resetSelection');
            return(true);
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		bizGift: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		exchange : function(){
			var giftId = getSelectedRow();
			$.get(baseURL + "bizgift/exchange/info/"+giftId, function(r){
				if(r.code != 0){
					alert(r.msg,function(index){
						vm.reload();
					});
				}else{
					vm.bizGift = r.bizGift;
					vm.showList = false;
					vm.title = "兑换礼品";
				}
            });
		},
		saveExchange: function (event) {
			var url = "bizgift/exchange/save";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
			    contentType: "application/json",
			    data: JSON.stringify(vm.bizGift),
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
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});