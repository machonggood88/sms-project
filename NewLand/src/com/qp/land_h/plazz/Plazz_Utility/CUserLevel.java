package com.qp.land_h.plazz.Plazz_Utility;

import java.util.ArrayList;

import Lib_Control.Other.CIniHelper;
import android.content.res.AssetManager;
import android.util.Log;

public class CUserLevel {
	public class Level {
		public String name = "";
		public long score = 0;

		public Level(String name, long score) {
			this.name = name;
			this.score = score;
		}
	}

	protected ArrayList<Level> m_LevelOpinion = new ArrayList<CUserLevel.Level>();

	
	public CUserLevel() {
		m_LevelOpinion.add(new Level("ũū", 0));
		m_LevelOpinion.add(new Level("��ؤ", 60));
		m_LevelOpinion.add(new Level("��ũ", 160));
		m_LevelOpinion.add(new Level("���˺�", 320));
		m_LevelOpinion.add(new Level("����", 560));
		m_LevelOpinion.add(new Level("�̹�", 920));
		m_LevelOpinion.add(new Level("����", 1400));
		m_LevelOpinion.add(new Level("��ũ", 2080));
		m_LevelOpinion.add(new Level("��ũ", 3200));
		m_LevelOpinion.add(new Level("����", 4800));
		m_LevelOpinion.add(new Level("������", 7200));
		m_LevelOpinion.add(new Level("�����", 10800));
		m_LevelOpinion.add(new Level("�����", 16000));
		m_LevelOpinion.add(new Level("��Ե���", 27200));
		m_LevelOpinion.add(new Level("��Բ���", 48000));
		m_LevelOpinion.add(new Level("ū����", 136000));
		m_LevelOpinion.add(new Level("��ū����", 240000));
		m_LevelOpinion.add(new Level("�ط���ǿ", 400000));
		m_LevelOpinion.add(new Level("���", 1000000));
	}

	public CUserLevel(AssetManager assets, String path) {

		int nIndex = 1;
		while (true) {
			String szmsg = "";
			try {
				szmsg = CIniHelper.getProfileString(assets, path,
						"LevelDescribe", "LevelItem" + nIndex, "");
			} catch (Exception e) {
				Log.e("CUserLevel", "ReadIni-Error!");
			} finally {
				if (szmsg == null || szmsg.equals("")) {
					break;
				} else {
					String[] strArray = szmsg.split(",");
					if (strArray.length == 1) {
						m_LevelOpinion.add(new Level(strArray[0].trim(), 0));
					} else if (strArray.length == 2) {
						m_LevelOpinion.add(new Level(strArray[0].trim(), Long
								.parseLong(strArray[1].trim())));
					} else {
						break;
					}
				}
				nIndex++;
			}
		}
	}

	public String GetUserLevel(long score) {
		if (m_LevelOpinion == null) {
			return "";
		}

		for (int i = 0; i < m_LevelOpinion.size(); i++) {
			if (i == m_LevelOpinion.size() - 1) {
				return m_LevelOpinion.get(i).name;
			}
			if (score < m_LevelOpinion.get(i).score) {
				return m_LevelOpinion.get(i).name;
			}
		}
		return "";
	}
}
