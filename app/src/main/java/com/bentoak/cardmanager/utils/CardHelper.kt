package com.bentoak.cardmanager.utils

import android.content.Context
import android.graphics.drawable.Drawable
import com.bentoak.cardmanager.R

class CardHelper {


    companion object {


        fun getCardTypeIcon(cardType: String, context: Context): Drawable? {

            if (cardType.equals("MasterCard")) {
                return context.resources.getDrawable(R.drawable.master_logo)
            } else if (cardType.equals("VisaCard")) {
                return context.resources.getDrawable(R.drawable.ic_visa_logo)
            } else if (cardType.equals("SoloCard")) {
                return context.resources.getDrawable(R.drawable.solo_logo)
            } else if (cardType.equals("SwitchCard")) {
                return context.resources.getDrawable(R.drawable.switch_logo)
            } else {
                return context.resources.getDrawable(R.drawable.ic_baseline_broken_image_24)
            }
        }


        fun addSpaceInCardNumber(cardNumber: String): String{
            val result = buildString {
                for (i in 0 until cardNumber.length) {
                    if (i % 4 == 0 && i > 0)
                        append(' ')
                    append(cardNumber[i])
                }
            }
            return result
        }
    }
}

