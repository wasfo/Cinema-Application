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
        redirectAttributes.addFlashAttribute("errorMessage",
                "This seat is already reserved.");
        return "redirect:/seats";
    }

    @ExceptionHandler(CinemaNotFoundException.class)
    public String handleCinemaNotFoundException(CinemaNotFoundException ex,
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage",
                "Cinema is not found.");
        return "redirect:/cinemas";
    }

}
