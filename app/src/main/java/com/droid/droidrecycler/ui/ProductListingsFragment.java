package com.droid.droidrecycler.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.droidrecycler.R;
import com.droid.droidrecycler.adapters.ProductListingAdapter;
import com.droid.droidrecycler.interfaces.AsyncCallback;
import com.droid.droidrecycler.models.ProductInfo;
import com.droid.droidrecycler.net.LoadProducts;
import com.droid.droidrecycler.utils.Config;
/**
 *
 * @author pallavi
 */

/**
 * A fragment representing a list of Items.
 *Shows the each products items
 *
 */
public class ProductListingsFragment extends Fragment implements /*AbsListView.OnItemClickListener,*/ AsyncCallback {

    /**
     * The fragment's ListView/GridView.
     */
    private RecyclerView recyclerView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ProductListingAdapter mAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadProducts(getActivity(), this).execute(Config.FETCH_PRODUCTS_URL);
    }

    @Override
    public void onDone(ProductInfo[] productInfos) {
        mAdapter = new ProductListingAdapter(getActivity(), productInfos, this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter!=null)
            recyclerView.setAdapter(mAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_listings, container, false);

          /*Inflate Recycler View*/
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);

        /*Position items in recycler view with LinearLayoutManager*/
        LinearLayoutManager lManager = new LinearLayoutManager(getActivity());
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lManager);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void sortProducts(Config.SORTBY sortby, boolean sortorder){
        switch (sortby){
            case PRICE:
                mAdapter.sortByPrice(sortorder);
                break;

            case RATING:
                mAdapter.sortByRating(sortorder);
                break;

        }
    }

    @Override
    public void navigateToDetails(ProductInfo info) {

        ProductDetailsFragment detailsFragment = new ProductDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("details", info);
        detailsFragment.setArguments(bundle);

        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, detailsFragment); // f1_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();


    }
}
