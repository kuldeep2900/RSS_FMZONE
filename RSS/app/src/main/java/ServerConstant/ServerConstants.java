package ServerConstant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ayush on 12/5/16.
 */
public class ServerConstants {



    @SuppressLint("NewApi")
    public ServerConstants(Context context) {

    }


    public String tab1parshingdevotio()
    {
        StringBuilder content = new StringBuilder();
        try
        {


            String final_url="http://www.trinityapplab.in/RSS/submenu.php?menu=Spiritual";
            URL url = new URL(final_url);


            URLConnection urlConnection = url.openConnection();


            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String line;


            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();


        } catch (Exception e) {
            e.printStackTrace();
            return  content.toString();

        }
        return  content.toString() ;

    }

    public String tab1parshingpolitics()
    {
        StringBuilder content = new StringBuilder();
        try
        {


            String final_url="http://www.trinityapplab.in/RSS/submenu.php?menu=Politics";
            URL url = new URL(final_url);


            URLConnection urlConnection = url.openConnection();


            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String line;


            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();


        } catch (Exception e) {
            e.printStackTrace();
            return  content.toString();

        }
        return  content.toString() ;

    }
    public String tab1parshinghealth()
    {
        StringBuilder content = new StringBuilder();
        try
        {


            String final_url="http://www.trinityapplab.in/RSS/submenu.php?menu=Health";
            URL url = new URL(final_url);


            URLConnection urlConnection = url.openConnection();


            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String line;


            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();


        } catch (Exception e) {
            e.printStackTrace();
            return  content.toString();

        }
        return  content.toString() ;

    }
    public String tab1parshing()
    {
        StringBuilder content = new StringBuilder();
       try
       {


        String final_url="http://www.trinityapplab.in/RSS/submenu.php?menu=Hinduism";
        URL url = new URL(final_url);


        URLConnection urlConnection = url.openConnection();


        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));

        String line;


        while ((line = bufferedReader.readLine()) != null) {
            content.append(line + "\n");
        }
        bufferedReader.close();


    } catch (Exception e) {
        e.printStackTrace();
        return  content.toString();

    }
    return  content.toString() ;

}
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}

