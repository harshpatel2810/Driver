package com.classicphoto.rpmfordriver.Adapter;

/**
 * Created by shine-2 on 6/15/2017.
 */

//public class SQLiteListAdapter extends BaseAdapter {
//
//    Context context;
//    ArrayList<String> userID;
//    ArrayList<String> Userlr;
//    ArrayList<String> branch;
//    ArrayList<String> image;
//    ArrayList<String> date;
//
//    public SQLiteListAdapter(
//            Context context2,
//            ArrayList<String> id,
//            ArrayList<String> Userlr,
//            ArrayList<String> branch,
//            ArrayList<String> image,
//            ArrayList<String> date
//    )
//    {
//
//        this.context = context2;
//        this.userID = id;
//        this.Userlr = Userlr;
//        this.branch = branch;
//        this.image = image;
//        this.date = date;
//    }
//
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return userID.size();
//    }
//
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    public View getView(final int position, View child, ViewGroup parent) {
//
//        final Holder holder;
//
//        LayoutInflater layoutInflater;
//
//        if (child == null) {
//            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            child = layoutInflater.inflate(R.layout.contact_list_row, null);
//
//            holder = new Holder();
//
//            holder.textviewid = (TextView) child.findViewById(R.id.textname);
//            holder.textviewname = (TextView) child.findViewById(R.id.text);
//            holder.sync = (ImageView) child.findViewById(R.id.imageyes);
//
//            child.setTag(holder);
//
//        } else {
//
//            holder = (Holder) child.getTag();
//        }
//        holder.textviewname.setText(Userlr.get(position) +" - "+ branch.get(position));
//        holder.textviewid.setText(date.get(position));
////        final Comment cmt = mListComment.get(position);
//      holder.sync.setOnClickListener(new View.OnClickListener(){
//         public void onClick(View view)
//            {
//                SQLITEDATABASE.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.KEY_ID+"="+position, null);
//            }
//        });
//
//        return child;
//    }
//
//    public class Holder {
//        TextView textviewid;
//        TextView textviewname;
//        ImageView sync;
//    }
//
//
//}
