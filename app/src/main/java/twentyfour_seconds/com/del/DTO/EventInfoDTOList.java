package twentyfour_seconds.com.del.DTO;

import java.util.ArrayList;
import java.util.List;

public class EventInfoDTOList {

    private List<EventInfoDTO> dtoArrayList = new ArrayList<>();

    public List<EventInfoDTO> getDtoArrayList() {
        return dtoArrayList;
    }

    public void setDtoArrayList(List<EventInfoDTO> dtoArrayList) {
        this.dtoArrayList = dtoArrayList;
    }

    public void addDtoArrayList(EventInfoDTO eventInfoDTO) {
        this.dtoArrayList.add(eventInfoDTO);
    }
}
