package bg.replacername.replacername.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class Name(
    var cyrillicName: String,
    var latinName: String): Parcelable