package com.viniciusjanner.desafio.sicredi.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.util.Locale

@Suppress("unused")
object Utils {

    private val tag: String = Utils::class.java.simpleName

    fun convertCoordinatesToAddressString(latitude: Double?, longitude: Double?, context: Context, callback: (String) -> Unit) {
        try {
            if (latitude != null && longitude != null && latitude != 0.0 && longitude != 0.0) {

                val geocoder = Geocoder(context, Locale.getDefault())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                    geocoder.getFromLocation(latitude, longitude, 1) { addressList ->

                        addressList.firstOrNull()?.let { address ->

                            val addressString: String = address.getAddressLine(0) ?: ""

                            callback(addressString)
                        }
                    }
                } else {
                    @Suppress("DEPRECATION")
                    geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()?.let { address ->

                        val addressString: String = address.getAddressLine(0) ?: ""

                        callback(addressString)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "convertCoordinatesToAddress: Exception = ${e.message}")
            callback("")
        }
    }

    fun openAppMap(addressString: String, context: Context) {
        try {
            val uriString = "geo:0,0?q=${addressString}"
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
