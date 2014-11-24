package com.qp.land_h.plazz;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Lib_DF.DF;
import Lib_Interface.IKeyBackDispatch;
import Lib_System.CActivity;
import Net_Interface.ITCPSocketReadManage;
import Net_Struct.CPackMessage;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.qp.new_land.R;
import com.qp.land_h.game.Game_Cmd.GDF;
import com.qp.land_h.game.Game_Engine.CGameClientView;
import com.qp.land_h.game.Game_Windows.CExpressionRes;
import com.qp.land_h.plazz.Plazz_Control.DownLoadAsyncTask;
import com.qp.land_h.plazz.Plazz_Fram.Cutscenes.CCutscenesEngine;
import com.qp.land_h.plazz.Plazz_Fram.Game.CGameFramEngine;
import com.qp.land_h.plazz.Plazz_Fram.Game.CGameFramView;
import com.qp.land_h.plazz.Plazz_Fram.Login.CLoginEngine;
import com.qp.land_h.plazz.Plazz_Fram.Logo.CLogoEngine;
import com.qp.land_h.plazz.Plazz_Fram.Option.CPlazzOptionEngine;
import com.qp.land_h.plazz.Plazz_Fram.Register.CRegisterEngine;
import com.qp.land_h.plazz.Plazz_Fram.Room.CRoomEngine;
import com.qp.land_h.plazz.Plazz_Fram.Server.CServerEngine;
import com.qp.land_h.plazz.Plazz_Graphics.CPlazzGraphics;
import com.qp.land_h.plazz.Plazz_Kernel.CClientKernel;
import com.qp.land_h.plazz.Plazz_Utility.CMyDialogInterface;
import com.qp.land_h.plazz.Plazz_Utility.CUserLevel;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_C_TransferScoreRequest;
import com.qp.land_h.plazz.cmd.Bank.CMD_GR_S_UserInsureFailure;
import com.qp.land_h.plazz.cmd.LOGON.CMD_MB_LogonFailure;
import com.qp.land_h.plazz.df.NetCommand;
import com.qp.land_h.plazz.df.PDF;

public abstract class ClientPlazz extends CActivity implements ITCPSocketReadManage {

	// 分辨率版本控制
	public static int				static_nDeviceType		= 0;																								// DF.DEVICETYPE_QVGA;
	/** 服务端 **/
	public static String			LOGIN_URL			= "laidapai.cn";
	/** 本地端 **/
	// public static String LOGIN_URL = "127.0.0.1";

	public static int			LOGIN_PORT			= 8300;
	public static String			LOGIN_UPDATA			= "";

	public static String			RES_PATH			= "";

	public final static int			QUERY_EXIT_SYSTEM		= 0;
	public final static int			QUERY_EXIT_SERVERLIST	        = 1;
	public final static int			QUERY_EXIT_ROOM			= 2;
	public final static int			QUERY_EXIT_GAME			= 3;
	public final static String[]	QUERY_ASK				= {"确认退出游戏？", "确认返回登录？", "确认离开房间？", "正在游戏中，确认强制退出？"};

	public final static int			MM_EXITSYSTEM			= 0;
	public final static int			MM_CHANGE_VIEW			= 1;
	public final static int			MS_LOAD					= 0;
	public final static int			MS_CUTSCENES			= 1;
	public final static int			MS_LOGIN				= 2;
	public final static int			MS_OPTION				= 3;
	public final static int			MS_REGISTER				= 4;
	public final static int			MS_ABOUT				= 5;
	public final static int			MS_SERVER				= 6;
	public final static int			MS_ROOM					= 7;
	public final static int			MS_GAME					= 8;

	public final static int			MS_TEST					= 9;

	public final static int			MM_UPDATA_QUERY			= 2;

	public final static int			MM_QUERY_EXIT			= 3;

	public final static int			MM_QUERY_TRANSFER		= 4;

	public final static int			MM_QUERY_DELETEACCOUNT	= 5;

	protected int					m_nLastViewStatus		= Integer.MIN_VALUE;
	protected int					m_nCurrentViewStatus	= Integer.MIN_VALUE;

	protected CPlazzGraphics		m_PlaszzGraphics;
	protected CLogoEngine			LogoEngine;
	protected CLoginEngine			LoginEngine;
	protected CPlazzOptionEngine	OptionEngine;
	protected CRegisterEngine		RegisterEngine;
	protected CCutscenesEngine		CutscenesEngine;

	protected CServerEngine			ServerEngine;
	protected CRoomEngine			RoomEngine;
	// protected CAboutEngine AboutEngine;
	protected CGameFramView			GameFramView;

	protected CUserLevel			m_UserLevel;
	/** 表情资源 **/
	protected CExpressionRes		ExpressionRes;

	/** 帮助信息 **/
	protected String				m_szHelp;
	/** 帮助页面 **/
	// CAboutView AboutView;
	/** 登录帐号记录 **/
	protected String				m_szAccounts;
	protected String				m_szPassWord;
	protected boolean				m_bSavePassWord;
	protected boolean				m_bAutoLogin;

	/** TEST **/
	// TestView testview;

	/** 设置登录信息 **/
	public void SetLogonInfo(String accounts, String password, boolean bsave, boolean bauto) {
		// Log.d("登陆帐号记录", "帐号:" + accounts + "-密码:" + password);
		m_szAccounts = accounts;
		m_szPassWord = password;
		m_bSavePassWord = bsave;
		m_bAutoLogin = bauto;
	}

	/** 登录帐号 **/
	public String GetLogonAccounts() {
		return m_szAccounts;
	}

	/** 登录密码 **/
	public String GetLogonPassWord() {
		return m_szPassWord;
	}

	/** 记住密码 **/
	public boolean IsSavePassWord() {
		return m_bSavePassWord;
	}

	/** 自动登陆 **/
	public boolean IsAutoLogin() {
		return m_bAutoLogin;
	}

	public static boolean	FAST_ENTER	= false;

	public static ClientPlazz GetPlazzInstance() {
		return (ClientPlazz) instance;
	}

	public static CLoginEngine GetLoginEngine() {
		return ((ClientPlazz) instance).LoginEngine;
	}

	public static CServerEngine GetServerEngine() {
		return ((ClientPlazz) instance).ServerEngine;
	}

	public static CCutscenesEngine GetCutscenesEngine() {
		return ((ClientPlazz) instance).CutscenesEngine;
	}

	public static CRoomEngine GetRoomEngine() {
		return ((ClientPlazz) instance).RoomEngine;
	}

	public static CGameFramEngine GetGameClientEngine() {
		return ((ClientPlazz) instance).GameFramView.GetGameClientEngine();
	}

	public static CGameFramView GetGameClientView() {
		return ((ClientPlazz) instance).GameFramView;
	}

	public static String GetUserLevel(long score) {
		return ((ClientPlazz) instance).m_UserLevel.GetUserLevel(score);
	}

	public static CRegisterEngine GetRegisterEngine() {
		return ((ClientPlazz) instance).RegisterEngine;
	}

	public static String GetHelpString() {
		return ((ClientPlazz) instance).m_szHelp;
	}

	// public static CAboutView GetAboutView() {
	// if (((ClientPlazz)instance).AboutView != null) {
	// if (((ClientPlazz)instance).AboutView.getParent() != null) {
	// ViewGroup group = (ViewGroup)((ClientPlazz)instance).AboutView
	// .getParent();
	// group.removeView(((ClientPlazz)instance).AboutView);
	// }
	// ((ClientPlazz)instance).AboutView.OnInitRes();
	// }
	// return ((ClientPlazz)instance).AboutView;
	// }
	//
	// public static void RemoveAboutView() {
	// if (((ClientPlazz)instance).AboutView != null) {
	// if (((ClientPlazz)instance).AboutView.getParent() != null) {
	// ViewGroup group = (ViewGroup)((ClientPlazz)instance).AboutView
	// .getParent();
	// group.removeView(((ClientPlazz)instance).AboutView);
	// ((ClientPlazz)instance).AboutView.OnDestoryRes();
	// }
	// }
	// }

	@Override
	protected void OnWelcome() {

		if (nDeviceType == 0) {
			static_nDeviceType = 0;
			OnDeviceTypeError();
			return;
		}
		// 分辨率版本控制
		if (static_nDeviceType != 0) {
			if (static_nDeviceType > nDeviceType) {
				OnDeviceTypeError();
				return;
			} else {
				nDeviceType = static_nDeviceType;
			}
		}

		// 资源路径
		switch (nDeviceType) {
			case DF.DEVICETYPE_WVGA :
				RES_PATH = PDF.RES_PATH_H;
				break;
			case DF.DEVICETYPE_HVGA :
				RES_PATH = PDF.RES_PATH_M;
				break;
			case DF.DEVICETYPE_QVGA :
				RES_PATH = PDF.RES_PATH_L;
				break;
		}
		// testview = new TestView(this);
		// PDF.SendMainMessage(MM_CHANGE_VIEW, MS_TEST, null);
		LogoEngine = new CLogoEngine(this);
		PDF.SendMainMessage(MM_CHANGE_VIEW, MS_LOAD, null);
		onRecordTimes("*OnWelcome*");
	}

	private void OnDeviceTypeError() {
		String szinfo = "";
		switch (static_nDeviceType) {
			case 0 :
				szinfo = "当前版本不支持此分辨率！";
				break;
			case DF.DEVICETYPE_WVGA :
				szinfo = "当前版本尽支持800-480以上分辨率，请下载对应版本！";
				break;
			case DF.DEVICETYPE_HVGA :
				szinfo = "当前版本尽支持480-320以上分辨率，请下载对应版本！";
				break;
			case DF.DEVICETYPE_QVGA :
				szinfo = "当前版本尽支持320-240以上分辨率，请下载对应版本！";
				break;
		}
		DialogInterface.OnClickListener ok = new CMyDialogInterface(szinfo) {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				onExit();
			}
		};

		Dialog dialog = new AlertDialog.Builder(this).setTitle("版本错误").setMessage(szinfo).setPositiveButton("确定", ok).create();
		dialog.show();
	}

	@Override
	protected void OnLoadFram() {
		super.OnLoadFram();
		onRecordTimes("*super.OnLoadFram()*");
		m_ClientKernel = CClientKernel.onCreatClientKernel();
		m_ClientKernel.SetGameAttribute(GDF.KIND_ID, GDF.GAME_PLAYER, PDF.ProcesVersion(6, 0, 3), PDF.ProcesVersion(6, 0, 3), GDF.GAME_NAME);
		onRecordTimes("*ClientKernel*");
		LoginEngine = new CLoginEngine(this);
		onRecordTimes("*CLoginEngine*");
		m_PlaszzGraphics = CPlazzGraphics.onCreate();
		onRecordTimes("*CPlazzGraphics*");
		CutscenesEngine = new CCutscenesEngine(this);
		onRecordTimes("*CCutscenesEngine*");
		RegisterEngine = new CRegisterEngine(this);
		onRecordTimes("*CRegisterEngine*");
		// AboutEngine = new CAboutEngine(this);
		ServerEngine = new CServerEngine(this);
		onRecordTimes("*CServerEngine*");
		RoomEngine = new CRoomEngine(this);
		onRecordTimes("*CRoomEngine*");
		OptionEngine = new CPlazzOptionEngine(this);
		onRecordTimes("*CPlazzOptionEngine*");
		m_UserLevel = new CUserLevel();
		onRecordTimes("*CUserLevel*");
		ExpressionRes = new CExpressionRes();
		onRecordTimes("*CExpressionRes*");
		GameFramView = new CGameClientView(this);
		onRecordTimes("*CGameClientView*");
		// AboutView = new CAboutView(this);
		m_GlobalUnitsInstance.SetDeviceType(NetCommand.DEVICETYPE_WVGA);

		StartApp(0, null);
	}

	@Override
	public void SubMessagedispatch(int mainid, int subid, Object obj) {
		switch (mainid) {
			case REV_FROM_SERVER : {
				if (obj != null) {
					CPackMessage packge = (CPackMessage) obj;
					((ITCPSocketReadManage) m_ClientKernel).onEventTCPSocketRead(packge.main, packge.sub, packge.Obj);
				}

				break;
			}
			case SEND_TO_SERVER : {
				CPackMessage packge = (CPackMessage) obj;
				if (m_ClientKernel != null && packge != null) {
					m_ClientKernel.SendSocketData(packge.main, packge.sub, packge.Obj);
				}
				break;
			}
			default :
				break;
		}
	}

	@Override
	public void MainMessagedispatchEx(int mainid, int subid, Object obj) {

		switch (mainid) {
			case MM_EXITSYSTEM :
				onExit();
				break;
			case MM_CHANGE_VIEW :
				m_nLastViewStatus = m_nCurrentViewStatus;
				m_nCurrentViewStatus = subid;
				onChangeView(subid, obj);
				break;
			case MM_UPDATA_QUERY :
				OnShowLoginView();
				OnQueryUpdata();
				break;
			case MM_QUERY_EXIT :
				OnQueryKeyBack();
				break;
			case MM_QUERY_TRANSFER :
				OnQueryTransfer((CMD_GR_C_TransferScoreRequest) obj);
				break;
			case MM_QUERY_DELETEACCOUNT :
				OnDeleteRecordAccount((String) obj);
				break;
			default :
				break;
		}
	}

	/**
	 * 切换界面
	 * 
	 * @param subid
	 * @param obj
	 */
	protected void onChangeView(int subid, Object obj) {

		switch (subid) {
			case MS_TEST : {
				// setContentView(testview);
				break;
			}
			case MS_LOAD :
				setContentView(LogoEngine);
				LogoEngine.OnStart();
				break;
			case MS_LOGIN :
				if (obj != null && CONNET_FAIL == (Integer) obj) {
					CMD_MB_LogonFailure cmd = new CMD_MB_LogonFailure();
					cmd.szDescribeString = "网络链接失败！";
					LoginEngine.OnLogonFailure(cmd);
				}
				setContentView(LoginEngine);
				break;
			case MS_OPTION :
				OptionEngine.SetRecordID(m_nLastViewStatus);
				setContentView(OptionEngine);
				break;
			case MS_REGISTER :
				setContentView(RegisterEngine);
				break;
			case MS_ABOUT :
				// setContentView(AboutEngine);
				break;
			case MS_SERVER :
				setContentView(ServerEngine);
				break;
			case MS_ROOM :
				setContentView(RoomEngine);
				break;
			case MS_GAME :
				setContentView(GameFramView);
				break;
			case MS_CUTSCENES :
				setContentView(CutscenesEngine);
				break;
			default :
				Log.e("onChangeView", "unkown-subid" + subid);
				break;
		}

	}

	public void OnShowGameClien() {
		m_nLastViewStatus = m_nCurrentViewStatus;
		m_nCurrentViewStatus = MS_GAME;
		onChangeView(MS_GAME, null);

	}

	public void OnShowCutscenes() {
		m_nLastViewStatus = m_nCurrentViewStatus;
		m_nCurrentViewStatus = MS_CUTSCENES;
		onChangeView(MS_CUTSCENES, null);
	}

	public void OnShowLoginView() {
		m_nLastViewStatus = m_nCurrentViewStatus;
		m_nCurrentViewStatus = MS_LOGIN;
		onChangeView(MS_LOGIN, null);
	}

	/**
	 * 按键处理
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 返回键处理
		final int nkey = event.getKeyCode();
		if (nkey == KeyEvent.KEYCODE_BACK) {

			boolean bnoqueryexit = false;
			if (!m_bNullView) {
				View view = findViewById(m_nCurrentViewID);
				if (view != null && view instanceof IKeyBackDispatch) {
					bnoqueryexit = ((IKeyBackDispatch) view).KeyBackDispatch();
				}
			}
			if (!bnoqueryexit) {
				PDF.SendMainMessage(MM_QUERY_EXIT, 0, null);
			}
			return true;
		} else if (nkey == KeyEvent.KEYCODE_MENU) {
			DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(final DialogInterface dialog, final int which) {
					PrintScreen();
				}
			};

			DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(final DialogInterface dialog, final int which) {
				}
			};
			Dialog dialog = new AlertDialog.Builder(this).setTitle("截屏询问").setMessage("是否截屏").setPositiveButton("确定", ok).setNegativeButton("取消", cancle)
					.create();
			dialog.show();
			return true;
		}
		// 声音键UP
		else if (nkey == KeyEvent.KEYCODE_VOLUME_UP) {
			// super.onKeyDown(keyCode, event);
			// m_GlobalUnitsInstance.InitBySystemMusic();
			// COptionEngine.OnSoundChange(m_GlobalUnitsInstance);
			m_GlobalUnitsInstance.OnMusicUp();
			m_GlobalUnitsInstance.OnSoundUp();
			return true;
		}
		// 声音键DOWN
		else if (nkey == KeyEvent.KEYCODE_VOLUME_DOWN) {
			// super.onKeyDown(keyCode, event);
			// m_GlobalUnitsInstance.InitBySystemMusic();
			// COptionEngine.OnSoundChange(m_GlobalUnitsInstance);
			m_GlobalUnitsInstance.OnMusicDown();
			m_GlobalUnitsInstance.OnSoundDown();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void OnQueryUpdata() {
		String gamename = "";
		gamename = m_ClientKernel == null || m_ClientKernel.GetGameAttribute() == null ? "游戏" : m_ClientKernel.GetGameAttribute().GameName;
		String Msg = "有新版本需要更新，现在更新？";

		DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onUpdataApp();
			}
		};

		DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				onExit();
			}
		};

		Dialog dialog = new AlertDialog.Builder(this).setTitle(gamename).setMessage(Msg).setPositiveButton("确定", ok).setNegativeButton("取消", cancle).create();
		dialog.show();

	}

	protected void onUpdataApp() {
		DownLoadAsyncTask downLoadAsyncTask = new DownLoadAsyncTask();
		downLoadAsyncTask.execute(LOGIN_UPDATA);

	}

	/**
	 * 转账询问
	 * 
	 * @param obj
	 */
	private void OnQueryTransfer(CMD_GR_C_TransferScoreRequest cmd) {
		String gamename = "";
		gamename = m_ClientKernel == null || m_ClientKernel.GetGameAttribute() == null ? "游戏" : m_ClientKernel.GetGameAttribute().GameName;
		String Msg = "";

		Msg += ((cmd.cbByNickName == 1 ? "昵称：" : "ID：") + cmd.szNickName + "\n" + "金额：" + cmd.lTransferScore);

		DialogInterface.OnClickListener ok = new CMyDialogInterface(cmd) {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				m_ClientKernel.SendSocketData(NetCommand.MDM_GR_INSURE, NetCommand.SUB_GR_TRANSFER_SCORE_REQUEST, obj);
			}
		};

		DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				CMD_GR_S_UserInsureFailure cmd = new CMD_GR_S_UserInsureFailure();
				cmd.szDescribeString = "取消转账";
				PDF.SendMainMessage(NetCommand.SUB_GR_USER_INSURE_FAILURE, 0, GetRoomEngine().GetBank().GetTag(), cmd);
			}
		};

		Dialog dialog = new AlertDialog.Builder(this).setTitle(gamename).setMessage(Msg).setPositiveButton("确定", ok).setNegativeButton("取消", cancle).create();
		dialog.show();
	}

	/**
	 * 返回询问
	 * 
	 * @param mode
	 * @return
	 */
	protected boolean OnQueryKeyBack() {
		int mode = -1;
		switch (m_nCurrentViewStatus) {
			case MS_SERVER :
				mode = QUERY_EXIT_SERVERLIST;
				break;
			case MS_ROOM :
				mode = QUERY_EXIT_ROOM;
				break;
			case MS_GAME :
				mode = QUERY_EXIT_GAME;
				break;
			case MS_LOGIN :
				mode = QUERY_EXIT_SYSTEM;
				break;
			default :
				return true;
		}
		String gamename = "";
		gamename = m_ClientKernel == null || m_ClientKernel.GetGameAttribute() == null ? "游戏" : m_ClientKernel.GetGameAttribute().GameName;
		DialogInterface.OnClickListener ok = null;
		switch (mode) {
			case QUERY_EXIT_SYSTEM :
				ok = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						onExit();
					}
				};
				break;
			case QUERY_EXIT_SERVERLIST :
				ok = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						PDF.SendMainMessage(MM_CHANGE_VIEW, MS_LOGIN, null);
					}
				};
				break;
			case QUERY_EXIT_ROOM :
				ok = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						PDF.SendMainMessage(MM_CHANGE_VIEW, MS_SERVER, null);
					}
				};
				break;
			case QUERY_EXIT_GAME :
				ok = new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						if (GameFramView.GetGameClientEngine() != null)
							GameFramView.GetGameClientEngine().PerformStandupAction(true);
						PDF.SendMainMessage(MM_CHANGE_VIEW, MS_ROOM, null);
					}
				};
				break;
			default :
				return false;
		}
		DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
			}
		};

		Dialog dialog = new AlertDialog.Builder(this).setTitle(gamename).setMessage(QUERY_ASK[mode]).setPositiveButton("确定", ok)
				.setNegativeButton("取消", cancle).create();
		dialog.show();
		return true;
	}

	/**
	 * 更新程序询问
	 * 
	 * @param MustUpdate
	 * @param AdviceUpdate
	 */
	protected void QueryUpdateApp(final int MustUpdate, final int AdviceUpdate) {

	}

	/**
	 * 网络错误处理
	 */
	@Override
	public void onSocketException(int main, int sub, Object obj) {
		((ITCPSocketReadManage) m_ClientKernel).onSocketException(main, sub, obj);
	}

	/**
	 * 网络事件读取
	 */
	@Override
	public boolean onEventTCPSocketRead(int main, int sub, Object obj) {
		PDF.SendSubMessage(main, sub, obj);
		return true;
	}

	public static int GetCurrentViewID() {
		return ((ClientPlazz) instance).getCurrentViewID();
	}

	protected int getCurrentViewID() {
		return m_nCurrentViewID;
	}

	public static int GetCurrentViewStatus() {
		return ((ClientPlazz) instance).getCurrentViewStatus();
	}

	protected int getCurrentViewStatus() {
		return m_nCurrentViewStatus;
	}

	@Override
	protected int getAppNameRes() {
		return R.string.app_name;
	}

	@Override
	public String GetAppName() {
		return GDF.GAME_NAME;
	}

	@Override
	public String GetOptionIniName() {

		return "gameoption.ini";
	}

	@Override
	public String GetSDCardSavePath() {
		return "Ox";
	}

	/**
	 * 获取表情资源
	 * 
	 * @return
	 */
	public static CExpressionRes getExpressionRes() {
		return ((ClientPlazz) instance).ExpressionRes;
	}

	private void PrintScreen() {
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();

		if (bitmap == null)
			return;
		String szPath = DF.GetSDCardPath() + "/" + GetSDCardSavePath() + "/photo";

		File fpath = new File(szPath);
		if (!fpath.exists()) {
			if (fpath.mkdirs() == false)
				return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
		String fname = szPath + "/" + sdf.format(new Date()) + ".png";

		try {
			FileOutputStream out = new FileOutputStream(fname);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			Log.d("PrintScreen", "OK" + ">>" + fname);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("PrintScreen", "失败");
		}

	}

	private void OnDeleteRecordAccount(String szaccount) {
		DialogInterface.OnClickListener ok = new CMyDialogInterface(szaccount) {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				LoginEngine.DeleteAccount((String) obj);
			}
		};

		DialogInterface.OnClickListener cancle = new CMyDialogInterface(szaccount) {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		Dialog dialog = new AlertDialog.Builder(this).setTitle("删除帐号").setMessage("是否删除").setPositiveButton("确定", ok).setNegativeButton("取消", cancle).create();
		dialog.show();
	}

	/**
	 * 获取发送目的地
	 * 
	 * @param msid
	 * @return
	 */
	public static int GetSendID(int msid) {
		switch (msid) {
			case MS_LOAD :
				return ((ClientPlazz) instance).LogoEngine.GetTag();
			case MS_CUTSCENES :
				return ((ClientPlazz) instance).CutscenesEngine.GetTag();
			case MS_LOGIN :
				return ((ClientPlazz) instance).LoginEngine.GetTag();
			case MS_OPTION :
				return ((ClientPlazz) instance).OptionEngine.GetTag();
			case MS_REGISTER :
				return ((ClientPlazz) instance).RegisterEngine.GetTag();
			case MS_ABOUT :
				return -1;
				// return ((ClientPlazz) instance).AboutEngine.GetTag();
			case MS_SERVER :
				return ((ClientPlazz) instance).ServerEngine.GetTag();
			case MS_ROOM :
				return ((ClientPlazz) instance).RoomEngine.GetTag();
			case MS_GAME :
				return ((ClientPlazz) instance).GameFramView.GetTag();
		}
		Log.e("GetSendID", "ERROR:msid-" + msid);
		return -1;
	}

	public static boolean IsViewEngineShow(int msid) {

		return ((ClientPlazz) instance).m_nCurrentViewStatus == msid;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (m_GlobalUnitsInstance != null)
			m_GlobalUnitsInstance.PlayBackGroundSound(R.raw.background, true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (m_GlobalUnitsInstance != null)
			m_GlobalUnitsInstance.StopBackGroundSound();
	}

}
