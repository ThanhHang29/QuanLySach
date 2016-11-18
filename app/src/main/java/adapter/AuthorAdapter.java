package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ttth.item.Author;
import com.ttth.quanlysach.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 05/04/2016.
 */
public class AuthorAdapter extends ArrayAdapter<Author> {
    private LayoutInflater inflater;
    private ArrayList<Author>arrAuthor;
    public AuthorAdapter(Context context, int resource, ArrayList<Author> data) {
        super(context, resource, data);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.arrAuthor = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view==null){
            view = inflater.inflate(R.layout.item_author,parent,false);
            TextView tvID = (TextView) view.findViewById(R.id.tvID);
            TextView tvItemSeri = (TextView) view.findViewById(R.id.tvItemSeri);
            TextView tvItemName = (TextView) view.findViewById(R.id.tvItemName);
            viewHolder = new ViewHolder();
            viewHolder.tvID = tvID;
            viewHolder.tvItemSeri = tvItemSeri;
            viewHolder.tvItemName = tvItemName;
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        viewHolder.tvID.setText(""+arrAuthor.get(position).getId());
        viewHolder.tvItemSeri.setText(arrAuthor.get(position).getSeri());
        viewHolder.tvItemName.setText(arrAuthor.get(position).getName());
        return view;
    }
    class ViewHolder {
        private TextView tvID,tvItemSeri,tvItemName;
    }
}
