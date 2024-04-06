package com.viniciusjanner.desafio.sicredi.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.framework.application.EventApp
import com.viniciusjanner.desafio.sicredi.presentation.common.MarginItemDecorationLandscape
import com.viniciusjanner.desafio.sicredi.presentation.common.MarginItemDecorationPortrait
import com.viniciusjanner.desafio.sicredi.util.extensions.dp
import java.util.Locale

@Suppress("unused")
object Utils {

    private val tag: String = Utils::class.java.simpleName

    fun convertCoordinatesToAddressString(
        latitude: Double?, longitude: Double?, context: Context = EventApp.instance, callback: (String) -> Unit
    ) {
        try {
            if (latitude != null && longitude != null) {

                val geocoder = Geocoder(context, Locale.getDefault())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                    geocoder.getFromLocation(latitude, longitude, 1) { addressList ->

                        addressList.firstOrNull()?.let { address ->

                            val addressString = address.getAddressLine(0) ?: ""

                            callback(addressString)
                        }
                    }
                } else {
                    @Suppress("deprecation")
                    geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()?.let { address ->

                        val addressString = address.getAddressLine(0) ?: ""

                        callback(addressString)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "convertCoordinatesToAddress: ${e.message}")
            callback("")
        }
    }

    fun getCustomItemDecoration(context: Context = EventApp.instance): RecyclerView.ItemDecoration? {

        return when (context.resources.configuration.orientation) {

            Configuration.ORIENTATION_PORTRAIT -> {
                MarginItemDecorationPortrait(
                    marginTop = R.dimen.portrait_item_list_margin_top.dp(),
                    marginBottom = R.dimen.portrait_item_list_margin_bottom.dp(),
                    marginBottomLast = R.dimen.portrait_item_list_margin_bottom_last.dp(),
                    marginRight = R.dimen.portrait_item_list_margin_right.dp(),
                    marginLeft = R.dimen.portrait_item_list_margin_left.dp(),
                )
            }

            Configuration.ORIENTATION_LANDSCAPE -> {
                MarginItemDecorationLandscape(
                    marginTop = R.dimen.landscape_item_list_margin_top.dp(),
                    marginBottom = R.dimen.landscape_item_list_margin_bottom.dp(),
                    marginRight = R.dimen.landscape_item_list_margin_right.dp(),
                    marginRightLast = R.dimen.landscape_item_list_margin_right_last.dp(),
                    marginLeft = R.dimen.landscape_item_list_margin_left.dp(),
                )
            }

            else -> null
        }
    }

    fun openAppMap(addressString: String, context: Context = EventApp.instance) {
        try {
            val action = Intent.ACTION_VIEW
            val uriString = "geo:0,0?q=${addressString}"
            val uri = Uri.parse(uriString)
            val flag = Intent.FLAG_ACTIVITY_NEW_TASK

            val intent = Intent(action, uri).addFlags(flag)

            context.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Nenhum aplicativo disponível!", Toast.LENGTH_SHORT).show()
            Log.e(tag, "openAddressInMap: ${e.message}")

        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e(tag, "openAddressInMap: ${e.message}")
        }
    }

    fun openAppSharing(messageSharing: String, context: Context = EventApp.instance) {
        try {
            val sendIntent: Intent = Intent()
                .apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, messageSharing)
                    type = "text/plain"
                }

            val shareTitle = null
            val shareFlag = Intent.FLAG_ACTIVITY_NEW_TASK
            val shareIntent = Intent.createChooser(sendIntent, shareTitle).addFlags(shareFlag)

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
