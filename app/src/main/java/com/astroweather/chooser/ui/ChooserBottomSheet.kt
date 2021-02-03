package com.astroweather.chooser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.astroweather.R
import com.astroweather.chooser.adapter.ChooserAdapter
import com.astroweather.chooser.listener.ChooserListener
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.chooser.model.ChooserUiModel
import com.astroweather.common.decorator.FirstLastDecorator
import com.astroweather.common.ui.FullBottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.chooser_bottom_sheet.*

@AndroidEntryPoint
class ChooserBottomSheet : FullBottomSheetDialogFragment() {

    private val viewModel: ChooserViewModel by viewModels()
    private lateinit var cityListener: ChooserListener
    private lateinit var optionAdapter: ChooserAdapter

    private val onRowClick: (cityId: Int?) -> Unit = {
        dismissAllowingStateLoss()
        cityListener.onChoosingOption(it)
    }

    private val listenUpdates = Observer { model: ChooserUiModel ->
        model.showOptions?.let { showOptions(it) }
        model.showErrorMessage?.let { showErrorSnackbar(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachToParentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chooser_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.chooserModel.observe(this, listenUpdates)
        initView()
        viewModel.fetchOptions()
    }

    private fun onAttachToParentFragment() = activity?.let {
        try {
            cityListener = it as ChooserListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$it must implement CityListener")
        }
    }

    private fun initView() {
        optionAdapter = ChooserAdapter(onRowClick)
        optionsList.apply {
            adapter = optionAdapter
            addItemDecoration(FirstLastDecorator(top = R.dimen._10sdp, bottom = R.dimen._10sdp))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showOptions(cities: List<ChooserModel>) {
        optionAdapter.submitList(cities)
    }

    private fun showErrorSnackbar(errorMessage: Int) = context?.let {
        Snackbar.make(viewParent, getString(errorMessage), Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(it, R.color.red))
            .show()
    }

    companion object {
        val TAG = ChooserBottomSheet::class.simpleName
        fun newInstance() = ChooserBottomSheet()
    }
}