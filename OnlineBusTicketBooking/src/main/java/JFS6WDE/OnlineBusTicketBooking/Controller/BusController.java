package JFS6WDE.OnlineBusTicketBooking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import JFS6WDE.OnlineBusTicketBooking.Entities.Bus;
import JFS6WDE.OnlineBusTicketBooking.Services.BusServiceImpl;

@Controller
public class BusController {

    @Autowired
    private BusServiceImpl busService;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/addBus")
    public String addBusForm() {
        return "addbus";
    }

    @PostMapping("/addBus")
    public String addBus(Bus bus, Model model) {
        busService.createBus(bus);
        model.addAttribute("success", true);
        return "addbus";
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "busName", "asc", model);
    }

    @GetMapping("/adminBusList")
    public String showAdminBusList(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        return "buslist";
    }

    @GetMapping("/userBusList")
    public String showUserBusList(Model model) {
        List<Bus> buses = busService.viewAllBuses();
        model.addAttribute("buses", buses);
        return "index";
    }

    @GetMapping("/updateBus")
    public String showUpdateBusForm(@RequestParam("busId") long busId, Model model) {
        Bus bus = busService.viewBusById(busId);
        model.addAttribute("bus", bus);
        return "updatebus";
    }

    @PostMapping("/updateBus")
    public String updateBus(@ModelAttribute("bus") Bus bus, Model model) {
        busService.updateBus(bus);
        model.addAttribute("success", true);
        return "redirect:/adminBusList";
    }

    @PostMapping("/deleteBus")
    public String deleteBus(@RequestParam("busId") long busId) {
        busService.deleteBus(busId);
        return "redirect:/adminBusList";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Bus> page = busService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Bus> listBus = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listBus", listBus);
        return "index";
    }
}
