package text;

import com.gdut.dkmfromcg.commonlib.activities.ProxyActivity;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.minepage.view.MineFragment;

/**
 * Created by dkmFromCG on 2018/5/3.
 * function:
 */

public class MainActivity extends ProxyActivity {
    @Override
    public ProxyFragment setRootFragment() {
        return new MineFragment();
    }
}
