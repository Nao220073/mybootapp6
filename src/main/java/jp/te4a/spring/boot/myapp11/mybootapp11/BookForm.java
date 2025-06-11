package jp.te4a.spring.boot.myapp11.mybootapp11;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BookForm {
    private Integer id ;
    @NotNull
    @Size(min=3)
    private String title;
    @Size(min=3, max=20)
    private String writer;
    private String publisher;
    @Min(0)
    private Integer price;
}
