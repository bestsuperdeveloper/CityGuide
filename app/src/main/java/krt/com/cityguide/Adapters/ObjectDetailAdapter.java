package krt.com.cityguide.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailFourFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailOneFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailThreeFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailTwoFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentFive;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentFour;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentOne;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentSix;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentThree;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragmentTwo;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/9/17.
 */

public class ObjectDetailAdapter extends FragmentPagerAdapter {


    private FirebaseObjectModel model;
    boolean okFlag;
    public ObjectDetailAdapter(FragmentManager fragmentManager, FirebaseObjectModel model) {
        super(fragmentManager);
        this.model = model;
        okFlag = isPremium();
    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
            {
                return ObjectDetailOneFragment.newInstance(model, "");
            }
            case 1:
            {
                if (okFlag)
                    return ObjectDetailTwoFragment.newInstance(model, "");
                else
                    return ObjectDetailThreeFragment.newInstance(model, "");

            }
            case 2:
            {
                if (okFlag)
                    return ObjectDetailThreeFragment.newInstance(model, "");
                else
                    return ObjectDetailFourFragment.newInstance(model, "");
            }
            case 3:
            {
                return ObjectDetailFourFragment.newInstance(model, "");
            }

            default:
                return null;
        }
//         return ObjectDetailOneFragment.newInstance(model, "");
    }

    @Override
    public int getCount() {
        if (!okFlag) return 3;
        return 4;
    }

    boolean isPremium()
    {
        if (model.getPremium() == null) return false;
        String str1 = Utils.avoidNullString(model.getPremium().get("content_2"));
        String str2 = Utils.avoidNullString(model.getPremium().get("content_4"));
        String str3 = Utils.avoidNullString(model.getPremium().get("content_5"));
        if (str1.equals("") && str2.equals("") && str3.equals(""))  return false;
        return true;

    }
}
