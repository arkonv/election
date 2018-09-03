
package ru.smartsarov.geocoder.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDataProperty {

    @SerializedName("GeocoderResponseMetaData")
    @Expose
    public GeocoderResponseMetaData geocoderResponseMetaData;

}
