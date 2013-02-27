package com.koushikdutta.widgets;



public class ListContentFragment extends BetterListFragment {
    @Override
    public ListContentFragmentInternal createFragmentInterface() {
        return new ListContentFragmentInternal(this);
    }

}
