package com.qp.land_h.plazz.Plazz_Struct;

public class tagServerItem {
	/** 类型ID **/
	public int nKindID;
	/** 节点ID 无用 **/
	public int nNodeID;
	/** 无用 **/
	public int nSortID;
	/** 服务器ID **/
	public int nServerID;
	/** 服务器端口 **/
	public int nServerPort;
	/** 在线人数 **/
	public long lOnLineCount;
	/** 最大人数 **/
	public long lOnFullCount;
	/** 服务器地址 **/
	public String szServerUrl = "";
	/** 服务器名字 **/
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
