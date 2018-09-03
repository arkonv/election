
package ru.smartsarov.geocoder.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoObjectCollection {

    @SerializedName("metaDataProperty")
    @Expose
    public MetaDataProperty metaDataProperty;
    @SerializedName("featureMember")
    @Expose
    public List<FeatureMember> featureMember = null;

}
