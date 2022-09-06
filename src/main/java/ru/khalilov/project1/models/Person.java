package ru.khalilov.project1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class Person {
    private int personId;
    @Pattern(regexp = "^[А-ЯЁ][а-яё]*([-][А-ЯЁ][а-яё]*)?\\s[А-ЯЁ][а-яё]*\\s[А-ЯЁ][а-яё]*$", message = "ФИО должно соответствовать образцу: Иванов Иван Иванович")
    @Size(min = 3, max =100, message = "ФИО должно содержать от 3 до 100 символов")
    private String fullName;
    @Min(value = 1900, message = "Введите действительную дату рождения")
    @Max(value = 2022, message = "Введите действительную дату рождения")
    private int birthdayDate;
}
