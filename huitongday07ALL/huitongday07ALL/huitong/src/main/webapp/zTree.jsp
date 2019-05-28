<%@ page language="java" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${ctx}/staticfile/components/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/staticfile/components/zTree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/staticfile/components/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/staticfile/components/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
<SCRIPT type="text/javascript">

		//zTree树的配置文件
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		//使用简单的array数组
		var zNodes = [
		             // id 是zTree树必须要的属性，
		             //是一条数据的唯一标示
		             // pId 表示 父节点的id的值 ， 可以不写 ，
		             //如果不写，默认同一级目录 
		             
                    {"id":1, "pId":0, "name":"仓库管理",checked:true},
                    {"id":11, "pId":0, "name":"货运管理"},
                    {"id":12, "pId":0, "name":"合同管理"},
                    {"id":111, "pId":0, "name":"财务管理",checked:true}
		            ];
		
		//相当于$(function () {})
		//主要用于数据的初始化操作		
		$(document).ready(function(){
			//zTree树的初始化函数：
			//参数一：id为htZtree的<ul>标签
			//参数二：setting 为 zTree树的 配置说明
			//参数三：zNodes 为zTree树 要展示的数据 
			$.fn.zTree.init($("#htZtree"), setting, zNodes);
			//表示 array数组的数据 转换成了 zTreeObject
			var zTreeObj = $.fn.zTree.getZTreeObj("htZtree");
			//当页面加载时，是否显示全部的节点信息
			//true 表示 展示全部节点
			//false 表示 只展示父节点
			zTreeObj.expandAll(true);		//展开所有树节点
		});
		
		
		
	</SCRIPT>

</head>

<body>
<h1>Ztree入门教例</h1>
<div style="padding:30px;">
		<ul id="htZtree" class="ztree"></ul>
</div>

</body>
</html>

