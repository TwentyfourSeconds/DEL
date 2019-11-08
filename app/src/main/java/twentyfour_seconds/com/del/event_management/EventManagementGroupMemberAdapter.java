package twentyfour_seconds.com.del.event_management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import twentyfour_seconds.com.del.R;

public class EventManagementGroupMemberAdapter extends BaseAdapter{

    Context context;
    LayoutInflater layoutInflater = null;
    public List<GroupMembersDTO> groupMembersDTOArrayList = new ArrayList<GroupMembersDTO>();

    public EventManagementGroupMemberAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMemberList(List<GroupMembersDTO> groupMembersDTOArrayList) {
        this.groupMembersDTOArrayList = groupMembersDTOArrayList;
    }

    @Override
    public int getCount() {
        return groupMembersDTOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupMembersDTOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return groupMembersDTOArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.event_management_maintenance_group_member_list_row,parent,false);

        ((TextView)convertView.findViewById(R.id.group_member_name)).setText(groupMembersDTOArrayList.get(position).getUsername());
        ImageView profileImageView = ((ImageView)convertView.findViewById(R.id.group_member_image));

        Log.d("position", position + "");
        Log.d("BaseAdapter username", groupMembersDTOArrayList.get(position).toString());
//        Log.d("BaseAdapter userimage", groupMembersDTOArrayList.get(position).getProfileImageUrl());

        Picasso.get().load(groupMembersDTOArrayList.get(position).getProfileImageUrl()).into(profileImageView);

        return convertView;
    }

}
