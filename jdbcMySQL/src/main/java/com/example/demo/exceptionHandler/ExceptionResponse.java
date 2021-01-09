package com.example.demo.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Этот класс является лишь демонстрационной модели обработки
 * исключений и пишется в соответсвтии с требованиями каждой конкретной фирмы/проекта
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String time;
    private String error;
    private String message;
}
