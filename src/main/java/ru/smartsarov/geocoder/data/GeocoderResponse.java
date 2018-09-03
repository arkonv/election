
package ru.smartsarov.geocoder.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderResponse {

    @SerializedName("response")
    @Expose
    public Response response;

}
