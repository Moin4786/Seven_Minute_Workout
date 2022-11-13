package com.sevenminuteworkout

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sevenminuteworkout.databinding.ActivityBmicalculatorBinding

class BMIcalculator : AppCompatActivity() {
    private lateinit var binding: ActivityBmicalculatorBinding
    private lateinit var viewModel: BMIViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bmicalculator)
        viewModel = ViewModelProvider(this).get(BMIViewModel::class.java)
        binding.computeBmiButton.setOnClickListener{
            viewModel.computeBmi(
                binding.weightEditText.text.toString(),
                binding.heightEditText.text.toString()
            )
        }
        viewModel.validationMessageLiveData.observe(this) { errorMessage ->
            showToastMessage(errorMessage)
        }
        viewModel.bmiLiveData.observe(this) { bmiValue ->
            launchChartActivity(bmiValue)
        }
    }

    private fun showToastMessage(errorMessage: String?) {
        Toast.makeText(
            applicationContext,
            errorMessage,
            LENGTH_LONG
        ).show()
    }
    private fun launchChartActivity(bmiValue: Double) {
        val intent = Intent(this@BMIcalculator, BmiStatusActivity::class.java)
        intent.putExtra("BMI_Value", bmiValue)
        startActivity(intent)
    }
}