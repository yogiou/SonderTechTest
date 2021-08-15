package jie.wen.sonder.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AirlineDataDTO (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("logo")
    var logo: String? = null,
    @SerializedName("slogan")
    var slogan: String? = null,
    @SerializedName("head_quaters")
    var headQuaters: String? = null,
    @SerializedName("website")
    var webSite: String? = null,
    @SerializedName("established")
    var established: Int = 0
) : Serializable
