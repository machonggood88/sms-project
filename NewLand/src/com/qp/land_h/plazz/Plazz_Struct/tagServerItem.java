package com.qp.land_h.plazz.Plazz_Struct;

public class tagServerItem {
	/** ����ID **/
	public int nKindID;
	/** �ڵ�ID ���� **/
	public int nNodeID;
	/** ���� **/
	public int nSortID;
	/** ������ID **/
	public int nServerID;
	/** �������˿� **/
	public int nServerPort;
	/** �������� **/
	public long lOnLineCount;
	/** ������� **/
	public long lOnFullCount;
	/** ��������ַ **/
	public String szServerUrl = "";
	/** ���������� **/
	public String szServerName = "";

	public tagServerItem() {

	}

	public tagServerItem(int kindid, int serverid, int serverport, long online,
			long fullcount, String serverurl, String servername) {
		nKindID = kindid;
		nNodeID = 0;
		nSortID = 0;
		nServerID = serverid;
		nServerPort = serverport;
		lOnLineCount = online;
		lOnFullCount = fullcount;
		szServerUrl = serverurl;
		szServerName = servername;
	}
}
