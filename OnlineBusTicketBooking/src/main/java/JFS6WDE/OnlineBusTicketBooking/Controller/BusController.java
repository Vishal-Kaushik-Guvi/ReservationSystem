package JFS6WDE.OnlineBusTicketBooking.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;

@Controller
public class BusController {

    @Autowired
    private BusServiceImpl busService;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/browseBuses";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        model.addAttribute("pageTitle", "GoReserve - Home");
        model.addAttribute("body", "index");
        return "layout";
    }

    @GetMapping("/addBus")
    public String addBusForm(Model model) {
        model.addAttribute("pageTitle", "Add New Bus");
        model.addAttribute("body", "addbus");
        return "layout";
    }

    @PostMapping("/addBus")
    public String addBus(Bus bus, Model model) {
        busService.createBus(bus);
        model.addAttribute("success", true);
        model.addAttribute("pageTitle", "Add New Bus");
        model.addAttribute("body", "addbus");
        return "layout";
    }

    @GetMapping("/adminBusList")
    public String showAdminBusList(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "Admin Bus List");
        model.addAttribute("body", "buslist");
        return "layout";
    }

    @GetMapping("/userBusList")
    public String showUserBusList(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "Available Buses");
        model.addAttribute("body", "index");
        return "layout";
    }

    @GetMapping("/updateBus")
    public String showUpdateBusForm(@RequestParam("busId") long busId, Model model) {
        Bus bus = busService.viewBusById(busId);
        model.addAttribute("bus", bus);
        model.addAttribute("pageTitle", "Update Bus");
        model.addAttribute("body", "updatebus");
        return "layout";
    }

    @PostMapping("/updateBus")
    public String updateBus(@ModelAttribute("bus") Bus bus) {
        busService.updateBus(bus);
        return "redirect:/adminBusList";
    }

    @PostMapping("/deleteBus")
    public String deleteBus(@RequestParam("busId") long busId) {
        busService.deleteBus(busId);
        return "redirect:/adminBusList";
    }

    // ✅ Simplified: Load all buses for client-side pagination
    @GetMapping("/browseBuses")
    public String showAllBuses(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "Browse Buses");
        model.addAttribute("body", "showbuses");
        return "layout";
    }
    
    @GetMapping("/book-ticket")
    public String showBookingPage(Principal principal) {
        if (principal == null) {
            return "redirect:/login?error"; // message will be handled by login page
        }
        return "redirect:/browseBuses";
    }

}
