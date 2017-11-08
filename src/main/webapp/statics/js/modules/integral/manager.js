$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'integral/manager/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 20, key: true },
			{ label: '用户名', name: 'username', width: 40 },
            { label: '所属部门', name: 'deptName', width: 40 },
			{ label: '积分账户', name: 'integralAddress', width: 70},
			{ label: '操作', name: 'integralAddressView', width: 70, formatter: function(value, options, row){
				return row.integralAddress === null ? 
						'<span class="label label-danger">未绑定</span>' :
						'<span class="layui-btn layui-btn-success pointer" onclick="vm.balance('+row.userId+')">余额</span><span class="layui-btn layui-btn-danger pointer" onclick="vm.sendIntegral('+row.userId+')">发放积分</span>';
			}},	
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 60}
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
	 el:'#app',
	    data:{
	        q:{
	            username: null
	        },
	        showList: true,
	        integralbalance:0
	    },
	    methods: {
	    	search: function () {
	            vm.reload();
	        },
	        balance: function (rowid) {
	            var rowData = getRowData(rowid);
	            console.log(rowData.integralAddress);
	        	$.get(baseURL + "token/integral/"+rowData.integralAddress, function(r){
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
	        sendIntegral :function (rowid){
	        	var rowData = getRowData(rowid);
	        	vm.showList = false;
	        },
	        reload: function () {
	            var page = $("#jqGrid").jqGrid('getGridParam','page');
	            $("#jqGrid").jqGrid('setGridParam',{
	                postData:{'username': vm.q.username},
	                page:page
	            }).trigger("reloadGrid");
	        }	    	
	    }
});