package org.com.controller.exceptions;

import org.com.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeatException.class)
    public String handleSeatsException(SeatException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/seats?error&cinemaId=" + ex.getCinemaId();
    }

    @ExceptionHandler(CinemaException.class)
    public String handleCinemaException(CinemaException ex,
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cinemas";
    }

    @ExceptionHandler(CinemaCreationException.class)
    public String handleCinemaCreationException(CinemaCreationException ex,
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/admin/cinema/create";
    }
    @ExceptionHandler(SeatNotFoundException.class)
    public String handleSeatNotFoundException(CinemaException ex,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cinemas";
    }

    @ExceptionHandler(TicketException.class)
    public String handleTicketExceptions(TicketException ex,
                                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/tickets";
    }

}
