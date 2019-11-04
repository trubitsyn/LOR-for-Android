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

import android.os.Bundle
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.BaseActivity
import uk.co.senab.photoview.PhotoView

class ImageActivity : BaseActivity() {
    private val photoView by lazy { findViewById<PhotoView>(R.id.photoView)!! }
    private val args by navArgs<ImageActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        Glide.with(this)
                .load(args.bitmap)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoView)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
