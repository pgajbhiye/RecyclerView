package com.droid.droidrecycler.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.droidrecycler.R;
import com.droid.droidrecycler.interfaces.AsyncCallback;
import com.droid.droidrecycler.models.ProductInfo;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author pallavi
 */
public class ProductListingAdapter extends RecyclerView.Adapter<ProductListingAdapter.DataHolder> {


    private Activity context;
    private ProductInfo[] products;
    private AsyncCallback asyncCallback;


    public ProductListingAdapter(Activity context, ProductInfo[] products, AsyncCallback asyncCallback) {
        this.context = context;
        this.products = products;
        this.asyncCallback = asyncCallback;
    }

    public void sortByPrice(final boolean reverse) {
        Arrays.sort(products, new Comparator<ProductInfo>() {
            @Override
            public int compare(ProductInfo lhs, ProductInfo rhs) {
                if (reverse) {
                    return lhs.getPrice().compareTo(rhs.getPrice());
                }
                return rhs.getPrice().compareTo(lhs.getPrice());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByRating(final boolean reverse) {
        Arrays.sort(products, new Comparator<ProductInfo>() {
            @Override
            public int compare(ProductInfo lhs, ProductInfo rhs) {
                if (reverse)
                    return lhs.getRating().compareTo(rhs.getRating());
                return rhs.getRating().compareTo(lhs.getRating());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.length : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*Inflate the view*/
    @Override
    public ProductListingAdapter.DataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View productRow = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_row, viewGroup, false);

        DataHolder dataHolder = new DataHolder(productRow);

        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder dataHolder, int i) {
        dataHolder.productName.setText(products[i].getName());
        dataHolder.productPriceUSD.setText(products[i].getPrice());
        Picasso.with(context).load(products[i].getImage()).into(dataHolder.productImage);

    }


    public final class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView productImage;
        public TextView productName;
        public TextView productPriceUSD;

        public DataHolder(View productItem) {
            super(productItem);
            this.productImage = (ImageView) productItem.findViewById(R.id.productimage);
            productName = (TextView) productItem.findViewById(R.id.productname);
            productPriceUSD = (TextView) productItem.findViewById(R.id.productpriceusd);
            productItem.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            asyncCallback.navigateToDetails(products[getPosition()]);

        }
    }
}
