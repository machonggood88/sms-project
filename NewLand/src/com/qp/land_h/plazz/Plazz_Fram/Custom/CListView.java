package com.qp.land_h.plazz.Plazz_Fram.Custom;

import java.util.List;

import android.content.Context;
import Lib_System.View.CViewEngine;

public abstract class CListView extends CViewEngine {

	List<Object> m_DataList;

	/** 每页数目 **/
	public int m_nPerPageCount;
	/** 每页列数 **/
	public int m_nRowCount;
	/** 每页行数 **/
	public int m_nLineCount;

	/** 当前页码 **/
	public int m_nCurrentPage;
	/** 最大页数 **/
	public int m_nMaxPage;
	
	/** 翻页时间 **/
	public int m_nPageTime;

	public CListView(Context context) {
		super(context);
	}

	/** 下一页判断 **/
	public boolean HasNetxt(){
		if (m_nCurrentPage + 1 < m_nMaxPage)
			return true;
		return false;
	}
	
	/** 上一页判断 **/
	public boolean HasFront(){
		if (m_nCurrentPage >= 1)
			return true;
		return false;
	}
	
	/** 下一页 **/
	public boolean PageNext() {
		if (HasNetxt()) {
			m_nCurrentPage++;
			return true;
		}
		return false;
	}

	/** 上一页 **/
	public boolean PageFront() {
		if (HasFront()) {
			m_nCurrentPage--;
			return true;
		}
		return false;
	}
	
	/** 获取数据 **/
	public Object GetListData(int index,boolean Current){
		if(m_DataList==null||m_DataList.isEmpty())
			return null;
		
		int selectindex = -1;
		
		if(Current){
			if(index<m_nPerPageCount){
				selectindex = m_nCurrentPage*m_nPerPageCount+index;
			}
		}else{
			if(index>=0&&index<m_DataList.size())
				selectindex = index;
		}
		
		if(selectindex>=0&&selectindex<m_DataList.size()){
			return m_DataList.get(selectindex);
		}
		return null;
	}
	
	public abstract int GetSelectIndex(int x, int y);
	
}
