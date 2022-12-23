package com.myfood.mvp.expiration

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myfood.databinding.ElementExpirationListBinding


class ExpirationListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    //Obtenemos el binding de la vista del item
    val binding = ElementExpirationListBinding.bind(view)

    //LLenamos los datos del item
    fun render(
        expirationListModel: ExpirationList,
        currency: String
    ) {
        binding.tvEPName.text = expirationListModel.name
        binding.tvEPDays.text = expirationListModel.days
        binding.tvEPPrice.text = expirationListModel.price
        binding.tvEPCurrency.text = currency

       //Según la caducidad del producto lo mostramos en un color u otro.
        when (expirationListModel.expiration) {

            //En rojo
            "expired" -> {
                binding.tvEPDays.setBackgroundColor(Color.parseColor("#EA7373"))
                binding.tvEPDaysLabel.setBackgroundColor(Color.parseColor("#EA7373"))
            }

            //En amarillo anaranjado
            "0to10days" -> {
                binding.tvEPDays.setBackgroundColor(Color.parseColor("#E4C76E"))
                binding.tvEPDaysLabel.setBackgroundColor(Color.parseColor("#E4C76E"))
            }

            //En verde
            "more10days" -> {
                binding.tvEPDays.setBackgroundColor(Color.parseColor("#80D876"))
                binding.tvEPDaysLabel.setBackgroundColor(Color.parseColor("#80D876"))
            }

        }
    }
}