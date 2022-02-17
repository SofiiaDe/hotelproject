package com.epam.javacourse.hotel.web.listener;


import com.epam.javacourse.hotel.exception.AppException;
import com.epam.javacourse.hotel.model.service.interfaces.IBookingService;
import com.epam.javacourse.hotel.model.service.interfaces.IInvoiceService;
import com.epam.javacourse.hotel.utils.AppContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler for issuing checking invoice payment within 2 days.
 * The booking is cancelled automatically in case of invoice not being paid by the due date.
 */
@WebListener
public class PaymentScheduler implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(PaymentScheduler.class);

    private ScheduledExecutorService scheduler;

    private final IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();
    private final IBookingService bookingService = AppContext.getInstance().getBookingService();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new DailyInvoiceBookingUpdater(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }


    /**
     * Runnable class for task of updating invoices' status and cancelling bookings.
     */
    public class DailyInvoiceBookingUpdater implements Runnable {

        @Override
        public void run() {

            try {
                invoiceService.updateInvoiceStatusToCancelled();
            } catch (AppException exception) {
                logger.error("Scheduler can't cancel unpaid invoice", exception);
            }

            try {
                bookingService.cancelUnpaidBookings();
            } catch (AppException exception) {
                logger.error("Scheduler can't cancel unpaid booking", exception);
            }
            logger.info("Daily invoice and booking updates were completed by scheduler");
        }

    }
}
