package com.freakselite.dto;

import com.freakselite.constraints.ImageSize;
import com.freakselite.model.News;
import com.freakselite.util.StaticPath;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class NewsDto {

    // == fields ==
    private int id;
    @NotEmpty(message = "Tytuł jest obowiązkowy.")
    private String title;
    @NotEmpty(message = "Treść jest obowiązkowa.")
    private String content;
    private String url;
    @ImageSize(message = "Plik nie może być większy niż " + StaticPath.IMAGE_SIZE)
    private MultipartFile imgFile;

    // == constructors ==
    public NewsDto(News news){
        this.id = news.getId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.url = news.getUrl();
    }

    // == public methods ==
    public News toNews(){
        News news = new News();

        news.setId(id);
        news.setTitle(title);
        news.setContent(content);
        news.setDescription(content);
        news.setUrl(url);

        return news;
    }

    // == private methods ==
    private String contentToDescription(String content){
        try {
            return content.substring(0, 360);
        }catch (StringIndexOutOfBoundsException e){
            return content;
        }
    }
}
