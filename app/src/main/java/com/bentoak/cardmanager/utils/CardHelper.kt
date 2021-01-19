package com.bentoak.cardmanager.utils

import android.content.Context
import android.graphics.drawable.Drawable
import com.bentoak.cardmanager.R

class CardHelper {


    companion object {


        fun getCardNumberIcon(cardNumber: String, context: Context): Drawable? {

            var cardIcon: Int

            if (cardNumber.startsWith("4")) {
                return context.resources.getDrawable(R.drawable.ic_visa_logo)

            } else if (cardNumber.startsWith("2221 1720") || cardNumber.startsWith("5155")) {
                return context.resources.getDrawable(R.drawable.master_logo)

            } else if (cardNumber.startsWith("6334") || cardNumber.startsWith("6767")) {
                return context.resources.getDrawable(R.drawable.solo_logo)

            } else if (cardNumber.startsWith("4903") || cardNumber.startsWith("4905")
                || cardNumber.startsWith("4911") || cardNumber.startsWith("4936") || cardNumber.startsWith(
                    "5641 82"
                )
                || cardNumber.startsWith("6331 10") || cardNumber.startsWith("6333") || cardNumber.startsWith(
                    "6759"
                )
            ) {
                return context.resources.getDrawable(R.drawable.switch_logo)
            } else {
                return context.resources.getDrawable(R.drawable.ic_baseline_broken_image_24)
            }
        }

    }
}

