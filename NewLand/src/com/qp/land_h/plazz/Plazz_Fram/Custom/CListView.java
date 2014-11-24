package com.qp.land_h.plazz.Plazz_Fram.Custom;

import java.util.List;

import android.content.Context;
import Lib_System.View.CViewEngine;

public abstract class CListView extends CViewEngine {

	List<Object> m_DataList;

	/** ÿҳ��Ŀ **/
	public int m_nPerPageCount;
	/** ÿҳ���� **/
	public int m_nRowCount;
	/** ÿҳ���� **/
	public int m_nLineCount;

	/** ��ǰҳ�� **/
	public int m_nCurrentPage;
	/** ���ҳ�� **/
	public int m_nMaxPage;
	
	/** ��ҳʱ�� **/
	public int m_nPageTime;

	public CListView(Context context) {
		super(context);
	}

	/** ��һҳ�ж� **/
	public boolean HasNetxt(){
		if (m_nCurrentPage + 1 < m_nMaxPage)
			return true;
		return false;
	}
	
	/** ��һҳ�ж� **/
	public boolean HasFront(){
		if (m_nCurrentPage >= 1)
			return true;
		return false;
	}
	
	/** ��һҳ **/
	public boolean PageNext() {
		if (HasNetxt()) {
			m_nCurrentPage++;
			return true;
		}
		return false;
	}

	/** ��һҳ **/
	public boolean PageFront() {
		if (HasFront()) {
			m_nCurrentPage--;
			return true;
		}
		return false;
	}
	
	/** ��ȡ���� **/
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
