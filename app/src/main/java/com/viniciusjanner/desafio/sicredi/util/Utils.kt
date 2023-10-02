package com.viniciusjanner.desafio.sicredi.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.Locale

object Utils {

    private val tag: String = Utils::class.java.simpleName

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

    fun openAppMap(address: String, context: Context) {
        try {
            val uriString = "geo:0,0?q=${address}"
            val uri = Uri.parse(uriString)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Nenhum aplicativo disponível!", Toast.LENGTH_SHORT).show()
            Log.e(tag, "openAddressInMap: ${e.message}")
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e(tag, "openAddressInMap: ${e.message}")
        }
    }

    fun openAppSharing(messageSharing: String, context: Context) {
        try {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, messageSharing)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Nenhum aplicativo disponível!", Toast.LENGTH_SHORT).show()
            Log.e(tag, "openSharing: ${e.message}")
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e(tag, "openSharing: ${e.message}")
        }
    }
}
