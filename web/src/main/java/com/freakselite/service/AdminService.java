package com.freakselite.service;

import com.freakselite.model.BandMember;
import com.freakselite.model.Link;
import com.freakselite.model.News;
import com.freakselite.model.PlannedConcert;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface AdminService {

    boolean addPost(News news);

    News getPost(int postId);

    boolean editPost(News post);

    boolean deletePost(int id);

    boolean addConcert(PlannedConcert concert);

    boolean editConcert(PlannedConcert concert);

    boolean deleteConcert(int id);

    PlannedConcert getConcert(int concertId);

    boolean addBandMember(BandMember bandMember);

    boolean editBandMember(BandMember bandMember);

    BandMember getBandMember(int memberId);

    boolean deleteBandMember(int id);

    List<BandMember> getBandMembers();

    void updateBandMemberArrangement(List<BandMember> bandMembers);

    Link getYtVideoLink();

    boolean updateYtVideoLink(Link link);

    Path storeFile(MultipartFile file, String newsImagesDir);
}
