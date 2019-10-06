package dev.trubitsyn.lorforandroid.ui.section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loopj.android.http.RequestParams

class ItemsViewModelFactory<R>(
        private val itemFactory: ItemFactory,
        private val path: String,
        private val requestParams: RequestParams?
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemsViewModel<R>(path, requestParams, itemFactory) as T
    }
}