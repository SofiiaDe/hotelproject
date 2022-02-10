package com.epam.javacourse.hotel.web.command.norole;

import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.User;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Objects;

/**
 * Internationalization command.
 */
public class I18nInternationalizationCommand implements ICommand {

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        HttpSession session = request.getSession();

        String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
//        String defaultLocale = "defaultLocale";

        if (Objects.equals(request.getParameter("langField"), "en")) {
            Config.set(session, fmtLocale, Path.LOCALE_NAME_EN);
//            session.setAttribute(defaultLocale, "en");
        } else {
            Config.set(session, fmtLocale, Path.LOCALE_NAME_UK);
//            session.setAttribute(defaultLocale, Path.LOCALE_NAME_UK);
        }

        User user = (User) session.getAttribute("authorisedUser");

        return ("manager".equalsIgnoreCase(user.getRole())) ? new RedirectCommandResult(Path.MANAGER_ACCOUNT_PAGE)
                : new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT_PAGE);
    }
}
