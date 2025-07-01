package JFS6WDE.OnlineBusTicketBooking.Services;

import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Exception.AdminException;
import JFS6WDE.OnlineBusTicketBooking.Exception.ResourceNotFound;

import java.util.List;

import org.springframework.data.domain.Page;

public interface BusService {
    Bus createBus(Bus bus) throws ResourceNotFound, AdminException;

    Bus updateBus(Bus bus) throws ResourceNotFound;

    void deleteBus(long busId) throws ResourceNotFound, AdminException;

    Bus viewBusById(long busId) throws ResourceNotFound;

    List<Bus> viewBusByBusType(String busType) throws ResourceNotFound;

    List<Bus> viewAllBuses() throws ResourceNotFound;

    Page<Bus> findPaginated(int pageNo,int pageSize,String sortField,String sortDirection);

    List<Bus> findByRouteFromAndRouteTo(String routeFrom, String routeTo);
}
