$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'integral/manager/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 20, key: true },
			{ label: '用户名', name: 'username', width: 30 },
            { label: '所属部门', name: 'deptName', width: 40 },
			{ label: '积分账户', name: 'integralAddress', width: 95},
			{ label: '操作', name: 'integralAddressView', width: 55, formatter: function(value, options, row){
				return row.integralAddress === null ? 
						'<span class="label label-warning">未绑定</span>' :
						'<span class="btn btn-small btn-info pointer" onclick="vm.balance('+row.userId+')">余额</span>&nbsp;<span class="btn btn-small btn-danger pointer" onclick="vm.sendIntegral('+row.userId+')">发放积分</span>';
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
	        title:null,
	        showList: true,
	        integralObject:{
	            userId:null,
	            username:null,
	            balance:0,
		        address:null,
		        password:null
	        },
	        integralBalance:0 
	    },
	    methods: {
	    	search: function () {
	            vm.reload();
	        },
	        balance: function (rowid) {
	            var rowData = getRowData(rowid);
	            console.log(rowData.integralAddress);
	        	$.get(baseURL + "token/integral/"+rowData.integralAddress, function(r){
	        		vm.integralBalance = r.integralbalance;
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
	        sendIntegral :function (rowid){
	        	var rowData = getRowData(rowid);
	        	vm.title="积分发放";
	        	vm.integralObject = {userId:rowData.userId, username:rowData.username, balance:0, address:rowData.integralAddress};
	        	vm.showList = false;
	        },
	        saveBalance	:function(){
	            if(vm.validator()){
	                return ;
	            }
	            var url = "integral/manager/savebalance";
	            $.ajax({
	                type: "POST",
	                url: baseURL + url,
	                contentType: "application/json",
	                data: JSON.stringify(vm.integralObject),
	                success: function(r){
	                    if(r.code === 0){
	                        alert('记录交易ID:'+r.txid, function(){
	                            vm.reload();
	                        });
	                    }else{
	                        alert(r.msg);
	                    }
//	                    vm.reload();
	                }
	            });	        	
	        },
	        validator: function () {
	        	var r = /^\+?[1-9][0-9]*$/;
	            if(!r.test(vm.integralObject.balance)){
	                alert("1-100间正整数");
	                return true;
	            }
	            if(vm.integralObject.balance > 100){
	            	 alert("最大分配100");
		             return true;
	            }
	        },	        
	        reload: function () {
	        	vm.showList = true;
	            var page = $("#jqGrid").jqGrid('getGridParam','page');
	            $("#jqGrid").jqGrid('setGridParam',{
	                postData:{'username': vm.q.username},
	                page:page
	            }).trigger("reloadGrid");
	        }	    	
	    }
});