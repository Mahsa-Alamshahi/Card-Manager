package com.bentoak.cardmanager.utils

import android.app.Activity
import android.view.WindowManager



const val  SCAN_CARD_REQUEST_CODE = 100


fun hideSoftKeyboard(activity: Activity) {
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}