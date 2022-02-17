package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.utils.AppContext;
import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.utils.Validator;
import com.epam.javacourse.hotel.model.Application;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.model.service.interfaces.IApplicationService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;


public class SubmitApplicationCommand implements ICommand {

    private static final Logger logger = LogManager.getLogger(SubmitApplicationCommand.class);

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        IApplicationService applicationService = AppContext.getInstance().getApplicationService();

        HttpSession session = request.getSession();
        User authorisedUser = (User) session.getAttribute("authorisedUser");

        String roomTypeBySeats = request.getParameter("room_seats");
        String roomClass = request.getParameter("room_class");

        String address = Path.PAGE_ERROR;

        String checkinDate = request.getParameter("checkin_date");
        String checkoutDate = request.getParameter("checkout_date");

        LocalDate checkin = Validator.dateParameterToLocalDate(checkinDate, request);
        LocalDate checkout = Validator.dateParameterToLocalDate(checkoutDate, request);

        if (!Validator.isCorrectDate(checkin, checkout, request)) {
            return new AddressCommandResult(address);
        }

        Application newApplication = new Application();
        newApplication.setUserId(authorisedUser.getId());
        newApplication.setRoomTypeBySeats(roomTypeBySeats);
        newApplication.setRoomClass(roomClass);
        newApplication.setCheckinDate(checkin);
        newApplication.setCheckoutDate(checkout);

        applicationService.create(newApplication);

        return new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT + "&success1=1"); //successful submission ==>);
    }
}
