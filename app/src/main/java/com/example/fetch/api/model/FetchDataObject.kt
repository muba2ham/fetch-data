package com.example.fetch.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FetchDataObject(val id: Long, val listId: Long, val name: String?, var digit: Long?) : Parcelable