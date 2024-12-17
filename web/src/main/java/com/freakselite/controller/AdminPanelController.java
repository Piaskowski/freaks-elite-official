package com.freakselite.controller;

import com.freakselite.dto.*;
import com.freakselite.model.BandMember;
import com.freakselite.model.Link;
import com.freakselite.model.News;
import com.freakselite.model.PlannedConcert;
import com.freakselite.service.AdminService;
import com.freakselite.util.PageMappings;
import com.freakselite.util.StaticPath;
import com.freakselite.util.ViewNames;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
public class AdminPanelController {

    // == fields ==
    private final AdminService adminService;

    // == constructors ==
    @Autowired
    public AdminPanelController(AdminService adminService) {
        this.adminService = adminService;
    }

    // == get mappings ==
    @GetMapping(PageMappings.ADMIN_PANEL)
    public String getAdminPanel(Model model){
        model.addAttribute("isAdmin", true);
        return ViewNames.ADMIN_PANEL;
    }

    @GetMapping(PageMappings.ADMIN_PANEL_ADD_POST)
    public String getAddPost(Model model, HttpServletRequest request){
        model.addAttribute("page", request.getServletPath());
        model.addAttribute("newsDto", new NewsDto());
        model.addAttribute("action", "/admin-panel/dodaj-post");
        model.addAttribute("isAdmin", true);

        return ViewNames.ADMIN_PANEL_ADD_POST;
    }

    @GetMapping(PageMappings.ADMIN_PANEL + "/{postId}/edytuj-post")
    public String getEditPost(@PathVariable("postId") int postId, Model model){

        News post = adminService.getPost(postId);
        NewsDto newsDto = new NewsDto(post);

        model.addAttribute("newsDto", newsDto);
        model.addAttribute("action", "/admin-panel/" + postId +"/edytuj-post");
        model.addAttribute("isAdmin", true);

        return ViewNames.ADMIN_PANEL_ADD_POST;
    }

    @GetMapping(PageMappings.ADMIN_PANEL_BAND_SETTINGS)
    public String getBandSettings(Model model, HttpServletRequest request){
        List<BandMember> bandMembers =  adminService.getBandMembers();
        model.addAttribute("page", request.getServletPath());
        model.addAttribute("bandMembers", bandMembers);
        model.addAttribute("isAdmin", true);
        model.addAttribute("deleteObject", new BandMember());

        return ViewNames.ADMIN_PANEL_BAND_SETTINGS;
    }

    @GetMapping(PageMappings.ADMIN_PANEl_BAND_ARRANGEMENT)
    public String getBandArrangement(Model model){
        List<BandMember> bandMembers =  adminService.getBandMembers();
        EditBandMemberArrangementDto editBandMemberArrangementDto = new EditBandMemberArrangementDto();
        bandMembers.forEach(editBandMemberArrangementDto::addMember);


        model.addAttribute("editBandMemberArrangementDto", editBandMemberArrangementDto);
        model.addAttribute("isAdmin", true);

        return ViewNames.ADMIN_PANEl_BAND_ARRANGEMENT;
    }

    @GetMapping(PageMappings.ADMIN_PANEL_BAND_MEMBER)
    public String getAddBandMember(Model model, HttpServletRequest request){
        model.addAttribute("bandMemberDto", new BandMemberDto());
        model.addAttribute("page", request.getServletPath());
        model.addAttribute("action", "/admin-panel/band-member");
        model.addAttribute("isAdmin", true);

        return ViewNames.ADMIN_PANEL_BAND_MEMBER;
    }

    @GetMapping(PageMappings.ADMIN_PANEL + "/{memberId}/edytuj-członka-zespołu")
    public String getEditBandMember(@PathVariable("memberId") int memberId, Model model){

        BandMember bandMember = adminService.getBandMember(memberId);
        EditBandMemberDto bandMemberDto = new EditBandMemberDto(bandMember);

        model.addAttribute("bandMemberDto", bandMemberDto);
        model.addAttribute("action", "/admin-panel/" + memberId +"/edytuj-członka-zespołu");

        return ViewNames.ADMIN_PANEL_BAND_MEMBER;
    }

    @GetMapping(PageMappings.ADMIN_PANEL_ADD_CONCERT)
    public String getAddConcert(Model model, HttpServletRequest request){

        model.addAttribute("concertDto", new PlannedConcertDto());
        model.addAttribute("page", request.getServletPath());
        model.addAttribute("action", "/admin-panel/dodaj-wydarzenie");
        model.addAttribute("isAdmin", true);

        return ViewNames.ADMIN_PANEL_ADD_CONCERT;
    }

    @GetMapping(PageMappings.ADMIN_PANEL + "/{concertId}/edytuj-wydarzenie")
    public String getEditConcert(@PathVariable("concertId") int concertId, Model model){

        PlannedConcert concert = adminService.getConcert(concertId);
        PlannedConcertDto concertDto = new PlannedConcertDto(concert);

        model.addAttribute("concertDto", concertDto);
        model.addAttribute("action", "/admin-panel/" + concertId +"/edytuj-wydarzenie");
        model.addAttribute("isAdmin", true);

        return ViewNames.ADMIN_PANEL_ADD_CONCERT;
    }

    @GetMapping(PageMappings.ADMIN_PANEL_LINKS)
    public String getLinks(Model model, HttpServletRequest request){
        LinkDto linkDto = new LinkDto(adminService.getYtVideoLink());

        model.addAttribute("linkDto", linkDto);
        model.addAttribute("isAdmin", true);
        model.addAttribute("page", request.getServletPath());

        return ViewNames.ADMIN_PANEL_LINKS;
    }

    // == post mappings ==
    @PostMapping(PageMappings.ADMIN_PANEL_ADD_POST)
    public String addPost(@Valid @ModelAttribute("newsDto") NewsDto newsDto,
                          BindingResult result,
                          Model model){

        // validation
        if (result.hasErrors()){
            return ViewNames.ADMIN_PANEL_ADD_POST;
        }

        // adding the post
        News post = newsDto.toNews();

        if (newsDto.getImgFile().isEmpty()){
            post.setImg(StaticPath.DEFAULT_NEWS_IMAGE);
        } else {
            post.setImg(StaticPath.NEWS_IMAGES +
                    adminService.storeFile(newsDto.getImgFile(), StaticPath.NEWS_IMAGES).getFileName());
        }

        // set post date
        post.setDate(LocalDate.now());

        // add post to the database
        if (adminService.addPost(post)){
            model.addAttribute("info", "Post został pomyślnie dodany");
            return ViewNames.SUCCESS;
        }else {
            return ViewNames.ADMIN_ERROR;
        }
    }

    @PostMapping(PageMappings.ADMIN_PANEL + "/{postId}/edytuj-post")
    public String editPost(@PathVariable("postId") int postId,
                           @Valid @ModelAttribute("newsDto") NewsDto newsDto,
                           BindingResult result,
                           Model model){

        // check validations
        if (result.hasErrors()){
            return ViewNames.ADMIN_PANEL_ADD_POST;
        }

        News post = newsDto.toNews();
        post.setId(postId);

        // update image
        if (!newsDto.getImgFile().isEmpty()){
            Path destinationFile = adminService.storeFile(newsDto.getImgFile(), StaticPath.NEWS_IMAGES);

            if (!destinationFile.getFileName().toString().isEmpty()){
                post.setImg(StaticPath.NEWS_IMAGES + destinationFile.getFileName());
            }
        } else {
            post.setImg(adminService.getPost(postId).getImg());
        }

        // update the existing post
        if (adminService.editPost(post)){
            model.addAttribute("info", "Edycja postu ukończona pomyślnie");
            return ViewNames.SUCCESS;
        }else {
            return ViewNames.ADMIN_ERROR;
        }
    }

    @PostMapping(PageMappings.NEWS + "/delete")
    public String deletePost(@ModelAttribute("deleteObject") News deleteObject){
        if (adminService.deletePost(deleteObject.getId())){
            return "redirect:/" + PageMappings.NEWS;
        }

        return "redirect:/" + PageMappings.ERROR;
    }

    @PostMapping(PageMappings.ADMIN_PANEL_ADD_CONCERT)
    public String addConcert(@Valid @ModelAttribute("concertDto") PlannedConcertDto concertDto,
                             BindingResult result,
                             Model model){

        // check validations
        if (result.hasErrors()){
            return ViewNames.ADMIN_PANEL_ADD_CONCERT;
        }

        PlannedConcert concert = concertDto.toPlannedConcert();

        // add concert to the database
        if (adminService.addConcert(concert)){
            model.addAttribute("info", "Wydarzenie zostało utworzone pomyślnie.");
            return ViewNames.SUCCESS;
        }

        return "redirect:/" + PageMappings.ADMIN_PANEL;
    }

    @PostMapping(PageMappings.ADMIN_PANEL + "/{concertId}/edytuj-wydarzenie")
    public String editConcert(@PathVariable("concertId") int concertId,
                              @Valid @ModelAttribute("concertDto") PlannedConcertDto concertDto,
                              BindingResult result,
                              Model model){

        // check validations
        if (result.hasErrors()){
            return ViewNames.ADMIN_PANEL_ADD_CONCERT;
        }

        PlannedConcert concert = concertDto.toPlannedConcert();
        // edit concert with the given id
        concert.setId(concertId);

        if (adminService.editConcert(concert)){
            model.addAttribute("info", "Edycja wydarzenia ukończona pomyślnie");
            return ViewNames.SUCCESS;
        }else {
            return ViewNames.ADMIN_ERROR;
        }

    }

    @PostMapping(PageMappings.CONCERTS + "/delete")
    public String deleteConcert(@ModelAttribute("deleteObject") PlannedConcert deleteObject){
        if (adminService.deleteConcert(deleteObject.getId())){
            return "redirect:/" + PageMappings.CONCERTS;
        }

        return "redirect:/" + PageMappings.ERROR;
    }

    // == band ==
    @PostMapping(PageMappings.ADMIN_PANEL_BAND_MEMBER)
    public String addBandMember(
            @Valid @ModelAttribute("bandMemberDto") BandMemberDto bandMemberDto,
            BindingResult result,
            Model model){

        // check validations
        if (result.hasErrors()){
            model.addAttribute("action", "/admin-panel/band-member");
            return ViewNames.ADMIN_PANEL_BAND_MEMBER;
        }

        BandMember bandMember = bandMemberDto.toBandMember();

        // upload img
        Path destinationFile = adminService.storeFile(bandMemberDto.getImgFile(), StaticPath.BAND_IMAGES);
        bandMember.setImg(StaticPath.BAND_IMAGES + destinationFile.getFileName());

        adminService.addBandMember(bandMember);

        return "redirect:/" + PageMappings.ADMIN_PANEL;
    }

    @PostMapping(PageMappings.ADMIN_PANEL + "/{memberId}/edytuj-członka-zespołu")
    public String editBandMember(@PathVariable("memberId") int memberId,
                                 @Valid @ModelAttribute("bandMemberDto") EditBandMemberDto bandMemberDto,
                                 BindingResult result,
                                 Model model){

        // check validations
        if (result.hasErrors()){
            model.addAttribute("action", "/admin-panel/" + memberId +"/edytuj-członka-zespołu");
            return ViewNames.ADMIN_PANEL_BAND_MEMBER;
        }

        BandMember bandMember = bandMemberDto.toBandMember();
        // edit the band member with the given id
        bandMember.setId(memberId);

        // update image
        if (!bandMemberDto.getImgFile().isEmpty()){
            Path destinationFile = adminService.storeFile(bandMemberDto.getImgFile(), StaticPath.BAND_IMAGES);

            if (!destinationFile.getFileName().toString().isEmpty()){
                bandMember.setImg(StaticPath.BAND_IMAGES + destinationFile.getFileName());
            }
        } else {
            bandMember.setImg(adminService.getBandMember(memberId).getImg());
        }

        if (adminService.editBandMember(bandMember)){
            model.addAttribute("info", "Edycja danych członka zespołu ukończona pomyślnie.");
            return ViewNames.SUCCESS;
        }else {
            return ViewNames.ADMIN_ERROR;
        }
    }

    @PostMapping(PageMappings.ADMIN_PANEL_BAND_SETTINGS + "/delete")
    public String deleteBandMember(@ModelAttribute("deleteObject") BandMember deleteObject){
        if (adminService.deleteBandMember(deleteObject.getId())){
            return "redirect:/" + PageMappings.ADMIN_PANEL_BAND_SETTINGS;
        }

        return "redirect:/" + PageMappings.ERROR;
    }

    @PostMapping(PageMappings.ADMIN_PANEl_BAND_ARRANGEMENT)
    public String changeBandArrangement(@ModelAttribute("editBandMemberArrangementDto") EditBandMemberArrangementDto editBandMemberArrangementDto){
        adminService.updateBandMemberArrangement(editBandMemberArrangementDto.getMembers());
        return "redirect:/" + PageMappings.ADMIN_PANEL_BAND_SETTINGS;
    }

    @PostMapping(PageMappings.ADMIN_PANEL_LINKS)
    public String updateLinks(@Valid @ModelAttribute("linkDto") LinkDto linkDto, BindingResult result, Model model){

        // check validations
        if (result.hasErrors()){
            return ViewNames.ADMIN_PANEL_LINKS;
        }

        Link link = adminService.getYtVideoLink();
        link.setUrl(linkDto.getUrl());

        if (adminService.updateYtVideoLink(link)){
            model.addAttribute("info", "Zaktualizowano zmiany.");
            return ViewNames.SUCCESS;
        }
        return "redirect:/" + PageMappings.ERROR;
    }
}
