package com.kj.random_chatting.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kj.random_chatting.R;
import com.kj.random_chatting.matchingChattingRoomList.MatchingChattingRoomListDTO;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchingChattingRoomListRecyclerAdapter extends RecyclerView.Adapter<MatchingChattingRoomListRecyclerAdapter.ViewHolder> {
    private ArrayList<MatchingChattingRoomListDTO.matchingChatting> mData = null ;
    // OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener = null;
    private Context context;

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
    public MatchingChattingRoomListRecyclerAdapter(Context mContext, ArrayList<MatchingChattingRoomListDTO.matchingChatting> list) {
        context = mContext;
        mData = list ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView photoNameHolder;
        TextView nickNameHolder;

        ViewHolder(View itemView) {
            super(itemView) ;

            //viewholder이기 때문에 binding 이 되지 않는다. 뷰 객체에 대한 참조. (hold strong reference)
            photoNameHolder = itemView.findViewById(R.id.matching_chatting_room_list_recycler_item_iv_photo_name);
            nickNameHolder = itemView.findViewById(R.id.matching_chatting_room_list_recycler_item_tv_nick_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        String nickName = mData.get(pos).getNickName();
                        String photoName = mData.get(pos).getPhotoName();

                        //DB User 정보 저장 (방 join)
                    }
                }
            });
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MatchingChattingRoomListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.matching_chatting_room_list_recycler_item, parent, false) ;
        MatchingChattingRoomListRecyclerAdapter.ViewHolder vh = new MatchingChattingRoomListRecyclerAdapter.ViewHolder(view) ;

        return vh ;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MatchingChattingRoomListRecyclerAdapter.ViewHolder holder, int position) {

        MatchingChattingRoomListDTO.matchingChatting item = mData.get(position) ;

        String photoName = item.getPhotoName();
        holder.nickNameHolder.setText(item.getNickName());

        Glide.with(context)
                .load(photoName)
                .circleCrop()
                .into(holder.photoNameHolder);

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}