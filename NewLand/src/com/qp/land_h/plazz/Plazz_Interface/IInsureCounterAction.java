package com.qp.land_h.plazz.Plazz_Interface;

/**
 * 银行操作接口
 * 
 * @note
 * @remark //主命令码 MDM_GR_INSURE 5
 * 
 *         //子命令码 SUB_GR_QUERY_INSURE_INFO 1 //查询银行 SUB_GR_SAVE_SCORE_REQUEST 2
 *         //存款操作 SUB_GR_TAKE_SCORE_REQUEST 3 //取款操作
 *         SUB_GR_TRANSFER_SCORE_REQUEST 4 //转账操作
 * 
 *         SUB_GR_USER_INSURE_INFO 100 //银行资料 SUB_GR_USER_INSURE_SUCCESS 101
 *         //银行成功 SUB_GR_USER_INSURE_FAILURE 102 //银行失败
 * 
 *         // 操作方式 INSURE_SAVE 0 //存取方式 INSURE_TRANSFER 1 //转账方式
 * 
 */
public interface IInsureCounterAction {
	/** 查询银行 **/
	public void PerformQueryInfo();

	/** 存款 **/
	public void PerformSaveScore(long lSaveScore);

	/** 取款 **/
	public void PerformTakeScore(long lTakeScore, String szInsurePass);

	/** 转账 **/
	public void PerformTransferScore(int cbByNickName, String szNickName,
			long lTransferScore, String szInsurePass);
}
