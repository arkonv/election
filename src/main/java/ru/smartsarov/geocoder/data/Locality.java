
package ru.smartsarov.geocoder.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locality {

    @SerializedName("LocalityName")
    @Expose
    public String localityName;
    @SerializedName("DependentLocality")
    @Expose
    public DependentLocality dependentLocality;

}
