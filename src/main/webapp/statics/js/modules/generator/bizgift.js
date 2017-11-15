$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'bizgift/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'giftId', index: 'gift_id', width: 50, key: true },
			{ label: '礼品名称', name: 'giftName', index: 'gift_name', width: 80 }, 			
			{ label: '积分兑换价格', name: 'exchangePrice', index: 'exchange_price', width: 80 }, 			
			{ label: '数量', name: 'total', index: 'total', width: 80 }, 			
			{ label: '简要描述', name: 'simpleDescribe', index: 'simple_describe', width: 80 }, 			
			{ label: '上架标志', name: 'isMarketable', index: 'is_marketable', width: 80,formatter: function(value, options, row){
				return value === 0 ? 
						'<span class="label label-danger">下架</span>' : 
						'<span class="label label-success">上架</span>';
			}  }, 			
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
		bizGift: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.bizGift = {isMarketable:1};
		},
		update: function (event) {
			var giftId = getSelectedRow();
			if(giftId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(giftId)
		},
		saveOrUpdate: function (event) {
			var url = vm.bizGift.giftId == null ? "bizgift/save" : "bizgift/update";
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
		del: function (event) {
			var giftIds = getSelectedRows();
			if(giftIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "bizgift/delete",
				    contentType: "application/json",
				    data: JSON.stringify(giftIds),
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
		getInfo: function(giftId){
			$.get(baseURL + "bizgift/info/"+giftId, function(r){
                vm.bizGift = r.bizGift;
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