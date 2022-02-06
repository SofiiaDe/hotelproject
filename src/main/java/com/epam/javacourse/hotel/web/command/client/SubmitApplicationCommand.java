package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.IApplicationService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class SubmitApplicationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SubmitApplicationCommand.class);

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IApplicationService applicationService = AppContext.getInstance().getApplicationService();

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        String roomTypeBySeats = request.getParameter("room_seats");
        String roomClass = request.getParameter("room_class");

        LocalDateTime checkInDate = null;
        LocalDateTime checkOutDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate ldCheckin = LocalDate.parse(request.getParameter("checkin_date"), formatter);
            checkInDate = LocalDateTime.of(ldCheckin, LocalDateTime.now().toLocalTime());

            LocalDate ldCheckout = LocalDate.parse(request.getParameter("checkout_date"), formatter);
            checkOutDate = LocalDateTime.of(ldCheckout, LocalDateTime.now().toLocalTime());

        } catch (DateTimeParseException e) {
            logger.error("Cannot get date type");
        }

        Application newApplication = new Application();
        newApplication.setUserId(authorisedUser.getId());
        newApplication.setRoomTypeBySeats(roomTypeBySeats);
        newApplication.setRoomClass(roomClass);
        newApplication.setCheckinDate(checkInDate);
        newApplication.setCheckoutDate(checkOutDate);

        applicationService.create(newApplication);

        return new AddressCommandResult(Path.PAGE_CLIENT_ACCOUNT);
    }
}
