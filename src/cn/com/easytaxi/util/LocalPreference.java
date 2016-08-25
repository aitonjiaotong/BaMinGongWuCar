package cn.com.easytaxi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class LocalPreference {
	private static final String DEFAULT_SP_NAME = "default_prefs";

	private static LocalPreference instance;
	private SharedPreferences mSharedPreferences;

	private LocalPreference(Context context, String mSpName) {
		if (mSharedPreferences == null) {
			if (TextUtils.isEmpty(mSpName)) {
				mSpName = DEFAULT_SP_NAME;
			}
			mSharedPreferences = context.getApplicationContext().getSharedPreferences(mSpName, Context.MODE_PRIVATE);
		}
	}

	public boolean hasKey(String key) {
		return mSharedPreferences.contains(key);
	}

	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	public boolean setString(String key, String value) {
		return mSharedPreferences.edit().putString(key, value).commit();
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	public boolean setBoolean(String key, boolean value) {
		return mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public int getInt(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	public boolean setInt(String key, int value) {
		return mSharedPreferences.edit().putInt(key, value).commit();
	}

	public float getFloat(String key, float defaultValue) {
		return mSharedPreferences.getFloat(key, defaultValue);
	}

	public boolean setFloat(String key, float value) {
		return mSharedPreferences.edit().putFloat(key, value).commit();
	}

	public long getLong(String key, long defaultValue) {
		return mSharedPreferences.getLong(key, defaultValue);
	}

	public boolean setLong(String key, long value) {
		return mSharedPreferences.edit().putLong(key, value).commit();
	}

	public boolean clearPreference() {
		return mSharedPreferences.edit().clear().commit();
	}

	public static synchronized LocalPreference getInstance(Context context) {
		if (instance == null) {
			instance = new LocalPreference(context, "");
		}
		return instance;
	}

	public static synchronized LocalPreference getInstance(Context context, String mSpName) {
		if (instance == null) {
			instance = new LocalPreference(context, mSpName);
		}
		return instance;
	}
}
