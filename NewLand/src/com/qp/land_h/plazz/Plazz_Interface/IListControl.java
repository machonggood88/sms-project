package com.qp.land_h.plazz.Plazz_Interface;

/**
 * 自定义ListView控制接口
 * 
 * @note
 * @remark
 */
public interface IListControl {
	/** 删除目标 **/
	public void DeleteItem(int index);

	/** 选择目标 **/
	public void SeleteItem(int index);

	/** 获取文本 **/
	public Object GetItem(int index);

	/** 获取数目 **/
	public int GetItemCount();
}
