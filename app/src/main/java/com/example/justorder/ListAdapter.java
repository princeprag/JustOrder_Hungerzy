package com.example.justorder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ListAdapter extends FirestoreRecyclerAdapter<items_part,ListAdapter.productHolder> {
    private Context mContext;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();


    public ListAdapter(@NonNull FirestoreRecyclerOptions<items_part> options, Context mContext) {
        super(options);
        this.mContext = mContext;

}

    //  public productAdapter(@NonNull FirestoreRecyclerOptions<Product_list> options) {
    //      super(options);
    //  }

    @Override
    protected void onBindViewHolder(@NonNull final productHolder productHolder, int i, @NonNull final items_part product_list) {
        productHolder.Name.setText(product_list.getNAME());
        //Toast.makeText(mContext, product_list.getNAME(), Toast.LENGTH_SHORT).show();
        productHolder.Order2.setText(product_list.getORDER2VEG());
        productHolder.Order3.setText(product_list.getORDER3VEG());

        productHolder.Order2n.setText(product_list.getORDER2VEGN());
        productHolder.Order3n.setText(product_list.getORDER3VEGN());
        String imURL=product_list.getIMAGEURL();
        productHolder.RoomNo.setText(product_list.getROOMNO());
        //Picasso.get().load(imURL).fit().into(productHolder.imageView);
        productHolder.Order1.setText(product_list.getORDER1VEG());
        productHolder.Order1nv.setText(product_list.getORDER1NV());
        productHolder.Order2nv.setText(product_list.getORDER2NV());
        productHolder.Order3nv.setText(product_list.getORDER3NV());

        productHolder.Order1n.setText(product_list.getORDER1VEGN());
        productHolder.Order1nvn.setText(product_list.getORDER1NVN());
        productHolder.Order2nvn.setText(product_list.getORDER2NVN());
        productHolder.Order3nvn.setText(product_list.getORDER3NVN());



       // productHolder.mobile.setText(product_list.getMOBILENO());


        productHolder.listproduct.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {



                if(!(mContext.getClass().getName().equals("com.example.justorder.MyOrder" )))  {
                // Toast.makeText(mContext,product_list.getname(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(mContext,details.class);
                i.putExtra("Name",product_list.getNAME());
                //i.putExtra("ImageURL",product_list.getIMAGEURL());

                i.putExtra("Mobile_no",product_list.getMOBILENO());
                i.putExtra("Order1",product_list.getORDER1VEG());
                i.putExtra("Order2",product_list.getORDER2VEG());
                i.putExtra("Order3",product_list.getORDER3VEG());

                    i.putExtra("Order1nv",product_list.getORDER1NV());
                    i.putExtra("Order2nv",product_list.getORDER2NV());
                    i.putExtra("Order3nv",product_list.getORDER3NV());


                    i.putExtra("Order1n",product_list.getORDER1VEGN());
                    i.putExtra("Order2n",product_list.getORDER2VEGN());
                    i.putExtra("Order3n",product_list.getORDER3VEGN());

                    i.putExtra("Order1nvn",product_list.getORDER1NVN());
                    i.putExtra("Order2nvn",product_list.getORDER2NVN());
                    i.putExtra("Order3nvn",product_list.getORDER3NVN());










                    i.putExtra("RoomNo",product_list.getROOMNO());
                i.putExtra("ParentId",product_list.getPARENTID());
                i.putExtra("UserId",product_list.getUSERID());
                Log.d("ala",mAuth.getCurrentUser().getUid());


                mContext.startActivity(i);
            }else{
                    //Toast.makeText(mContext, mAuth.getCurrentUser().getUid()+"mess", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, "No access!!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_part,parent,false);
        return new productHolder(v);
    }

    public  class productHolder extends RecyclerView.ViewHolder {
        TextView RoomNo;
        TextView Name;
        ImageView imageView;

        TextView Order1,Order2,Order3,Order1n,Order2n,Order3n,mobile;
        TextView Order1nv,Order2nv,Order3nv,Order1nvn,Order2nvn,Order3nvn;
        RelativeLayout listproduct;

        public productHolder(@NonNull View itemView) {
            super(itemView);
            RoomNo=itemView.findViewById(R.id.txt_room_number);
            Name=itemView.findViewById(R.id.txt_names);
            Order2=itemView.findViewById(R.id.txt_item2veg);
           // imageView=itemView.findViewById(R.id.imageView);
            listproduct=itemView.findViewById(R.id.list_product);
            Order1=itemView.findViewById(R.id.txt_itemveg);
            Order3=itemView.findViewById(R.id.txt_item3veg);
            Order1nv=itemView.findViewById(R.id.txt_itemnv);
            Order2nv=itemView.findViewById(R.id.txt_item2nv);
            Order3nv=itemView.findViewById(R.id.txt_item3nv);


            Order2n=itemView.findViewById(R.id.txt_item2vegn);
            Order1n=itemView.findViewById(R.id.txt_itemvegn);
            Order3n=itemView.findViewById(R.id.txt_item3vegn);
            Order1nvn=itemView.findViewById(R.id.txt_itemnvn);
            Order2nvn=itemView.findViewById(R.id.txt_item2nvn);
            Order3nvn=itemView.findViewById(R.id.txt_item3nvn);
            //mobile=itemView.findViewById(R.id.txt_mobile_number);


        }
    }
}

