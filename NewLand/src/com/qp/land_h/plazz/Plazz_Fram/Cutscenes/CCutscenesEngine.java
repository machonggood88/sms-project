package com.qp.land_h.plazz.Plazz_Fram.Cutscenes;

import Lib_DF.DF;
import Lib_Graphics.CImage;
import Lib_Interface.HandleInterface.IMainMessage;
import Lib_Interface.UserInterface.IClientUserItem;
import Lib_System.CActivity;
import Lib_System.View.CViewEngine;
import Net_Struct.CPackMessage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Toast;

import com.qp.land_h.plazz.ClientPlazz;
import com.qp.land_h.plazz.Plazz_Interface.IClientKernelEx;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_ConfigServer;
import com.qp.land_h.plazz.cmd.SERVERLIST.CMD_GR_LogonFailure;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public class CCutscenesEngine extends CViewEngine implements ISeekFinish, IMainMessage {

    final static String BAR_POINT    = ClientPlazz.RES_PATH + "cutscenes/seekbar_p.png";
    final static String BAR_BG       = ClientPlazz.RES_PATH + "cutscenes/seekbar_bg.png";
    final static String BAR_FULL     = ClientPlazz.RES_PATH + "cutscenes/seekbar_seek.png";

    CCutscenesBar       seekbar;

    int                 m_nDstViewID = Integer.MIN_VALUE;

    public CCutscenesEngine(Context context) {
        super(context);
        seekbar = new CCutscenesBar(context, BAR_POINT, BAR_BG, BAR_FULL, this);

        if (CActivity.nDeviceType == DF.DEVICETYPE_QVGA)
            seekbar.SetTextSize(12);

        addView(seekbar);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        switch (CActivity.nDeviceType) {
            case DF.DEVICETYPE_WVGA:
                onLayoutH(changed, l, t, r, b);
                break;
            case DF.DEVICETYPE_HVGA:
                onLayoutM(changed, l, t, r, b);
                break;
            case DF.DEVICETYPE_QVGA:
                seekbar.SetTextSize(12);
                onLayoutL(changed, l, t, r, b);
                break;
        }
    }

    private void onLayoutL(boolean changed, int l, int t, int r, int b) {
        // ����λ��
        seekbar.layout(ClientPlazz.SCREEN_WIDHT / 2 - seekbar.GetW() / 2, ClientPlazz.SCREEN_HEIGHT - 65,
                ClientPlazz.SCREEN_WIDHT / 2 + seekbar.GetW() / 2, ClientPlazz.SCREEN_HEIGHT - 65 + seekbar.GetH());
    }

    private void onLayoutM(boolean changed, int l, int t, int r, int b) {
        // ����λ��
        seekbar.layout(ClientPlazz.SCREEN_WIDHT / 2 - seekbar.GetW() / 2, ClientPlazz.SCREEN_HEIGHT - 75,
                ClientPlazz.SCREEN_WIDHT / 2 + seekbar.GetW() / 2, ClientPlazz.SCREEN_HEIGHT - 75 + seekbar.GetH());
    }

    private void onLayoutH(boolean changed, int l, int t, int r, int b) {
        // ����λ��
        seekbar.layout(ClientPlazz.SCREEN_WIDHT / 2 - seekbar.GetW() / 2, ClientPlazz.SCREEN_HEIGHT - 125,
                ClientPlazz.SCREEN_WIDHT / 2 + seekbar.GetW() / 2, ClientPlazz.SCREEN_HEIGHT - 125 + seekbar.GetH());

    }

    @Override
    public void ActivateView() {

    }

    @Override
    public void OnDestoryRes() {
        seekbar.OnStop();
        seekbar.OnDestoryRes();

    }

    @Override
    public void OnInitRes() {
        setBackgroundDrawable(new BitmapDrawable(new CImage(PDF.GetContext(), ClientPlazz.RES_PATH
                + "cutscenes/bg_cutscenes.png", null, true).GetBitMap()));
        seekbar.OnInitRes();

    }

    @Override
    protected void Render(Canvas canvas) {

    }

    @Override
    public void OnSeekFinish() {
        switch (m_nDstViewID) {
            case ClientPlazz.MS_ROOM:
                PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_ROOM, null);
                break;
            case ClientPlazz.MS_GAME:
                PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_GAME, null);
                break;
        }
    }

    @Override
    public void MainMessagedispatch(int mainid, int subid, Object obj) {
        if (ClientPlazz.GetCurrentViewID() != ClientPlazz.MS_CUTSCENES) {
            ClientPlazz.GetPlazzInstance().OnShowCutscenes();
        }
        switch (mainid) {
            case NetCommand.MDM_GR_LOGON: {
                OnLogonInfo(subid, obj);
                break;
            }
            case NetCommand.MDM_GR_USER: {
                OnUserInfo(subid, obj);
                break;
            }
            case NetCommand.MDM_GF_FRAME: {
                OnFrameInfo(subid, obj);
                break;
            }
            case NetCommand.MDM_GR_GONFIG: {
                OnLogonConfig(subid, obj);
                break;
            }
        }

    }

    /** �����Ϣ **/
    private void OnFrameInfo(int subid, Object obj) {
        m_nDstViewID = ClientPlazz.MS_GAME;
        switch (subid) {
            case NetCommand.SUB_GR_USER_STATUS: {
                seekbar.SetInfo(80, "���ڻ�ȡ��Ϸ����");
                IClientKernelEx clientkernel = (IClientKernelEx) ClientPlazz.GetKernel();
                clientkernel.SendGameOption();
                break;
            }
            case NetCommand.SUB_GF_GAME_SCENE: {
                seekbar.SetInfo(100, "������...");
                break;
            }
        }

    }

    /** ��½���� **/
    private void OnLogonConfig(int subid, Object obj) {
        switch (subid) {
            case NetCommand.SUB_GR_CONFIG_SERVER: {
                seekbar.SetInfo((ClientPlazz.FAST_ENTER ? 20 : 40), "���ڼ�������...");
                CMD_GR_ConfigServer cmd = (CMD_GR_ConfigServer) obj;
                ClientPlazz.GetRoomEngine().onRoomConfig(cmd.nServerType, cmd.lServerRule, cmd.nTableCount,
                        cmd.nChairCount);
                // ClientPlazz.GetRoomEngine().onSubRoomList(cmd.nTableCount);
                break;
            }
            case NetCommand.SUB_GR_CONFIG_COLUMN: {

                break;
            }
            case NetCommand.SUB_GR_CONFIG_FINISH: {
                seekbar.SetInfo((ClientPlazz.FAST_ENTER ? 40 : 80), "�������...");
                break;
            }
        }
    }

    /** ��½��Ϣ **/
    private void OnLogonInfo(int subid, Object obj) {
        switch (subid) {
        // ��½����
            case NetCommand.SUB_GR_LOGON_MOBILE: {
                seekbar.SetInfo((ClientPlazz.FAST_ENTER ? 10 : 20), "���ڵ�¼����...");
                CPackMessage packge = new CPackMessage(NetCommand.MDM_GR_LOGON, NetCommand.SUB_GR_LOGON_MOBILE, obj);
                PDF.SendSubMessage(ClientPlazz.SEND_TO_SERVER, 0, packge);
                break;
            }
            // ��½ʧ��
            case NetCommand.SUB_GR_LOGON_FAILURE: {
                CMD_GR_LogonFailure cmdFailure = (CMD_GR_LogonFailure) obj;
                Toast.makeText(DF.GetContext(), cmdFailure.szDescribeString, Toast.LENGTH_SHORT).show();
                m_nDstViewID = Integer.MIN_VALUE;
                seekbar.OnStop();
                PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_SERVER, null);
                break;
            }
            // ��½���
            case NetCommand.SUB_GR_LOGON_FINISH: {
                if ((ClientPlazz.GetRoomEngine().getRoomRule() & PDF.SR_ALLOW_AVERT_CHET_MODE) != 0) {
                    Toast.makeText(DF.GetContext(), "�ֻ��ͻ���ռ��֧�ִ�ģʽ����!", Toast.LENGTH_SHORT).show();
                    m_nDstViewID = Integer.MIN_VALUE;
                    seekbar.OnStop();
                    PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_SERVER, null);
                    break;
                }
                IClientKernelEx kernel = (IClientKernelEx) ClientPlazz.GetKernel();
                IClientUserItem MeUserItem = kernel.GetMeUserItem();
                if (MeUserItem.GetUserStatus() == PDF.US_PLAYING) {
                    seekbar.SetInfo(80, "���ڻ�ȡ��Ϸ����");
                    kernel.SendGameOption();

                } else {
                    if (ClientPlazz.FAST_ENTER) {
                        m_nDstViewID = ClientPlazz.MS_GAME;
                        seekbar.SetInfo(60, "��½�ɹ�,�Զ���λ��...");
                        ClientPlazz.FAST_ENTER = false;
                        ClientPlazz.GetRoomEngine().onQuickSitDown();
                    } else {
                        m_nDstViewID = ClientPlazz.MS_ROOM;
                        seekbar.SetInfo(100, "��½�ɹ�,������...");
                    }
                }

                break;
            }
        }
    }

    /** �û���Ϣ **/
    private void OnUserInfo(int sub, Object obj) {
        switch (sub) {
            case NetCommand.SUB_GR_USER_SITDOWN: {

                seekbar.SetInfo(40, "���ڽ�����Ϸ��...");

                CPackMessage packge = new CPackMessage(NetCommand.MDM_GR_USER, NetCommand.SUB_GR_USER_SITDOWN, obj);

                PDF.SendSubMessage(ClientPlazz.SEND_TO_SERVER, 0, packge);
                break;
            }
            case NetCommand.SUB_GR_REQUEST_FAILURE: {

                PDF.SendMainMessage(ClientPlazz.MM_CHANGE_VIEW, ClientPlazz.MS_ROOM, null);
                break;
            }
        }

    }
}
