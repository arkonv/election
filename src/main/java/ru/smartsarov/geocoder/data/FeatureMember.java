
package ru.smartsarov.geocoder.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureMember {

    @SerializedName("GeoObject")
    @Expose
    public GeoObject geoObject;

}
