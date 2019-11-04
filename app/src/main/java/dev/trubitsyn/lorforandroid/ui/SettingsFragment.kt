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
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import dev.trubitsyn.lorforandroid.R


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val KEY_DARK_THEME = getString(R.string.pref_dark_theme)
    private val KEY_LOAD_IMAGES = getString(R.string.pref_load_images)

    override fun onCreatePreferences(bundle: Bundle, s: String) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            KEY_DARK_THEME -> {
                val preference = findPreference<Preference>(key) as SwitchPreferenceCompat?
                sharedPreferences.edit { putBoolean(KEY_DARK_THEME, preference!!.isChecked) }
                refreshScreen()
            }
            KEY_LOAD_IMAGES -> {
                val preference = findPreference<Preference>(key) as SwitchPreferenceCompat?
                sharedPreferences.edit { putBoolean(KEY_LOAD_IMAGES, preference!!.isChecked) }
            }
        }
    }

    private fun refreshScreen() {
        activity?.apply {
            finish()
            Intent().apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(ARG_RESTART_ACTIVITY, true)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        const val ARG_RESTART_ACTIVITY = "restartActivity"
    }
}
