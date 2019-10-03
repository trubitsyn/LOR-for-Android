/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle

import androidx.core.content.IntentCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

import dev.trubitsyn.lorforandroid.R


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(bundle: Bundle, s: String) {
        addPreferencesFromResource(R.xml.preferences)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == getString(R.string.pref_dark_theme)) {
            val preference = findPreference<Preference>(key) as SwitchPreferenceCompat?
            sharedPreferences.edit().putBoolean(getString(R.string.pref_dark_theme), preference!!.isChecked).apply()
            restart()
        } else if (key == getString(R.string.pref_load_images)) {
            val loadImagesPreference = findPreference<Preference>(key) as SwitchPreferenceCompat?
            sharedPreferences.edit().putBoolean(getString(R.string.pref_load_images), loadImagesPreference!!.isChecked).apply()
        }
    }

    private fun restart() {
        activity!!.finish()
        val intent = activity!!.intent
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(getString(R.string.intent_settings), true)
        activity!!.startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}
