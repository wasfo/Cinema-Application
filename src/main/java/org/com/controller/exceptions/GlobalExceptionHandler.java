package org.com.controller.exceptions;

import org.com.exceptions.CinemaNotFoundException;
import org.com.exceptions.SeatAlreadyReservedException;
import org.com.exceptions.SeatNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatAlreadyReservedException.class)
    public String handleSeatAlreadyReservedException(SeatAlreadyReservedException ex,
                                                     RedirectAttributes redirectAttributes) {
        Long cinemaId = (Long) redirectAttributes.getAttribute("cinemaId");
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cinemas/seats?cinemaId=" + cinemaId;
    }

    @ExceptionHandler(CinemaNotFoundException.class)
    public String handleCinemaNotFoundException(CinemaNotFoundException ex,
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cinemas";
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public String handleSeatNotFoundException(CinemaNotFoundException ex,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cinemas";
    }

}
