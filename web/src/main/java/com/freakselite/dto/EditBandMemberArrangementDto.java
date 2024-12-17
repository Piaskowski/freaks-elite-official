package com.freakselite.dto;

import com.freakselite.model.BandMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EditBandMemberArrangementDto {

    // == fields ==
    private List<BandMember> members = new ArrayList<>();

    // == public methods ==
    public void addMember(BandMember bandMember){
        members.add(bandMember);
    }
}
