package com.qp.land_h.plazz.Plazz_Interface;

/**
 * �Զ���ListView���ƽӿ�
 * 
 * @note
 * @remark
 */
public interface IListControl {
	/** ɾ��Ŀ�� **/
	public void DeleteItem(int index);

	/** ѡ��Ŀ�� **/
	public void SeleteItem(int index);

	/** ��ȡ�ı� **/
	public Object GetItem(int index);

	/** ��ȡ��Ŀ **/
	public int GetItemCount();
}
