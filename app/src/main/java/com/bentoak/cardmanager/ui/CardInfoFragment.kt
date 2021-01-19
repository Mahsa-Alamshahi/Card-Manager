package com.bentoak.cardmanager.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bentoak.cardmanager.R
import com.bentoak.cardmanager.databinding.FragmentCardInfoBinding
import com.bentoak.cardmanager.utils.CardHelper
import com.bentoak.cardmanager.utils.SCAN_CARD_REQUEST_CODE
import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import java.text.SimpleDateFormat
import java.util.*


class CardInfoFragment : Fragment() {


    private var scanCardIntent: Intent? = null
    private lateinit var mDataBinding: FragmentCardInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_info,
            container,
            false)

        mDataBinding.fragmentProvider = this
        return mDataBinding.root
    }



    fun onScanPress(v: View?) {
        scanCardIntent = Intent(requireContext(), CardIOActivity::class.java).apply {
            putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
            putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
            putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false)
        }
        startActivityForResult(scanCardIntent, SCAN_CARD_REQUEST_CODE)
    }



    fun showDateDialogFragment(view: View?) {

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(2010, 1, 1)

        val yearMonthPickerDialog = YearMonthPickerDialog(requireContext(),
            YearMonthPickerDialog.OnDateSetListener { year, month ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                val dateFormat = SimpleDateFormat("MM/yy")
                mDataBinding.txtExpireDate.setText(dateFormat.format(calendar.time))
            })

        yearMonthPickerDialog.show()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === SCAN_CARD_REQUEST_CODE) {

            var resultDisplayStr: String

            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {

                val scanResult: CreditCard = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)!!
                resultDisplayStr = """
                 Card Number: ${scanResult.redactedCardNumber}
                  """.trimIndent()
                mDataBinding.txtCardNumber.text = scanResult.redactedCardNumber
                mDataBinding.imgLogo.setImageDrawable(CardHelper.getCardNumberIcon(scanResult.redactedCardNumber, requireContext()))


                if (scanResult.isExpiryValid) {
                    resultDisplayStr += """
                    Expiration Date: ${scanResult.expiryMonth}/${scanResult.expiryYear}
                    """.trimIndent()
                    mDataBinding.txtExpireDate.text = resultDisplayStr
                }


                if (scanResult.cvv != null) {
                    resultDisplayStr += """CVV has ${scanResult.cvv.length} digits."""
                    mDataBinding.txtCVV.text = scanResult.cvv
                }

            } else {
                resultDisplayStr = "Scan was canceled."
            }
        }

    }

}