/*
 * SPDX-FileCopyrightText: Paranoid Android
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.settings.network.tether;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

/**
 * Lets hotspot clients use the VPN of the main user. Tethering reads the same
 * secure setting and follows the VPN as its upstream while it is on.
 */
public class TetheringAllowVpnUpstreamsPreferenceController extends TogglePreferenceController {

    private static final String TETHERING_ALLOW_VPN_UPSTREAMS = "tethering_allow_vpn_upstreams";

    public TetheringAllowVpnUpstreamsPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(),
                TETHERING_ALLOW_VPN_UPSTREAMS, 0) == 1;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        return Settings.Secure.putInt(mContext.getContentResolver(),
                TETHERING_ALLOW_VPN_UPSTREAMS, isChecked ? 1 : 0);
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_network;
    }
}
