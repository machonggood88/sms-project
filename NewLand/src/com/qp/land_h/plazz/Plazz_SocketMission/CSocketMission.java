package com.qp.land_h.plazz.Plazz_SocketMission;

import Lib_Interface.UserInterface.IClientUserItem;
import Lib_Interface.UserInterface.IUserManageSkin;
import Lib_Struct.tagUserInfo;
import android.util.Log;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Fram.Game.CGameFramEngine;
import com.qp.land_h.plazz.Plazz_Fram.Game.CGameFramView;
import com.qp.land_h.plazz.Plazz_Fram.Room.CRoomEngine;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.Plazz_Interface.IGameClientManage;
import com.qp.land_h.plazz.cmd.CMD_CM_AcitonMessage;
import com.qp.land_h.plazz.cmd.CMD_CM_SystemMsg;
import com.qp.land_h.plazz.cmd.Game.CMD_GF_GameStatus;
import com.qp.land_h.plazz.cmd.Game.CMD_GF_S_UserChat;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_LogonFailure;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_LogonSuccess;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_ConfigServer;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_LogonFailure;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_LogonSuccess;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_TableInfo;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_TableStatus;
import com.qp.land_h.plazz.cmd.SERVERLIST.tagUserInfoHead;
import com.qp.land_h.plazz.cmd.User.CMD_GR_RequestFailuer;
import com.qp.land_h.plazz.cmd.User.CMD_GR_S_UserExpression;
import com.qp.land_h.plazz.cmd.User.CMD_GR_UserScore;
import com.qp.land_h.plazz.cmd.User.CMD_GR_UserStatus;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public class CSocketMission {

    public CSocketMission() {

    }

    public boolean onEventGPSocketRead(int main, int sub, Object obj) {
        switch (main) {
            case NetCommand.MDM_MB_LOGON:
            case NetCommand.MDM_GP_LOGON: // �ֿ�ܷ���������ʾ��Ȼ��MDM_GP_LOGON
                return OnSocketMainLogonGP(sub, (byte[]) obj);
            case NetCommand.MDM_MB_SERVER_LIST:
                return OnSocketMainServerList(sub, (byte[]) obj);
            default:
                break;
        }
        return false;
    }

    public boolean onEventGRSocketRead(int main, int sub, Object obj) {
        switch (main) {
            case NetCommand.MDM_GR_LOGON:
                return OnSocketMainLogonGR(sub, (byte[]) obj);
            case NetCommand.MDM_GR_GONFIG:
                return OnSocketMainConfig(sub, (byte[]) obj);
            case NetCommand.MDM_GR_USER:
                return OnSocketMainUser(sub, (byte[]) obj);
            case NetCommand.MDM_GR_STATUS:
                return OnSocketMainStatus(sub, (byte[]) obj);
            case NetCommand.MDM_GR_INSURE:
                return OnScocketMainInsure(sub, (byte[]) obj);
            case NetCommand.MDM_CM_SYSTEM:
                return OnSocketMainSystem(sub, (byte[]) obj);
            case NetCommand.MDM_GF_GAME:
            case NetCommand.MDM_GF_FRAME:
                return OnSocketMainGameFrame(main, sub, (byte[]) obj);
            default:
                break;
        }
        return false;
    }

    /**
     * ������¼
     * 
     * @param sub
     * @param data
     * @return
     */
    public boolean OnSocketMainLogonGP(int sub, byte data[]) {
        switch (sub) {
        // ��¼ʧ��
            case NetCommand.SUB_MB_LOGON_FAILURE: {

                // �Ͽ�����
                IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
                kernel.IntemitConnect();
                // ��ȡ��Ϣ
                CMD_MB_LogonFailure cmd = new CMD_MB_LogonFailure(data, 0);
                PDF.SendMainMessage(NetCommand.MDM_MB_LOGON, NetCommand.SUB_MB_LOGON_FAILURE, ClientPlazz
                        .GetLoginEngine().GetTag(), cmd);

                return true;
            }
            // ��¼�ɹ�
            case NetCommand.SUB_MB_LOGON_SUCCESS: {

                // �ж��Լ���Ϣ�Ƿ�Ϊ��
                IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
                if (kernel.GetMeUserItem() != null) {
                    Log.d("OnSocketMainLogonGP", "�Լ���Ϣ��Ϊnull");
                    return false;
                }
                IUserManageSkin manage = kernel.GetUserManage();

                // ��ȡ��Ϣ
                CMD_MB_LogonSuccess cmd = new CMD_MB_LogonSuccess(data, 0);
                // ���ظ�������
                tagUserInfo tempUserItem = new tagUserInfo();
                tempUserItem.lUserID = cmd.lUserID;
                tempUserItem.lGameID = cmd.lGameID;
                tempUserItem.szNickName = cmd.szNickName;
                tempUserItem.lExperience = cmd.lExperience;
                tempUserItem.cbGender = cmd.cbGender;

                manage.AddUserItem(tempUserItem);

                PDF.SendMainMessage(NetCommand.MDM_MB_LOGON, NetCommand.SUB_MB_LOGON_SUCCESS, ClientPlazz
                        .GetLoginEngine().GetTag(), null);
                return true;

            }
            case NetCommand.SUB_MB_LOGON_FINISH: {
                return true;
            }
            case NetCommand.SUB_MB_UPDATE_NOTIFY: {
                PDF.SendMainMessage(ClientPlazz.MM_UPDATA_QUERY, 0, null);
                return true;
            }
            default:
                break;
        }
        return false;
    }

    /**
     * �������б���Ϣ����
     * 
     * @param sub
     * @param data
     * @return
     */
    public boolean OnSocketMainServerList(int sub, byte data[]) {
        switch (sub) {

        // ����������
            case NetCommand.SUB_MB_LIST_KIND: {

                return true;
            }
            // �������б�
            case NetCommand.SUB_MB_LIST_SERVER: {

                PDF.SendMainMessage(NetCommand.MDM_MB_SERVER_LIST, NetCommand.SUB_MB_LIST_SERVER, ClientPlazz
                        .GetLoginEngine().GetTag(), data);

                return true;
            }
            // �������
            case NetCommand.SUB_MB_LIST_FINISH: {

                IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
                if (kernel != null) {
                    // �ر�����
                    kernel.IntemitConnect();
                    kernel.SetSocketReadMode(1);
                    IUserManageSkin usermanage = kernel.GetUserManage();
                    if (usermanage != null) {
                        usermanage.ReleaseUserItem(true);
                    }
                }
                PDF.SendMainMessage(NetCommand.MDM_MB_SERVER_LIST, NetCommand.SUB_MB_LIST_FINISH, ClientPlazz
                        .GetLoginEngine().GetTag(), null);

                return true;
            }
            default:
                break;
        }
        return false;
    }

    public boolean OnSocketMainLogonGR(int sub, byte data[]) {
        switch (sub) {
        // ��½�ɹ�
            case NetCommand.SUB_GR_LOGON_SUCCESS: {
                CMD_GR_LogonSuccess cmdSuccess = new CMD_GR_LogonSuccess();
                cmdSuccess.ReadFromByteArray(data, 0);

                PDF.SendMainMessage(NetCommand.MDM_GR_LOGON, NetCommand.SUB_GR_LOGON_SUCCESS, ClientPlazz
                        .GetCutscenesEngine().GetTag(), cmdSuccess);
                return true;
            }
            // ��½ʧ��
            case NetCommand.SUB_GR_LOGON_FAILURE: {
                CMD_GR_LogonFailure cmdFailure = new CMD_GR_LogonFailure();
                cmdFailure.ReadFromByteArray(data, 0);
                PDF.SendMainMessage(NetCommand.MDM_GR_LOGON, NetCommand.SUB_GR_LOGON_FAILURE, ClientPlazz
                        .GetCutscenesEngine().GetTag(), cmdFailure);
                return true;
            }
            // ��½���
            case NetCommand.SUB_GR_LOGON_FINISH: {

                IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
                CGameFramEngine game = ClientPlazz.GetGameClientEngine();
                if (game != null) {
                    game.SetMeUserItem(kernel.GetMeUserItem());
                }
                if (kernel.GetMeUserItem().GetUserStatus() == PDF.US_PLAYING) {
                    kernel.SendUserChairInfoReq(kernel.GetMeUserItem().GetTableID(), PDF.INVALID_CHAIR);
                    ClientPlazz.GetGameClientEngine().SetActive(kernel.GetMeUserItem().GetTableID(),
                            kernel.GetMeUserItem().GetChairID());

                } else {

                    ClientPlazz.GetGameClientEngine().SetActive(PDF.INVALID_TABLE, PDF.INVALID_CHAIR);
                }

                PDF.SendMainMessage(NetCommand.MDM_GR_LOGON, NetCommand.SUB_GR_LOGON_FINISH,
                        ClientPlazz.GetSendID(ClientPlazz.MS_CUTSCENES), null);
                return true;
            }
            // �汾��Ϣ
            case NetCommand.SUB_GR_UPDATE_NOTIFY: {
//                PDF.SendMainMessage(NetCommand.MDM_GR_LOGON, NetCommand.SUB_GR_UPDATE_NOTIFY,
//                        ClientPlazz.GetSendID(ClientPlazz.MS_CUTSCENES), null);

                PDF.SendMainMessage(ClientPlazz.MM_UPDATA_QUERY, 0, null);
                
                return true;
            }
            default:
                break;
        }
        return false;
    }

    public boolean OnSocketMainConfig(int sub, byte data[]) {
        switch (sub) {
        // �б����� ������Ϸ�����ã�
            case NetCommand.SUB_GR_CONFIG_COLUMN: {

                return false;
            }
            // ��������
            case NetCommand.SUB_GR_CONFIG_SERVER: {
                // ���½���
                CMD_GR_ConfigServer cmd = new CMD_GR_ConfigServer();
                cmd.ReadFromByteArray(data, 0);

                PDF.SendMainMessage(NetCommand.MDM_GR_GONFIG, NetCommand.SUB_GR_CONFIG_SERVER, ClientPlazz
                        .GetCutscenesEngine().GetTag(), cmd);
                return true;
            }
            // �������
            case NetCommand.SUB_GR_CONFIG_FINISH: {
                PDF.SendMainMessage(NetCommand.MDM_GR_GONFIG, NetCommand.SUB_GR_CONFIG_FINISH, ClientPlazz
                        .GetCutscenesEngine().GetTag(), null);
                return true;
            }
        }
        return false;
    }

    public boolean OnSocketMainUser(int sub, byte data[]) {
        switch (sub) {
        // ���NetCommand��
            case NetCommand.SUB_GR_USER_ENTER:
                return OnSocketSubUserEnter(data);
                // ������Ϣ
            case NetCommand.SUB_GR_USER_SCORE:
                return OnSocketSubUserScore(data);
                // ���״̬
            case NetCommand.SUB_GR_USER_STATUS:
                return OnSocketSubUserStatus(data);
                // ����ʧ��
            case NetCommand.SUB_GR_REQUEST_FAILURE:
                return OnSocketRequestFailure(data);
                // ������Ϣ
            case NetCommand.SUB_GR_USER_CHAT:
                return OnSocketSubUserChat(data);
                // ˽����Ϣ
            case NetCommand.SUB_GR_WISPER_CHAT:
                return false;
                // ������Ϣ
            case NetCommand.SUB_GR_USER_EXPRESSION:
                return OnSocketSubUserExpression(data);
                // ˽�ı���
            case NetCommand.SUB_GR_WISPER_EXPRESSION:
                return false;
        }
        return false;
    }

    public boolean OnSocketMainStatus(int sub, byte data[]) {
        switch (sub) {
            case NetCommand.SUB_GR_TABLE_INFO: {
                // ��ȡ��Ϣ
                CMD_GR_TableInfo cmd = new CMD_GR_TableInfo();
                cmd.ReadFromByteArray(data, 0);
                // // ����״̬
                // for (int i = 0; i < cmd.nTableCount
                // && i < cmd.TableStatusArray.length; i++) {
                // PlazzActivity.getRoomDlg().SetTableStatus(i,
                // cmd.TableStatusArray[i].cbPlayStatus == 1,
                // cmd.TableStatusArray[i].cbTableLock == 1);
                // }
                return false;
            }
            case NetCommand.SUB_GR_TABLE_STATUS: {
                // ��ȡ��Ϣ
                CMD_GR_TableStatus cmd = new CMD_GR_TableStatus();
                cmd.ReadFromByteArray(data, 0);
                // ����״̬
                // PlazzActivity.getRoomDlg().SetTableStatus(cmd.nTableID,
                // cmd.TableStatus.cbPlayStatus == 1,
                // cmd.TableStatus.cbTableLock == 1);
                return false;
            }
        }
        return false;
    }

    public boolean OnScocketMainInsure(int sub, byte[] obj) {
        if (ClientPlazz.GetRoomEngine() != null) {
            return ClientPlazz.GetRoomEngine().OnScocketMainInsure(sub, obj);
        }
        return false;
    }

    public boolean OnSocketMainSystem(int sub, byte data[]) {

        switch (sub) {
        // ϵͳ��Ϣ
            case NetCommand.SUB_CM_SYSTEM_MESSAGE:
                return OnSocketSubSystemMessage(data);

                // ������Ϣ
            case NetCommand.SUB_CM_ACTION_MESSAGE:
                return OnSocketSubActionMessage(data);

        }
        return false;
    }

    public boolean OnSocketMainGameFrame(int main, int sub, byte data[]) {

        boolean bSucceed = false;

        if (ClientPlazz.GetGameClientEngine() != null) {
            // ��Ϸ����
            if (main == NetCommand.MDM_GF_GAME) {
                bSucceed = ClientPlazz.GetGameClientEngine().OnEventGameMessage(sub, data);
            }
            // ��������
            if (!bSucceed && main == NetCommand.MDM_GF_FRAME) {
                switch (sub) {
                // �û�����
                    case NetCommand.SUB_GF_USER_CHAT:
                        return OnSocketSubFramUserChat(data);

                        // �û�����
                    case NetCommand.SUB_GF_USER_EXPRESSION:
                        return OnSocketSubUserFramExpression(data);

                        // ��Ϸ״̬
                    case NetCommand.SUB_GF_GAME_STATUS:
                        return OnSocketSubGameStatus(data);

                        // ��Ϸ����
                    case NetCommand.SUB_GF_GAME_SCENE:
                        return OnSocketSubGameScene(data);

                        // �ۿ���Ϣ
                    case NetCommand.SUB_GF_LOOKON_STATUS:
                        return OnSocketSubLookonStatus(data);

                        // ϵͳ��Ϣ
                    case NetCommand.SUB_GF_SYSTEM_MESSAGE:
                        return OnSocketSubSystemMessage(data);

                        // ������Ϣ
                    case NetCommand.SUB_GF_ACTION_MESSAGE:
                        return OnSocketSubActionMessage(data);

                    default:
                        break;
                }
            }
        }
        return bSucceed;
    }

    /**
     * ����ʧ����Ϣ
     * 
     * @param data
     * @return
     */
    private boolean OnSocketRequestFailure(byte[] data) {
        // ��ȡ��Ϣ
        CMD_GR_RequestFailuer cmd = new CMD_GR_RequestFailuer();
        cmd.ReadFromByteArray(data, 0);
        // ��ʾ��Ϣ
        Toast.makeText(PDF.GetContext(), "����ʧ�ܣ�" + cmd.szDescribeString, Toast.LENGTH_SHORT).show();

        if (ClientPlazz.IsViewEngineShow(ClientPlazz.MS_CUTSCENES)) {
            PDF.SendMainMessage(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_REQUEST_FAILURE, ClientPlazz
                    .GetCutscenesEngine().GetTag(), cmd);
        }

        return true;
    }

    /**
     * �û�״̬��Ϣ
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubUserStatus(byte[] data) {
        CMD_GR_UserStatus cmd = new CMD_GR_UserStatus();
        cmd.ReadFromByteArray(data, 0);
        Log.d("�û�״̬", cmd.lUserID + ">>���ӣ�" + cmd.UserStatus.nTableID + ">>����" + cmd.UserStatus.nChairID + ">>״̬:"
                + cmd.UserStatus.nStatus);

        // ��ȡ����ӿ�
        IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
        IUserManageSkin usermange = kernel.GetUserManage();
        IClientUserItem useritem = usermange.GetUserItem(cmd.lUserID);
        if (useritem == null && (cmd.UserStatus.nStatus != PDF.US_NULL)) {

            useritem = new tagUserInfo();
            ((tagUserInfo) useritem).szNickName = "��ȡ�û���ʧ��";
            ((tagUserInfo) useritem).lUserID = cmd.lUserID;
            ((tagUserInfo) useritem).nStatus = cmd.UserStatus.nStatus;
            ((tagUserInfo) useritem).nTableID = cmd.UserStatus.nTableID;
            ((tagUserInfo) useritem).nChairID = cmd.UserStatus.nChairID;
            usermange.AddUserItem((tagUserInfo) useritem);
            if (cmd.UserStatus.nTableID != PDF.INVALID_TABLE && cmd.UserStatus.nChairID != PDF.INVALID_CHAIR)
                kernel.SendUserChairInfoReq(cmd.UserStatus.nTableID, cmd.UserStatus.nChairID);
        }
        if (useritem != null) {
            int oldtable = useritem.GetTableID();
            int oldchair = useritem.GetChairID();
            int newtable = cmd.UserStatus.nTableID;
            int newchair = cmd.UserStatus.nChairID;
            int oldstatus = useritem.GetUserStatus();
            CRoomEngine room = ClientPlazz.GetRoomEngine();

            if (oldtable != PDF.INVALID_TABLE && oldchair != PDF.INVALID_CHAIR)
                room.SetTableUserItem(oldtable, oldchair, null);

            IClientUserItem MeUserItem = usermange.GetMeUserItem();

            switch (cmd.UserStatus.nStatus) {
                case PDF.US_NULL: {
                    // ɾ���û�
                    usermange.DelUserItem(cmd.lUserID);
                    // ������Լ� �˳���Ϸ
                    if (useritem.GetUserID() == MeUserItem.GetUserID()) {
                        PDF.SendMainMessage(ClientPlazz.MM_EXITSYSTEM, 0, null);
                    } else {
                        if (MeUserItem.GetTableID() != PDF.INVALID_TABLE && oldtable == MeUserItem.GetTableID()) {
                            Toast.makeText(PDF.GetContext(), "[" + useritem.GetNickName() + "] �뿪", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    break;
                }
                case PDF.US_FREE: {
                    // �����û�
                    usermange.UpdataUserItemStatus(cmd.lUserID, cmd.UserStatus.nTableID, cmd.UserStatus.nChairID,
                            cmd.UserStatus.nStatus);
                    // ������Լ��ж��Ƿ�����ʾ����
                    if (useritem.GetUserID() == MeUserItem.GetUserID()) {

                        if (ClientPlazz.GetCurrentViewStatus() != ClientPlazz.MS_ROOM) {
                            PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_ROOM, null);
                        }
                        ClientPlazz.GetGameClientEngine().SetActive(cmd.UserStatus.nTableID, cmd.UserStatus.nChairID);
                    } else {
                        if (MeUserItem.GetTableID() != PDF.INVALID_TABLE && oldtable == MeUserItem.GetTableID()) {
                            Toast.makeText(PDF.GetContext(), "[" + useritem.GetNickName() + "] �뿪", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                }
                case PDF.US_LOOKON:
                case PDF.US_OFFLINE:
                case PDF.US_PLAYING:
                case PDF.US_READY:
                case PDF.US_SIT: {

                    // �����û�
                    usermange.UpdataUserItemStatus(cmd.lUserID, cmd.UserStatus.nTableID, cmd.UserStatus.nChairID,
                            cmd.UserStatus.nStatus);
                    // ����������ͼ
                    if (newtable != PDF.INVALID_TABLE && newchair != PDF.INVALID_CHAIR)
                        room.SetTableUserItem(newtable, newchair, useritem);

                    if (useritem.GetUserID() == MeUserItem.GetUserID()) {
                        ClientPlazz.GetGameClientEngine().SetActive(cmd.UserStatus.nTableID, cmd.UserStatus.nChairID);
                        // �����ж�
                        if (oldstatus < PDF.US_SIT) {
                            kernel.SendUserChairInfoReq(kernel.GetMeUserItem().GetTableID(), PDF.INVALID_CHAIR);

                            PDF.SendMainMessage(NetCommand.MDM_GF_FRAME, NetCommand.SUB_GR_USER_STATUS, ClientPlazz
                                    .GetCutscenesEngine().GetTag(), cmd.UserStatus.nStatus);
                        }
                    } else if (cmd.UserStatus.nStatus == PDF.US_SIT) {
                        if (MeUserItem.GetTableID() != PDF.INVALID_TABLE && newtable == MeUserItem.GetTableID()
                                && oldtable != MeUserItem.GetTableID()) {
                            Toast.makeText(PDF.GetContext(), "[" + useritem.GetNickName() + "] ����", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                }
                default:
                    return false;

            }

            if (ClientPlazz.GetGameClientEngine().IsActive()) {
                if (oldtable == MeUserItem.GetTableID() || newtable == MeUserItem.GetTableID()) {
                    ClientPlazz.GetGameClientView().postInvalidateChair(
                            oldchair == MeUserItem.GetTableID() ? oldchair : newchair);
                }
            }

        }
        return true;
    }

    /**
     * �û�������Ϣ
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubUserScore(byte[] data) {
        CMD_GR_UserScore cmd = new CMD_GR_UserScore();
        cmd.ReadFromByteArray(data, 0);

        IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
        IUserManageSkin usermange = kernel.GetUserManage();
        usermange.UpdataUserItemScore(cmd.lUserID, cmd.UserScore);

        return true;
    }

    /**
     * �û�������Ϣ
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubUserEnter(byte[] data) {
        tagUserInfoHead UserInfoHead = new tagUserInfoHead();
        UserInfoHead.ReadFromByteArray(data, 0);
        tagUserInfo useritem = new tagUserInfo();
        Log.d("�û�����", UserInfoHead.szNickName + ">>���ӣ�" + UserInfoHead.nTableID + ">>����" + UserInfoHead.nChairID
                + ">>״̬:" + UserInfoHead.cbUserStatus);
        useritem.szNickName = UserInfoHead.szNickName;
        useritem.nFaceID = UserInfoHead.nFaceID;
        useritem.lGameID = UserInfoHead.lGameID;
        useritem.lScore = UserInfoHead.lScore;
        useritem.nStatus = UserInfoHead.cbUserStatus;
        useritem.nTableID = UserInfoHead.nTableID;
        useritem.nChairID = UserInfoHead.nChairID;
        useritem.lUserID = UserInfoHead.lUserID;
        useritem.lExperience = UserInfoHead.lExperience;
        useritem.cbGender = UserInfoHead.cbGender;
        useritem.lWinCount = UserInfoHead.lWinCount;
        useritem.lLostCount = UserInfoHead.lLostCount;
        useritem.lDrawCount = UserInfoHead.lDrawCount;
        useritem.lFleeCount = UserInfoHead.lFleeCount;

        // ��ȡ����ӿ�
        IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
        IUserManageSkin usermange = kernel.GetUserManage();
        IClientUserItem olditem = usermange.GetUserItem(useritem.lUserID);

        // ��������
        int oldtable = PDF.INVALID_TABLE;
        int oldchair = PDF.INVALID_CHAIR;
        int newtable = useritem.nTableID;
        int newchair = useritem.nChairID;

        // ��ȡ�ϴ�λ��
        if (olditem != null) {
            oldtable = olditem.GetTableID();
            oldchair = olditem.GetChairID();
        }

        // �����û���Ϣ
        if (useritem.nStatus == PDF.US_NULL) {
            usermange.DelUserItem(useritem.lUserID);
        } else {
            usermange.AddUserItem(useritem);
        }

        // ������ͼ����
        CRoomEngine room = ClientPlazz.GetRoomEngine();
        if (room != null) {
            if (oldtable != PDF.INVALID_TABLE && oldchair != PDF.INVALID_CHAIR)
                room.SetTableUserItem(oldtable, oldchair, null);
            if (newtable != PDF.INVALID_TABLE && newchair != PDF.INVALID_CHAIR) {
                IClientUserItem newuseritem = usermange.GetUserItem(UserInfoHead.lUserID);
                room.SetTableUserItem(newtable, newchair, newuseritem);
                if (useritem.GetUserID() == kernel.GetMeUserItem().GetUserID()) {
                    ClientPlazz.GetGameClientEngine().SetActive(newtable, newchair);
                }
            }
        }
        return true;
    }

    private boolean OnSocketSubActionMessage(byte[] data) {
        CMD_CM_AcitonMessage cmd = new CMD_CM_AcitonMessage();
        cmd.ReadFromByteArray(data, 0);
        return false;
    }

    private boolean OnSocketSubSystemMessage(byte[] data) {
        // ��ȡ��Ϣ
        CMD_CM_SystemMsg cmd = new CMD_CM_SystemMsg();
        cmd.ReadFromByteArray(data, 0);
        if (cmd.szString != null && !cmd.szString.equals("")) {
            Toast.makeText(PDF.GetContext(), cmd.szString, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * ��Ϸ״̬��Ϣ
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubLookonStatus(byte[] data) {

        return false;
    }

    /**
     * ��Ϸ������Ϣ
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubGameScene(byte[] data) {
        IGameClientManage game = ClientPlazz.GetGameClientEngine();
        if (game != null) {
            game.OnEventSceneMessage(data);
            return true;
        }
        return false;
    }

    /**
     * ��Ϸ״̬
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubGameStatus(byte[] data) {
        CGameFramEngine game = ClientPlazz.GetGameClientEngine();
        if (game != null) {
            CMD_GF_GameStatus cmd = new CMD_GF_GameStatus();
            cmd.ReadFromByteArray(data, 0);
            game.SetGameStatus(cmd.nGameStatus);
            game.SetAllowLookon(cmd.nAllowLookon == 1);
            return true;
        }
        return false;
    }

    /**
     * �û�����
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubUserChat(byte[] data) {

        return false;
    }

    /**
     * �û�����
     * 
     * @param data
     * @return
     */
    private boolean OnSocketSubUserExpression(byte[] data) {

        return false;
    }

    /** �������� **/
    private boolean OnSocketSubUserFramExpression(byte[] data) {

        CGameFramView gameview = ClientPlazz.GetGameClientView();
        if (gameview != null) {
            CMD_GR_S_UserExpression cmd = new CMD_GR_S_UserExpression();
            cmd.ReadFromByteArray(data, 0);
            PDF.SendMainMessage(NetCommand.SUB_GF_USER_EXPRESSION, 0, gameview.GetTag(), cmd);
            return true;
        }
        return false;
    }

    /** �������� **/
    private boolean OnSocketSubFramUserChat(byte[] data) {

        CGameFramView gameview = ClientPlazz.GetGameClientView();
        if (gameview != null) {
            CMD_GF_S_UserChat cmd = new CMD_GF_S_UserChat();
            cmd.ReadFromByteArray(data, 0);
            PDF.SendMainMessage(NetCommand.SUB_GF_USER_CHAT, 0, gameview.GetTag(), cmd);
            return true;
        }
        return false;
    }
}
