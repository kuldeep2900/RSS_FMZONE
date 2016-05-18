package home.example.com.rss;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
  ArrayList<String> result;
    Context context;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Context con, ArrayList<String> prgmNameList, Resources res) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=con;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter, null);
        holder.tv=(TextView) rowView.findViewById(R.id.sanatan_content);
        holder.tv.setText(result.get(position));


        return rowView;
    }

}