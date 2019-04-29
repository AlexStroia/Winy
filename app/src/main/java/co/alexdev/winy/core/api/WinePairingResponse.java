package co.alexdev.winy.core.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;

public class WinePairingResponse {

    public List<String> pairedWines;
    @SerializedName("pairingText")
    public String pairingText;
    @SerializedName("productMatches")
    public List<ProductMatches> productMatches;
}
