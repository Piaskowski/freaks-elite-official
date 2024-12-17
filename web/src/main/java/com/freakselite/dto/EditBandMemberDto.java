package com.freakselite.dto;

import com.freakselite.constraints.ImageSize;
import com.freakselite.model.BandMember;
import com.freakselite.util.StaticPath;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class EditBandMemberDto {

    // == fields ==
    private int id;
    @NotEmpty(message = "Imię jest obowiązkowe.")
    private String name;
    @NotEmpty(message = "Nazwisko jest obowiązkowe.")
    private String surname;
    private String nick;
    @NotEmpty(message = "Opis jest obowiązkowy.")
    private String description;
    private String facebookUrl;
    private String instagramUrl;
    @ImageSize(message = "Plik nie może być większy niż " + StaticPath.IMAGE_SIZE)
    private MultipartFile imgFile;

    // == constructors ==
    public EditBandMemberDto(BandMember bandMember){
        this.id = bandMember.getId();
        this.name = bandMember.getName();
        this.surname = bandMember.getSurname();
        this.nick = bandMember.getNick();
        this.description = bandMember.getDescription();
        this.facebookUrl = bandMember.getFacebookUrl();
        this.instagramUrl = bandMember.getInstagramUrl();
    }

    // == public methods ==
    public BandMember toBandMember() {
        BandMember bandMember = new BandMember();

        bandMember.setId(id);
        bandMember.setName(name);
        bandMember.setSurname(surname);
        bandMember.setNick(nick);
        bandMember.setDescription(description);
        bandMember.setFacebookUrl(facebookUrl);
        bandMember.setInstagramUrl(instagramUrl);

        return bandMember;
    }
}
