package com.qp.land_h.plazz.Plazz_Kernel;

import java.util.Timer;
import java.util.TimerTask;

import Lib_Control.Other.CUserManage;
import Lib_DF.DF;
import Lib_Interface.ITimeInterface.IUserTimeDispath;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_Interface.UserInterface.IUserManageSkin;
import Lib_Struct.tagGameAttribute;
import Lib_Struct.tagServerAttribute;
import Net_Engine.SocketkEngineJavaLinkC;
import Net_Interface.ISocketEngine;
import Net_Interface.ITCPSocketReadManage;
import android.util.Log;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.Plazz_SocketMission.CSocketMission;
import com.qp.land_h.plazz.cmd.Game.CMD_GF_GameOption;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_ChairUserInfoReq;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_UserInfoReq;
import com.qp.land_h.plazz.cmd.User.CMD_GR_C_UserChat;
import com.qp.land_h.plazz.cmd.User.CMD_GR_C_UserExpression;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public class CClientKernel implements IClientKernelEx, ITCPSocketReadManage {

    /**
     * 计时器
     */
    protected class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            if (m_nTime > 0) {
                m_nTime--;
                IUserTimeDispath TimeDispath = ClientPlazz.GetGameClientEngine();
                if (TimeDispath != null)
                    TimeDispath.OnEventGameClockInfo(m_nChairID, m_nClockID, m_nTime);
                if (m_nTime == 0) {
                    m_nClockID = 0;
                    m_nChairID = DF.INVALID_CHAIR;
                    return;
                }
            }
        }
    }

    /** 玩家时钟 **/
    protected MyTimerTask             myTask;
    /** 玩家时钟 **/
    protected Timer                   timer;
    /** 椅子位置 **/
    protected int                     m_nChairID  = DF.INVALID_CHAIR;
    /** 时钟ID **/
    protected int                     m_nClockID;
    /** 玩家时间 **/
    protected int                     m_nTime;

    // 服务状态
    protected boolean                 m_bService;
    // 游戏状态
    protected int                     m_nGameStatus;
    // 游戏属性
    protected static tagGameAttribute m_GameAttribute;
    // 房间属性
    protected tagServerAttribute      m_ServerAttribute;
    // 用户管理
    protected CUserManage             m_UserManage;
    // 网络连接
    protected ISocketEngine           m_SocketEngine;
    // 网络接收
    protected CSocketMission          m_SocketMission;

    private static CClientKernel      instance;

    protected int                     m_nReadMode = 0;

    private CClientKernel() {
        InitClientKernel();
    }

    public static CClientKernel onCreatClientKernel() {
        if (instance == null)
            instance = new CClientKernel();
        return instance;
    }

    @Override
    public boolean InitClientKernel() {
        m_SocketEngine = SocketkEngineJavaLinkC.QueryInterface(ClientPlazz.GetPlazzInstance());
        m_SocketEngine.SetRevtTimeOut(90000);
        m_SocketEngine.SetTCPValidate(true);
        m_UserManage = new CUserManage();
        m_SocketMission = new CSocketMission();
        return true;
    }

    @Override
    public boolean SetGameAttribute(int kindid, int playercount, long clentversion,long gameversion, String szGameName) {
        m_GameAttribute = new tagGameAttribute(kindid, playercount, clentversion, gameversion,szGameName);
        return true;
    }

    @Override
    public boolean CreateConnect(String url, int port) {
        Log.d("CClientKernel", "网络连接>>" + url + ">>" + port);
        m_bService = m_SocketEngine.ConnectSocket(url, port);
        return m_bService;
    }

    @Override
    public boolean IntemitConnect() {
        Log.d("CClientKernel", "关闭网络连接");
        m_bService = false;
        return m_SocketEngine.Close();
    }

    @Override
    public tagGameAttribute GetGameAttribute() {

        return m_GameAttribute;
    }

    @Override
    public tagServerAttribute GetServerAttribute() {

        return m_ServerAttribute;
    }

    @Override
    public boolean IsSingleMode() {

        return false;
    }

    @Override
    public boolean IsLookonMode() {

        return GetMeUserItem().GetUserStatus() == DF.US_LOOKON;
    }

    @Override
    public boolean IsAllowLookon() {
        return false;
    }

    @Override
    public boolean IsServiceStatus() {
        return m_bService;
    }

    @Override
    public int GetGameStatus() {
        return m_nGameStatus;
    }

    @Override
    public void SetGameStatus(int gamestatus) {
        m_nGameStatus = gamestatus;
    }

    @Override
    public int GetMeChairID() {
        if (m_UserManage != null)
            return m_UserManage.GetMeUserItem().GetChairID();
        return PDF.INVALID_ITEM;
    }

    @Override
    public IClientUserItem GetMeUserItem() {
        if (m_UserManage != null)
            return m_UserManage.GetMeUserItem();
        return null;
    }

    @Override
    public IClientUserItem GetTableUserItem(int chairid) {
        if (m_UserManage != null)
            return m_UserManage.GetTableUserItem(m_UserManage.GetMeUserItem().GetTableID(), chairid);
        return null;
    }

    @Override
    public IClientUserItem SearchUserByUserID(long userid) {
        if (m_UserManage != null)
            return m_UserManage.GetUserItem(userid);
        return null;
    }

    /** 未提供 **/
    @Override
    public IClientUserItem SearchUserByGameID(long gameid) {
        return null;
    }

    @Override
    public IClientUserItem SearchUserByNickName(String nickname) {
        if (m_UserManage != null)
            return m_UserManage.GetUserItem(nickname);
        return null;
    }

    /** 未提供 **/
    @Override
    public IClientUserItem EnumLookonUserItem(int enumindex) {

        return null;
    }

    @Override
    public boolean SendSocketData(int maincmd, int subcmd) {
        if (m_SocketEngine != null)
            return m_SocketEngine.SendSocketDate(maincmd, subcmd, null);
        return false;

    }

    @Override
    public boolean SendSocketData(int maincmd, int subcmd, Object obj) {
        if (m_SocketEngine != null)
            return m_SocketEngine.SendSocketDate(maincmd, subcmd, obj);
        return false;
    }

    @Override
    public boolean SendUserReady(byte[] data, int datasize) {
        if (m_SocketEngine != null)
            return m_SocketEngine.SendSocketDate(NetCommand.MDM_GF_FRAME, NetCommand.SUB_GF_USER_READY, null);
        return false;
    }

    @Override
    public boolean SendUserLookon(long userid, boolean allowlookon) {

        return false;
    }

    @Override
    public boolean SendUserExpression(long targetuserid, int itemindex) {
        CMD_GR_C_UserExpression cmd = new CMD_GR_C_UserExpression();
        cmd.dwTargetUserID = (targetuserid == 0 || targetuserid == PDF.INVALID_CHAIR) ? (GetMeUserItem().GetUserID())
                : targetuserid;
        cmd.wItemIndex = itemindex;
        SendSocketData(NetCommand.MDM_GF_FRAME, NetCommand.SUB_GF_USER_EXPRESSION, cmd);
        return true;
    }

    @Override
    public boolean SendUserChatMessage(long targetuserid, String Chat, long color) {
        CMD_GR_C_UserChat cmd = new CMD_GR_C_UserChat();
        cmd.wChatLength = Chat.length();
        cmd.nChatColor = (int) color;
        cmd.lTargetUserID = targetuserid;
        cmd.szChatString = Chat;
        SendSocketData(NetCommand.MDM_GF_FRAME, NetCommand.SUB_GF_USER_CHAT, cmd);
        return true;
    }

    @Override
    public int SwitchViewChairID(int chairid) {
        if (chairid == DF.INVALID_CHAIR || m_GameAttribute == null || m_UserManage.GetMeUserItem() == null)
            return DF.INVALID_CHAIR;
        int nChairCount = m_GameAttribute.ChairCount;
        return (chairid + nChairCount * 3 / 2 - m_UserManage.GetMeUserItem().GetChairID()) % nChairCount;
    }

    @Override
    public int GetClockID() {
        return m_nClockID;
    }

    @Override
    public int GetClockChairID() {
        return m_nChairID;
    }

    @Override
    public void KillGameClock(int clockid) {
        if (clockid == m_nClockID) {
            m_nClockID = 0;
            m_nTime = 0;
            m_nChairID = DF.INVALID_CHAIR;
        }
    }

    @Override
    public void SetGameClock(int chairid, int clockid, int time) {
        m_nChairID = chairid;
        m_nTime = time;
        m_nClockID = clockid;
        if (timer == null || myTask == null) {
            InitUserClock();
        }
    }

    private void InitUserClock() {
        timer = new Timer();
        myTask = new MyTimerTask();
        timer.schedule(myTask, 0, 1000);
    }

    @Override
    public int GetUserClock(int chair) {
        if (m_nClockID == 0) {
            return 0;
        }
        if (chair != m_nChairID) {
            return 0;
        }
        return m_nTime;
    }

    @Override
    public void Release() {
        if (m_SocketEngine != null) {
            m_SocketEngine.Close();
            m_SocketEngine = null;
        }
        ReleasUserTime();
        m_ServerAttribute = null;
        if (m_UserManage != null) {
            m_UserManage.ReleaseUserItem(false);
            m_UserManage = null;
        }
    }

    @Override
    public boolean onEventTCPSocketRead(int main, int sub, Object obj) {
        boolean bSucceed = false;
        switch (m_nReadMode) {
            case 0:
                bSucceed = m_SocketMission.onEventGPSocketRead(main, sub, obj);
                break;
            case 1:
                bSucceed = m_SocketMission.onEventGRSocketRead(main, sub, obj);
                break;
            default:
                Log.d("ClientKernel", "onEventTCPSocketRead未知处理模式:" + m_nReadMode);
                return false;
        }
        if (!bSucceed) {
            Log.d("ClientKernel", "onEventTCPSocketRead信息处理失败>>main:" + main + ">>sub:" + sub + ">>mode:" + m_nReadMode);
        }
        return bSucceed;
    }

    @Override
    public void onSocketException(int main, int sub, Object obj) {
        Log.d("CONNET_FAIL", "sub:" + sub);
        if (main == CONNET_FAIL) {
            switch (sub) {
                case ER_SOCKET_CONNECT:
                    break;
                case ER_SOCKET_INPUT:
                    break;
                case ER_SOCKET_OUTPUT:
                    break;
                case ER_SOCKET_REV:
                    break;
                case ER_SOCKET_SEND:
                    break;
                case ER_PACK:
                    break;
                case ER_SOCKET_UNKNOW:
                    break;
                case ER_SOCKET_REVTIMEOUT:
                    break;
                default:
                    Log.d("ClientKernel", "onEventTCPSocketRead未知网络错误sub:" + sub);
                    break;
            }
        } else {
            Log.d("ClientKernel", "onEventTCPSocketRead未知网络错误main:" + main);
        }

        ReleasUserTime();

        if (m_bService) {
            m_bService = false;
            PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_LOGIN, CONNET_FAIL);
        }

    }

    @Override
    public IUserManageSkin GetUserManage() {
        return m_UserManage;
    }

    @Override
    public void SetSocketReadMode(int mode) {
        m_nReadMode = mode;
    }

    @Override
    public void FrushUserInfo(long userid, int tablepos) {
        CMD_GR_UserInfoReq cmd = new CMD_GR_UserInfoReq();
        cmd.lUserIDReq = userid;
        cmd.nTablePos = tablepos;
        SendSocketData(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_USER_INFO_REQ, cmd);
    }

    /**
     * 发送规则
     */
    @Override
    public void SendGameOption() {
        CMD_GF_GameOption cmd = new CMD_GF_GameOption();
        cmd.cbAllowLookon = (byte) (IsAllowLookon() ? 1 : 0);
        cmd.lClientVersion = GetGameAttribute().ProcesVersion;
        cmd.lFrameVersion = GetGameAttribute().ProcesVersion;
        SendSocketData(NetCommand.MDM_GF_FRAME, NetCommand.SUB_GF_GAME_OPTION, cmd);
    }

    @Override
    public void ReleasUserTime() {
        m_nClockID = 0;
        m_nTime = 0;
        m_nChairID = DF.INVALID_CHAIR;

        if (myTask != null) {
            myTask.cancel();
            myTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void SendUserChairInfoReq(int table, int chair) {
        CMD_GR_ChairUserInfoReq cmd = new CMD_GR_ChairUserInfoReq();
        cmd.nTablePos = table;
        cmd.nChairPos = chair;
        SendSocketData(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_USER_CHAIR_INFO_REQ, cmd);
    }

    @Override
    public void QuickSitDown() {
        SendSocketData(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_USER_CHAIR_REQ);
    }

    public static int GetPlayerCount() {
        return m_GameAttribute.ChairCount;
    }
}
