/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.notification;

import static android.provider.Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED;
import static android.provider.Settings.Global.HEADS_UP_OFF;
import static android.provider.Settings.Global.HEADS_UP_ON;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

public class HeadsUpNotificationsPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {

    private static final String TAG = "HeadsUpPrefContr";

    private SettingObserver mSettingObserver;

    public HeadsUpNotificationsPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        Preference preference = screen.findPreference(getPreferenceKey());
        if (preference != null) {
            mSettingObserver = new SettingObserver(preference);
        }
    }

    @Override
    public void onResume() {
        if (mSettingObserver != null) {
            mSettingObserver.register(mContext.getContentResolver(), true /* register */);
        }
    }

    @Override
    public void onPause() {
        if (mSettingObserver != null) {
            mSettingObserver.register(mContext.getContentResolver(), false /* register */);
        }
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), "heads_up_notifications_enabled");
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_notifications;
    }

    @Override
    public boolean isChecked() {
        return Settings.Global.getInt(mContext.getContentResolver(),
                HEADS_UP_NOTIFICATIONS_ENABLED, HEADS_UP_ON) != HEADS_UP_OFF;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        return Settings.Global.putInt(mContext.getContentResolver(),
                HEADS_UP_NOTIFICATIONS_ENABLED, isChecked ? HEADS_UP_ON : HEADS_UP_OFF);
    }

    class SettingObserver extends ContentObserver {

        private final Uri mHeadsUpNotificationsEnabledUri =
                Settings.Global.getUriFor(HEADS_UP_NOTIFICATIONS_ENABLED);

        private final Preference mPreference;

        public SettingObserver(Preference preference) {
            super(new Handler());
            mPreference = preference;
        }

        public void register(ContentResolver cr, boolean register) {
            if (register) {
                cr.registerContentObserver(mHeadsUpNotificationsEnabledUri, false, this);
            } else {
                cr.unregisterContentObserver(this);
            }
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (mHeadsUpNotificationsEnabledUri.equals(uri)) {
                updateState(mPreference);
            }
        }
    }
}
