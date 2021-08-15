package jie.wen.sonder.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PassengerListResponseDTO (
    @SerializedName("totalPassengers")
    var totalPassengers: Int = 0,
    @SerializedName("totalPages")
    var totalPages: String? = null,
    @SerializedName("data")
    var data: List<PassengerDataDTO>? = null
) : Serializable
