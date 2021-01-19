package com.bentoak.cardmanager.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bentoak.cardmanager.R
import com.bentoak.cardmanager.databinding.FragmentCardInfoBinding
import com.bentoak.cardmanager.utils.CardHelper
import com.bentoak.cardmanager.utils.SCAN_CARD_REQUEST_CODE
import com.bentoak.cardmanager.utils.hideSoftKeyboard
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideSoftKeyboard(requireActivity())
    }


    fun onScanPress(v: View?) {
        scanCardIntent = Intent(requireContext(), CardIOActivity::class.java).apply {
            putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false)
            putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
            putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true)
            putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true)
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
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SCAN_CARD_REQUEST_CODE) {

            var resultDisplayStr: String = ""

            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {

                val scanResult: CreditCard = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)!!

                mDataBinding.txtCardNumber.text = CardHelper.addSpaceInCardNumber(scanResult.cardNumber)
                mDataBinding.imgLogo.setImageDrawable(CardHelper.getCardTypeIcon(scanResult.cardType.toString(), requireContext()))


                if (scanResult.isExpiryValid) {
                    mDataBinding.txtExpireDate.text =  """
                    Expiration Date: ${scanResult.expiryMonth}/${scanResult.expiryYear}
                    """.trimIndent()
                }


                if (scanResult.cvv != null) {
                    mDataBinding.txtCVV.setText("""${scanResult.cvv} """)
                }

            } else {
                resultDisplayStr = "Scan was canceled."
            }
        } else {
            Toast.makeText(requireContext(), "Can not scan card. Non-embossed cards are not supported.", Toast.LENGTH_SHORT).show()
        }
    }


    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}