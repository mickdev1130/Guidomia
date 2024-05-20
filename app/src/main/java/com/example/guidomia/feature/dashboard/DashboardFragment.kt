package com.example.guidomia.feature.dashboard

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.guidomia.R
import com.example.guidomia.core.adapter.CarsAdapter
import com.example.guidomia.core.base.BaseActivity
import com.example.guidomia.core.base.BaseFragment
import com.example.guidomia.core.base.BaseViewModel
import com.example.guidomia.core.extensions.observe
import com.example.guidomia.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    @Inject
    lateinit var adapter: CarsAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_dashboard

    override fun getViewModel(): BaseViewModel = viewModel

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        (activity as? BaseActivity<*>)?.setToolbar(
            show = true,
            showBackButton = false,
            title = getString(R.string.app_name)
        )
    }

    private fun initDropdown(make: List<String>? = null, model: List<String>? = null) {
        val adapterMake = ArrayAdapter(requireContext(), R.layout.item_make, make ?: emptyList())
        binding.actvMake.setAdapter(adapterMake)
        val adapterModel = ArrayAdapter(requireContext(), R.layout.item_make, model ?: emptyList())
        binding.actvModel.setAdapter(adapterModel)
    }

    private fun initViews() {
        binding.apply {
            rvData.adapter = adapter
            binding.actvMake.setOnItemClickListener { adapterView, view, position, id ->
                val selected = adapterView.getItemAtPosition(position) as String
                viewModel?.filterCars(selected, binding.actvModel.text.toString())
            }
            binding.actvModel.setOnItemClickListener { adapterView, view, position, id ->
                val selected = adapterView.getItemAtPosition(position) as String
                viewModel?.filterCars(binding.actvMake.text.toString(), selected)
            }
        }
    }

    private fun initObservers() {
        viewModel.apply {
            observe(carResult) {
                it?.let {
                    adapter.collection = it
                    adapter.notifyDataSetChanged()
                }
            }
            observe(carsInit) {
                it?.let { data ->
                    initDropdown(
                        make = data.map { it.make },
                        model = data.map { it.model },
                    )
                }
            }
        }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

}