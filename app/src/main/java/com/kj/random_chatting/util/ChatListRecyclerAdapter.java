package com.kj.random_chatting.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kj.random_chatting.R;

import java.util.ArrayList;

public class ChatListRecyclerAdapter extends RecyclerView.Adapter<ChatListRecyclerAdapter.ViewHolder> {
    private ArrayList<RecyclerItem> mData = null ;
    // OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener = null;

    //===== [Click 이벤트 구현을 위해 추가된 코드] ==========================
    // OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }


    // OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
    }
    //======================================================================

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ChatListRecyclerAdapter(ArrayList<RecyclerItem> list) {
        mData = list ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomName ;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            roomName = itemView.findViewById(R.id.roomName) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        itemClickListener.onItemClick(v, pos);
                    }
                }
            });
        }


        public TextView getRoomName() {
            return roomName;
        }

        public void setRoomName(TextView roomName) {
            this.roomName = roomName;
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ChatListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycler_item, parent, false) ;
        ChatListRecyclerAdapter.ViewHolder vh = new ChatListRecyclerAdapter.ViewHolder(view) ;

        //===== [Click 이벤트 구현을 위해 추가된 코드] =====================
        /*
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                int position = vh.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    data = vh.getRoomName().getText().toString();
                }
                //itemClickListener.onItemClicked(position, data);
            }
        });

         */
        //==================================================================

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ChatListRecyclerAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position) ;

        holder.roomName.setText(item.getRoomName()) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}