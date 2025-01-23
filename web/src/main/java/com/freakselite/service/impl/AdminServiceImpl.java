package com.freakselite.service.impl;

import com.freakselite.dao.LinksDao;
import com.freakselite.dao.daoImpl.BandMemberDaoImpl;
import com.freakselite.dao.daoImpl.NewsDaoImpl;
import com.freakselite.dao.daoImpl.PlannedConcertDaoImpl;
import com.freakselite.exception.StorageException;
import com.freakselite.model.BandMember;
import com.freakselite.model.Link;
import com.freakselite.model.News;
import com.freakselite.model.PlannedConcert;
import com.freakselite.service.AdminService;
import com.freakselite.util.StaticPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    // == fields ==
    private final BandMemberDaoImpl bandMemberDaoImpl;
    private final NewsDaoImpl newsDaoImpl;
    private final PlannedConcertDaoImpl concertDao;
    private final LinksDao linksDao;
    @Value(value = "${store-images-path}")
    private String imgStoragePath;

    // == constructors ==
    @Autowired
    public AdminServiceImpl(BandMemberDaoImpl bandMemberDaoImpl, NewsDaoImpl newsDaoImpl, PlannedConcertDaoImpl concertDao, LinksDao linksDao) {
        this.bandMemberDaoImpl = bandMemberDaoImpl;
        this.newsDaoImpl = newsDaoImpl;
        this.concertDao = concertDao;
        this.linksDao = linksDao;
    }

    // == public methods ==
    @Override
    public boolean addConcert(PlannedConcert concert) {
        return concertDao.insert(concert);
    }

    @Override
    public PlannedConcert getConcert(int concertId) {
        return concertDao.findById(concertId);
    }

    @Override
    public boolean editConcert(PlannedConcert concert) {
        return concertDao.update(concert);
    }

    @Override
    public boolean deleteConcert(int id) {
        return concertDao.delete(id);
    }

    @Override
    public List<BandMember> getBandMembers() {
        String path = System.getProperty("user.dir");
        log.info(path);
        log.info(new File("file").getAbsolutePath());
        return bandMemberDaoImpl.getAll();
    }

    @Override
    public void updateBandMemberArrangement(List<BandMember> bandMembers) {
        for (BandMember bandMember : bandMembers){
            BandMember bandMember2 = bandMemberDaoImpl.findById(bandMember.getId());
            bandMember2.setArrangement(bandMember.getArrangement());
            bandMemberDaoImpl.update(bandMember2);
        }
    }

    @Override
    public Link getYtVideoLink() {
        return linksDao.findByName("yt-video");
    }

    @Override
    public boolean updateYtVideoLink(Link link) {
        return linksDao.update(link);
    }

    @Override
    public boolean deleteBandMember(int id) {
        BandMember bandMember = bandMemberDaoImpl.findById(id);
        if (bandMemberDaoImpl.delete(id)){
            File bandMemberImg = new File(StaticPath.STATIC_RESOURCES + bandMember.getImg());
            return bandMemberImg.delete();
        }
        return false;
    }

    @Override
    public boolean addPost(News news) {
        return newsDaoImpl.insert(news);
    }

    @Override
    public News getPost(int postId) {
        return newsDaoImpl.findById(postId);
    }

    @Override
    public boolean editPost(News post) {
        return newsDaoImpl.update(post);
    }

    @Override
    public boolean deletePost(int id) {
        News post = newsDaoImpl.findById(id);
        if (newsDaoImpl.delete(id)){
            if (!post.getImg().equals(StaticPath.DEFAULT_NEWS_IMAGE)){
                File postImg = new File(StaticPath.STATIC_RESOURCES + post.getImg());
                return postImg.delete();
            }
        }
        return false;
    }

    @Override
    public boolean addBandMember(BandMember bandMember) {
        return bandMemberDaoImpl.insert(bandMember);
    }

    @Override
    public boolean editBandMember(BandMember bandMember) {
        return bandMemberDaoImpl.update(bandMember);
    }

    @Override
    public BandMember getBandMember(int memberId) {
        return bandMemberDaoImpl.findById(memberId);
    }

    @Override
    public Path storeFile(MultipartFile file, String newsImagesDir) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            if (newsImagesDir.trim().length() == 0){
                throw new  StorageException("File upload location can not be Empty.");
            }
            Path rootLocation = Paths.get(String.join("" , imgStoragePath, newsImagesDir));

            Path destinationFile = rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            int increment = 1;
            while (true){
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile);
                    break;
                }catch (FileAlreadyExistsException e){
                    String newFilename = incrementFileName(file.getOriginalFilename(), increment);
                    destinationFile = rootLocation.resolve(
                                    Paths.get(newFilename))
                            .normalize().toAbsolutePath();
                    increment++;
                }
            }
            return destinationFile;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    // == private methods ==
    private String incrementFileName(String filename, int increment){
        int dotIndex = filename.lastIndexOf(".");
        return new StringBuffer(filename).insert(dotIndex, increment).toString();
    }
}
