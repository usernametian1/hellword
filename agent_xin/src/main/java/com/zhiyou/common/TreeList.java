package com.zhiyou.common;

import java.util.ArrayList;
import java.util.List;

import com.zhiyou.auth.dto.AuthMenuDto;

public class TreeList {
	private List<AuthMenuDto> resultNodes = new ArrayList<AuthMenuDto>();// 树形结构排序之后list内容
	private List<AuthMenuDto> nodes; // 传入list参数

	public TreeList() {
		super();
	}

	public TreeList(List<AuthMenuDto> nodes) {// 通过构造函数初始化
		this.nodes = nodes;
	}

	/**
	 * 构建树形结构list
	 * 
	 * @return 返回树形结构List列表
	 */
	public List<AuthMenuDto> buildTree() {
		for (AuthMenuDto node : nodes) {
			int id = node.getResourceId();
			if (node.getParentId() == -1) {// 通过循环一级节点 就可以通过递归获取二级以下节点
				resultNodes.add(node);// 添加一级节点
				build(node);// 递归获取二级、三级、。。。节点
			}
		}
		return resultNodes;
	}

	/**
	 * 递归循环子节点
	 *
	 * @param node
	 *            当前节点
	 */
	private void build(AuthMenuDto node) {
		List<AuthMenuDto> children = getChildren(node);
		if (!children.isEmpty()) {// 如果存在子节点
			for (AuthMenuDto child : children) {// 将子节点遍历加入返回值中
				resultNodes.add(child);
				build(child);
			}
		}
	}

	/**
	 * @param node
	 * @return 返回
	 */
	private List<AuthMenuDto> getChildren(AuthMenuDto node) {
		List<AuthMenuDto> children = new ArrayList<AuthMenuDto>();
		int id = node.getResourceId();
		for (AuthMenuDto child : nodes) {
			if (id == (child.getParentId())) {// 如果id等于父id
				children.add(child);// 将该节点加入循环列表中
			}
		}
		return children;
	}

}
