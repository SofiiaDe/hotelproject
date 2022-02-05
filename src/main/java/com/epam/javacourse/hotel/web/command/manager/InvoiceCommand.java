package com.epam.javacourse.hotel.web.command.manager;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Booking;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class InvoiceCommand implements ICommand {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IBookingService bookingService = AppContext.getInstance().getBookingService();
        List<Booking> allBookings = bookingService.getAllBookings();

        String address;

        if(allBookings.isEmpty()) {
            address = Path.COMMAND_REDIRECT; // ????
        }
        for(Booking booking : allBookings) {
//            if()
        }

        return null;
    }
}
