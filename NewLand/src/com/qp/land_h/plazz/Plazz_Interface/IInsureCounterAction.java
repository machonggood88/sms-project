package com.qp.land_h.plazz.Plazz_Interface;

/**
 * ���в����ӿ�
 * 
 * @note
 * @remark //�������� MDM_GR_INSURE 5
 * 
 *         //�������� SUB_GR_QUERY_INSURE_INFO 1 //��ѯ���� SUB_GR_SAVE_SCORE_REQUEST 2
 *         //������ SUB_GR_TAKE_SCORE_REQUEST 3 //ȡ�����
 *         SUB_GR_TRANSFER_SCORE_REQUEST 4 //ת�˲���
 * 
 *         SUB_GR_USER_INSURE_INFO 100 //�������� SUB_GR_USER_INSURE_SUCCESS 101
 *         //���гɹ� SUB_GR_USER_INSURE_FAILURE 102 //����ʧ��
 * 
 *         // ������ʽ INSURE_SAVE 0 //��ȡ��ʽ INSURE_TRANSFER 1 //ת�˷�ʽ
 * 
 */
public interface IInsureCounterAction {
	/** ��ѯ���� **/
	public void PerformQueryInfo();

	/** ��� **/
	public void PerformSaveScore(long lSaveScore);

	/** ȡ�� **/
	public void PerformTakeScore(long lTakeScore, String szInsurePass);

	/** ת�� **/
	public void PerformTransferScore(int cbByNickName, String szNickName,
			long lTransferScore, String szInsurePass);
}
