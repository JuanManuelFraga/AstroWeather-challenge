package com.astroweather.chooser.ui

import androidx.annotation.StringRes
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astroweather.chooser.domain.repository.ChooserRepository
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.chooser.model.ChooserUiModel
import com.astroweather.util.Result
import com.astroweather.util.coroutines.DispatcherProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChooserViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: ChooserRepository
) : ViewModel() {

    private val _chooserModel = MutableLiveData<ChooserUiModel>()
    val chooserModel: LiveData<ChooserUiModel>
        get() = _chooserModel

    fun fetchOptions() = viewModelScope.launch(dispatcherProvider.io()) {
        when (val result = repository.fetchOptions()) {
            is Result.Success -> updateUi(showOptions = result.data)
            is Result.Error -> updateUi(showErrorMessage = result.errorMessageRes)
        }
    }

    private suspend fun updateUi(
        showOptions: List<ChooserModel>? = null,
        @StringRes showErrorMessage: Int? = null
    ) = withContext(dispatcherProvider.ui()) {
        _chooserModel.value = ChooserUiModel(showOptions, showErrorMessage)
    }
}