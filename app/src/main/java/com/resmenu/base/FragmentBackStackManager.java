package com.resmenu.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentBackStackManager {
        private boolean isInflating = false;
        private static FragmentBackStackManager mFragmentBackStackManager;
        private AppCompatActivity backStackActivity;

        //constructor
    private FragmentBackStackManager(AppCompatActivity appCompatActivity) {
        this.isInflating = false;
        backStackActivity = appCompatActivity;
    }

        //returns instance of class
        public static FragmentBackStackManager getInstance(AppCompatActivity appCompatActivity) {
        mFragmentBackStackManager = new FragmentBackStackManager(appCompatActivity);
        return mFragmentBackStackManager;
    }

        //returns fragmentmanager asociated with  activity
        public FragmentManager getSupportFragmentManager() {
        return backStackActivity.getSupportFragmentManager();
    }

        /**
         * @param paramFragment
         * @param tag                            always pass YourFragmentName.class.getName()
         * @param addToBackStack
         * @param isReplaceWithBackStackInstance
         */
        //to loadFragment
        public int loadFragment(Fragment paramFragment, String tag, boolean addToBackStack, boolean isReplaceWithBackStackInstance, int fragmentHolderLayoutId) {
        int commitId = -1;
        Fragment fragment = null;
        if (isReplaceWithBackStackInstance) {
            fragment = backStackActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment == null) {
                fragment = paramFragment;
            }
        } else {
            fragment = paramFragment;
        }
        if (fragment != null)
            commitId = doChangeFragment(paramFragment, tag, addToBackStack, fragmentHolderLayoutId);
        return commitId;

    }
        //beginTranscation of loaded fragment
        private synchronized int doChangeFragment(Fragment fragment, String tag, boolean addToBackStack, int fragmentHolderLayoutId) {
        isInflating = true;
        int commitId = -1;
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = backStackActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(fragmentHolderLayoutId, fragment, tag);

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }

            commitId = fragmentTransaction.commit();

        }
        isInflating = false;
        return commitId;
    }

        // clean the stack first,all fragments will be cleared
        public void popBackStackInclusive() {
        backStackActivity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

        // returns the number of entries currently in backstack
        public int getBackStackEntryCount() {
        return backStackActivity.getSupportFragmentManager().getBackStackEntryCount();
    }

        //finds a fragment that was identified by the given tag
        public Fragment getFragmentByTag(String tag) {
        return backStackActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }

        //returns the name that was supplied to FragmentTransaction.addToBackStack(String) when creating.
        public String getFragmentTagByIndex(int index) {
        FragmentManager.BackStackEntry backStackEntry = backStackActivity.getSupportFragmentManager().getBackStackEntryAt(index);
        return backStackEntry.getName();
    }

        //returns unique id of fragment at given index
        public int getFragmentIdByIndex(int index) {
        FragmentManager.BackStackEntry backStackEntry = backStackActivity.getSupportFragmentManager().getBackStackEntryAt(index);
        return backStackEntry.getId();
    }

        //remove all fragments from backstack
        public void removeAllFragmentsFromTransaction() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null)
                fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commit();
    }

        public void popBackStackInclusiveUptoFragmentId(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
            fragmentManager.popBackStack(fragment.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        fragmentTransaction.commit();
    }

        public void popBackStack(int commitId, int flag) {
        getSupportFragmentManager().popBackStack(commitId, flag);
    }

        public void popBackStackImmediately() {
        getSupportFragmentManager().popBackStackImmediate();
    }

    }