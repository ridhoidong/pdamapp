package com.example.idong.mbanking.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by akbaranjas on 26/01/17.
 */

public abstract class BackStackFragment extends Fragment {

    public static boolean handleBackPressed(FragmentManager fm)
    {
        if(fm.getFragments() != null){
            for(Fragment frag : fm.getFragments()){
                if(frag != null && frag.isVisible() && frag instanceof BackStackFragment){
                    if(((BackStackFragment)frag).onBackPressed()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean onBackPressed()
    {
        FragmentManager fm = getChildFragmentManager();
        if(handleBackPressed(fm)){
            return true;
        } else if(getUserVisibleHint() && fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
            return true;
        }
        return false;
    }

}
