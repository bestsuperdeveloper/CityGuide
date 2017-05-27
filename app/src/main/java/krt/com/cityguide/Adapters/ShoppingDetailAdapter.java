package krt.com.cityguide.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailFourFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailThreeFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailTwoFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentFive;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentFour;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentOne;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentSix;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentThree;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentTwo;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/9/17.
 */

public class ShoppingDetailAdapter extends FragmentPagerAdapter {


    private FirebaseObjectModel model;
    boolean okflag;
    public ShoppingDetailAdapter(FragmentManager fragmentManager, FirebaseObjectModel model) {
        super(fragmentManager);
        this.model = model;
        okflag = isPremium();
    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
            {
                return ShoppingPlaceDetailFragmentOne.newInstance(model, "");

            }
            case 1:
            {
                if (okflag)
                    return ObjectDetailTwoFragment.newInstance(model, "");
                else
                    return ObjectDetailThreeFragment.newInstance(model, "");
            }
            case 2:
            {
                if (okflag)
                    return ObjectDetailThreeFragment.newInstance(model, "");
                else
                    return ObjectDetailFourFragment.newInstance(model, "");

            }
            case 3:
            {
                return ObjectDetailFourFragment.newInstance(model, "");
            }
            case 4:
            {
                return ShoppingPlaceDetailFragmentFive.newInstance(model, "");
            }
            case 5:
            {
                return ShoppingPlaceDetailFragmentSix.newInstance(model, "");
            }
            default:
                break;
        }
         return ShoppingPlaceDetailFragmentOne.newInstance(model, "");
    }

    @Override
    public int getCount() {
        if (isPremium())
        return 4;
        else
            return 3;
    }
    boolean isPremium()
    {
        String str1 = Utils.avoidNullString(model.getPremium().get("content_2"));
        String str2 = Utils.avoidNullString(model.getPremium().get("content_4"));
        String str3 = Utils.avoidNullString(model.getPremium().get("content_5"));
        if (str1.equals("") && str2.equals("") && str3.equals(""))  return false;
        return true;

    }
}
