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

package com.android.settings.accessibility.textreading.ui

import android.content.Context
import androidx.preference.Preference
import com.android.settings.R
import com.android.settingslib.metadata.PreferenceMetadata
import com.android.settingslib.metadata.UI_ONLY_PREFERENCE
import com.android.settingslib.preference.PreferenceBinding

/**
 * Preference metadata for the screen density preference shown on the "Display size and text"
 * screen.
 *
 * It binds the legacy [com.android.settings.display.DensityPreference] widget, which shows an
 * edit dialog for the smallest width in dp.
 */
internal class DensityPreference(val isUiOnly: Boolean) : PreferenceMetadata, PreferenceBinding {

    override val key: String
        get() = KEY

    override val purpose: Int
        get() = R.string.density_purpose

    override val title: Int
        get() = R.string.developer_smallest_width

    override fun tags(context: Context): Array<String> {
        if (isUiOnly) {
            return arrayOf(UI_ONLY_PREFERENCE)
        }
        return super.tags(context)
    }

    override fun createWidget(context: Context): Preference =
        com.android.settings.display.DensityPreference(context, null)

    companion object {
        const val KEY = "density"
    }
}
