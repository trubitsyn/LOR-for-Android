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

package dev.trubitsyn.lorforandroid.ui.util

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

import dev.trubitsyn.lorforandroid.R

object SpinnerViewUtils {
    fun setSpinnerView(activity: Activity, stringArrayResource: Int, defaultSelection: Int, listener: AdapterView.OnItemSelectedListener) {
        val spinnerView = View.inflate(activity, R.layout.spinner, null)
        val spinner = spinnerView.findViewById<View>(R.id.toolbar_spinner) as Spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(activity, stringArrayResource, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.setSelection(defaultSelection)

        val supportBar = (activity as AppCompatActivity).supportActionBar
        supportBar!!.setDisplayShowCustomEnabled(true)
        supportBar.customView = spinnerView

        spinner.post { spinner.onItemSelectedListener = listener }
    }
}
