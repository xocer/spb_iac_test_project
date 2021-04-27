package spb.iac.controller;

import org.json.JSONWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spb.iac.model.SubDirectory;
import spb.iac.service.DirectoryService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {
    private DirectoryService directoryService;

    public IndexController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping("/")
    public String showPage() throws IOException {
        return "index";
    }

    @GetMapping("/getAllDir")
    public void getAllDirectories(HttpServletResponse response) throws IOException {
        response.getWriter().write(JSONWriter.valueToString(directoryService.getAllDirectory()));
    }

    @GetMapping("/details")
    public void getDetailsByDir(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
        List<SubDirectory> list = directoryService.getSubDirById(id);
        response.getWriter().write(JSONWriter.valueToString(directoryService.getSubDirById(id)));
    }

    @PostMapping("/create")
    public String  create(@RequestParam("path") String path) {
        directoryService.save(path);
        return "redirect:/";
    }
}
