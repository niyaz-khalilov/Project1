package ru.khalilov.project1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Book {
    private int bookId;
    @Size(min = 1, max =100, message = "Имя автора должно содержать от 1 до 100 символов")
    @NotBlank(message = "ФИО автора не может быть пустым или занятым пробелами")
    private String author;
    @NotBlank(message = "Название книги не может быть пустым или занятым пробелами")
    @Size(max = 100, message = "Имя автора должно содержать до 100 символов")
    private String tittle;
    @Min(value = 0, message = "Год выпуска должен быть не раньше 0 года")
    @Max(value = 2022, message = "Введите действительную дату рождения")
    private int issueYear;
}
