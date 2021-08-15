package jie.wen.sonder.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PassengerDataDTO(
    @SerializedName("_id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("trips")
    var trips: Int = 0,
    @SerializedName("airline")
    var airlineDatumDTOS: List<AirlineDataDTO>? = null,
    @SerializedName("__v")
    var v: Int = 0
) : Serializable
