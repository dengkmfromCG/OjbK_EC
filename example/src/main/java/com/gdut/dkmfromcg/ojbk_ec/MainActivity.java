package com.gdut.dkmfromcg.ojbk_ec;

import com.gdut.dkmfromcg.ojbk_ec.fragments.ExampleFragment;
import com.gdut.dkmfromcg.ojkb.activities.ProxyActivity;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

public class MainActivity extends ProxyActivity {


    @Override
    public ProxyFragment setRootFragment() {
        return new ExampleFragment();
    }

}
