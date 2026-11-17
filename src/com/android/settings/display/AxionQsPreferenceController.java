/*
 * Copyright (C) 2026 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.display;

import static android.provider.Settings.System.AXION_QS_ENABLED;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.core.BasePreferenceController;

/**
 * Selects which Quick Settings implementation SystemUI uses.
 *
 * <p>The value is only read when the QS fragment is created. SystemUI watches the setting and
 * restarts itself immediately when it changes, so no further action is needed here.
 */
public class AxionQsPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {

    private static final int ON = 1;
    private static final int OFF = 0;

    public AxionQsPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        ((SwitchPreferenceCompat) preference).setChecked(
                Settings.System.getInt(mContext.getContentResolver(), AXION_QS_ENABLED, ON) != 0);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return Settings.System.putInt(mContext.getContentResolver(), AXION_QS_ENABLED,
                ((Boolean) newValue) ? ON : OFF);
    }
}
