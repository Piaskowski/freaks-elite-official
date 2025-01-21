package com.freakselite.dao;

import com.freakselite.model.BandMember;

import java.util.List;

public interface BandMemberDao {
    BandMember findById(int id);
    List<BandMember> getAll();
    boolean insert(BandMember t);
    boolean update(BandMember t);
    boolean delete(int id);
}
