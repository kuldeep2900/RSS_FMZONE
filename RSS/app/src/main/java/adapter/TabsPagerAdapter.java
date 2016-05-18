package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Menu;

import home.example.com.rss.DevotionalFragment;
import home.example.com.rss.HealthFragment;
import home.example.com.rss.PoliticsFragment;
import home.example.com.rss.R;
import home.example.com.rss.ReligionFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {


	static final int ITEMS = 3;
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}





	@Override
	public Fragment getItem(int position) {

		switch (position) {
		case 0:
			// Top Rated fragment activity
			return new ReligionFragment();

		case 1:
			// Games fragment activity
			return new DevotionalFragment();
		case 2 :
			// Movies fragment activity
			return new HealthFragment();

			default:
				// Movies fragment activity
				return new PoliticsFragment();
		}

	//	return null;
	}



	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
