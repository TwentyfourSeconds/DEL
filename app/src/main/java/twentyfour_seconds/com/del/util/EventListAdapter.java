package twentyfour_seconds.com.del.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.R;

public class EventListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    List<EventInfoDTO> eventInfoDTOList;

    public EventListAdapter(Context context) {
        this.context = context;
//        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setEventList(EventInfoDTOList eventInfoDTOList) {
        this.eventInfoDTOList = eventInfoDTOList.getDtoArrayList();
    }

    @Override
    public int getCount() {
        return eventInfoDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventInfoDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.valueOf(eventInfoDTOList.get(position).getEventId());
    }

    public long getItemAtPosition(int position) {
        return Integer.valueOf(eventInfoDTOList.get(position).getEventId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.row, parent,false);

        ((TextView)convertView.findViewById(R.id.image)).setText("test");
        ((TextView)convertView.findViewById(R.id.title)).setText(eventInfoDTOList.get(position).getEventName());
        ((TextView)convertView.findViewById(R.id.large_area)).setText(eventInfoDTOList.get(position).getLargeArea());
        ((TextView)convertView.findViewById(R.id.term)).setText(eventInfoDTOList.get(position).getEventDay());
        ((TextView)convertView.findViewById(R.id.eventstatus)).setText(eventInfoDTOList.get(position).getClosedDay());
        ((TextView)convertView.findViewById(R.id.member)).setText(eventInfoDTOList.get(position).getMember());

        return convertView;
    }
}