package org.com.controller.exceptions;

import org.com.exceptions.CinemaException;
import org.com.exceptions.SeatException;
import org.com.exceptions.SeatNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatException.class)
    public String handleSeatsException(SeatException ex,
                                                     RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/seats?error&cinemaId=" + ex.getCinemaId();
    }

    @ExceptionHandler(CinemaException.class)
    public String handleCinemaNotFoundException(CinemaException ex,
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "cinemas";
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public String handleSeatNotFoundException(CinemaException ex,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "cinemas";
    }

}
