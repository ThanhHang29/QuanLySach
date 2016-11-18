package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ttth.item.Book;
import com.ttth.quanlysach.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/04/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    private LayoutInflater inflater;
    private ArrayList<Book>arrBook;
    public BookAdapter(Context context, int resource, ArrayList<Book> data) {
        super(context, resource, data);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.arrBook = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolderBook viewHolder;
        if (view==null){
            view = inflater.inflate(R.layout.item_book,parent,false);
            TextView tvIdBook = (TextView) view.findViewById(R.id.tvIdBook);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            TextView tvDateBook = (TextView) view.findViewById(R.id.tvDateBook);
            viewHolder = new ViewHolderBook();
            viewHolder.tvIdBook = tvIdBook;
            viewHolder.tvTitle = tvTitle;
            viewHolder.tvDateBook = tvDateBook;
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolderBook) view.getTag();
        viewHolder.tvIdBook.setText(""+arrBook.get(position).getId());
        viewHolder.tvTitle.setText(arrBook.get(position).getTitle());
        viewHolder.tvDateBook.setText(arrBook.get(position).getDate());
        return view;
    }
    class ViewHolderBook {
        private TextView tvIdBook,tvTitle,tvDateBook;
    }
}
