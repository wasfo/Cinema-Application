package org.com.controller.exceptions;

import org.com.exceptions.CinemaNotFoundException;
import org.com.exceptions.SeatAlreadyReservedException;
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
        redirectAttributes.addFlashAttribute("errorMessage",
                "This seat is already purchased.");
        return "redirect:/cinemas/seats?cinemaId=" + cinemaId;
    }

    @ExceptionHandler(CinemaNotFoundException.class)
    public String handleCinemaNotFoundException(CinemaNotFoundException ex,
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage",
                "Cinema is not found.");
        return "redirect:/cinemas";
    }

}
