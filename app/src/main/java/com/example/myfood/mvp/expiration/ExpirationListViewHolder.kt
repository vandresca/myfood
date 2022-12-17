package com.example.myfood.mvp.expiration

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfood.databinding.ElementExpirationListBinding


class ExpirationListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val binding = ElementExpirationListBinding.bind(view)

    fun render(
        expirationListModel: ExpirationList,
        currency: String
    ) {
        binding.tvEPName.text = expirationListModel.name
        binding.tvEPDays.text = expirationListModel.days
        when (expirationListModel.expiration) {
            "expired" -> {
                binding.tvEPDays.setBackgroundColor(Color.parseColor("#EA7373"))
                binding.tvEPDaysLabel.setBackgroundColor(Color.parseColor("#EA7373"))
            }
            "0to10days" -> {
                binding.tvEPDays.setBackgroundColor(Color.parseColor("#E4C76E"))
                binding.tvEPDaysLabel.setBackgroundColor(Color.parseColor("#E4C76E"))
            }
            "more10days" -> {
                binding.tvEPDays.setBackgroundColor(Color.parseColor("#80D876"))
                binding.tvEPDaysLabel.setBackgroundColor(Color.parseColor("#80D876"))
            }

        }
        binding.tvEPPrice.text = expirationListModel.price
        binding.tvEPCurrency.text = currency
    }
}