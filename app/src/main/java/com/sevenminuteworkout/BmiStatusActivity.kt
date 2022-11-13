package com.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenminuteworkout.databinding.ActivityBmiStatusBinding

class BmiStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiStatusBinding
    private lateinit var viewModel: BmiStatusViewModel
    private var computedBmiValue: Double = 0.0
    private lateinit var viewModelFactory: BmiStatusActivityViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bmi_status)
        computedBmiValue = intent.getDoubleExtra("BMI_Value", 0.0)
        binding.bmiValue.text = computedBmiValue.toString()
        binding.bmiValuesTable.layoutManager = LinearLayoutManager(this)
        viewModelFactory = BmiStatusActivityViewModelFactory(computedBmiValue)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BmiStatusViewModel::class.java)
        viewModel.bmiValuesListLiveData.observe(this, Observer { bmiValuesList ->
            binding.bmiValuesTable.adapter = BMIValuesListAdapter(applicationContext, bmiValuesList)
            (binding.bmiValuesTable.adapter as BMIValuesListAdapter).notifyDataSetChanged()
        })

    }
}