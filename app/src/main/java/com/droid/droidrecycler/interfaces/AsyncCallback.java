package com.droid.droidrecycler.interfaces;


import com.droid.droidrecycler.models.ProductInfo;

/**
 *
 * @author pallavi
 */
public interface AsyncCallback {
    public void onDone(ProductInfo[] productInfos);

    public void navigateToDetails(ProductInfo info);
}
