package com.epam.javacourse.hotel.web;


import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.model.service.IBookingService;
import com.epam.javacourse.hotel.model.service.IInvoiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler for issuing an invoice and checking its payment within 2 days.
 * The booking is cancelled automatically in case of invoice not being paid by the due date.
 */
@WebListener
public class InvoiceScheduler implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(InvoiceScheduler.class);

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
                invoiceService.generateInvoiceForBooking();
            } catch (AppException exception) {
                logger.error("Can't generate invoice for booking", exception);
            }

            try {
                invoiceService.updateInvoiceStatusToCancelled();
            } catch (AppException exception) {
                logger.error("Can't cancel unpaid invoice", exception);
            }

            try {
                bookingService.cancelUnpaidBookings();
            } catch (AppException exception) {
                logger.error("Can't cancel unpaid booking", exception);
            }
            logger.info("Daily invoice and booking updates were completed by scheduler");
        }

    }
}
