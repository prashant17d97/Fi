package com.prashant.fi

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Year
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivityViewModel :ViewModel() {

    val message = MutableLiveData<String>()
    val finish = MutableLiveData<Boolean>()
    var focusOn = MutableLiveData<FocusedOn>()
    val date: ObservableField<String> = ObservableField("")
    val month: ObservableField<String> = ObservableField("")
    val year: ObservableField<String> = ObservableField("")
    val pan: ObservableField<String> = ObservableField("")

    private val yearInt = Year.now().value

    /** yearInt is used to limit the user to enter the
    less than 18 from current year*/

    fun onClicks(view: View) {
        when (view.id) {
            R.id.btnNext -> {
                if (isValid()) {
                    message.value = "Details submitted successfully"
                    finish.value = true
                }
            }
            R.id.tvDontHavePan -> {
                finish.value = true
            }
        }
    }

    /**
     * isValid fun validate the entered values.
     * if any value not found as per requirement
     * then return false with message and value
     * for field to focus using FocusedOn
     * */

    private fun isValid(): Boolean {
        when {
            (pan.get()?.isBlank() == true) -> {
                focusOn.value = FocusedOn.PAN
                message.value = "Pan card field is not allowed to be empty"
                return false
            }
            !panValidator(pan.get() ?: "") -> {
                focusOn.value = FocusedOn.PAN
                message.value = "Pan card should follow the \"ABCDE1234F\" pattern!"
                return false
            }
            (date.get()?.isBlank() == true) -> {
                message.value = "Date field is not allowed to be empty"
                focusOn.value = FocusedOn.DATE
                return false
            }
            date.get().toString().toInt() > 31 || date.get().toString().toInt() < 1 -> {
                message.value = "Date between 1 to 31 is allowed"
                focusOn.value = FocusedOn.DATE
                return false
            }
            (month.get()?.isBlank() == true) -> {
                focusOn.value = FocusedOn.MONTH
                message.value = "Month field is not allowed to be empty"
                return false
            }
            month.get().toString().toInt() > 12 || month.get().toString().toInt() < 1 -> {
                focusOn.value = FocusedOn.MONTH
                message.value = "Month between 1 to 12 is allowed"
                return false
            }
            (year.get()?.isBlank() == true) -> {
                focusOn.value = FocusedOn.YEAR
                message.value = "Year field is not allowed to be empty"
                return false
            }
            year.get().toString().toInt() < 1900 || year.get().toString()
                .toInt() > (yearInt - 18) -> {
                focusOn.value = FocusedOn.YEAR
                message.value = "Year less than 18 from current year allowed"
                return false
            }
        }
        return true
    }

    /**
     * panValidator is declared to match
     * the pan pattern with entered pan value
     * */
    private fun panValidator(pan: String): Boolean {
        val pattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]")
        val matcher: Matcher = pattern.matcher(pan)
        return matcher.matches()

    }

}

/**
 * This is defined to limit for focus value.
 * So, there is no chance to make mistake
 * while request field focus.
 * */
enum class FocusedOn {
    PAN,
    DATE,
    MONTH,
    YEAR
}
