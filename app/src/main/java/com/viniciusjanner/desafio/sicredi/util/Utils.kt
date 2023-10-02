package com.viniciusjanner.desafio.sicredi.util

import android.content.Context
import android.location.Geocoder
import java.io.IOException
import java.util.Locale

object Utils {

    @Suppress("DEPRECATION")
    fun convertCoordinatesToAddress(latitude: Double?, longitude: Double?, context: Context): String {
        try {
            if (latitude != null && longitude != null && latitude != 0.0 && longitude != 0.0) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addressList = geocoder.getFromLocation(latitude, longitude, 1)
                val size = addressList?.size ?: 0

                if (addressList != null && size > 0) {
                    val address = addressList[0]
                    val addressFull = StringBuilder()

                    // Adicione o nome da rua, se disponível
                    address.thoroughfare?.let { addressFull.append(it) }

                    // Adicione a cidade, se disponível
                    address.locality?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    // Adicione o estado, se disponível
                    address.adminArea?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    // Adicione o país, se disponível
                    address.countryName?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    // Adicione o CEP, se disponível
                    address.postalCode?.let {
                        if (addressFull.isNotEmpty()) addressFull.append(", ")
                        addressFull.append(it)
                    }

                    return addressFull.toString()
                }
            } else {
                // Endereço não encontrado
                return ""
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        // Endereço não encontrado
        return ""
    }
}