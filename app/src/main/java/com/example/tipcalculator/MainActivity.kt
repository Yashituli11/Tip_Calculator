package com.example.tipcalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
private const val TAG ="MainActivity"
private const val INITIAL_TIP_PERCENTAGE =15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount:EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentageLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount =findViewById(R.id.etBaseAmount)
        seekBarTip=findViewById(R.id.seekBarTip)
        tvTipPercentageLabel=findViewById(R.id.tvTipPercentageLabel)
        tvTipAmount=findViewById(R.id.tvTipAmount)
        tvTotalAmount=findViewById(R.id.tvTotalAmount)
        tvTipDescription=findViewById(R.id.tvTipDescription)
        seekBarTip.progress= INITIAL_TIP_PERCENTAGE
        tvTipPercentageLabel.text="$INITIAL_TIP_PERCENTAGE %"
        updateTipDescription(INITIAL_TIP_PERCENTAGE)
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                Log.i(TAG, "onProgressChanged $p1")
                tvTipPercentageLabel.text="$p1%"
                computeTipAndTotal()
                updateTipDescription(p1)
            }



            private fun computeTipAndTotal() {
                if(etBaseAmount.text.isEmpty()){
                    tvTotalAmount.text=" "
                    tvTipAmount.text=" "
                    return
                }
                //1. To Get the Value of Base and tip percentage

                val baseAmount = etBaseAmount.text.toString().toDouble()
                val tipPercent = seekBarTip.progress
                //2. To calculate the tip and total amount
                val tipAmount =  baseAmount* tipPercent/100
                val totalTipAmount = baseAmount+tipAmount
                //3. Update the UI
                tvTipAmount.text=tipAmount.toString()
                tvTotalAmount.text= totalTipAmount.toString()
            }


            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        } )
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"afterTextChanged $p0")
                computeTipAndTotal()
            }

            private fun computeTipAndTotal() {
                if(etBaseAmount.text.isEmpty()){
                    tvTotalAmount.text=" "
                    tvTipAmount.text=" "
                    return
                }
                //1. To Get the Value of Base and tip percentage

                val baseAmount = etBaseAmount.text.toString().toDouble()
                val tipPercent = seekBarTip.progress
                //2. To calculate the tip and total amount
              val tipAmount =  baseAmount* tipPercent/100
                val totalTipAmount = baseAmount+tipAmount
                //3. Update the UI
                tvTipAmount.text="%.2f".format(tipAmount)
                tvTotalAmount.text= "%.2f".format(totalTipAmount)
            }

        })
    }

    private fun updateTipDescription(initialTipPercentage: Int) {
        val tipDescription = when(initialTipPercentage){
            in 0..9 ->"poor"
            in 10..14 ->"Acceptable"
            in 15..19 -> "good"
            in 20..24 -> "awesome"
            else ->"God"
    }
     tvTipDescription.text =tipDescription
}}