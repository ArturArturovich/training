package ru.offenso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.offenso.entities.Node;
import ru.offenso.services.NodeServiceImpl;

@Controller
public class NodeController {


    @Autowired
    private NodeServiceImpl service;

    @RequestMapping(value = "/node/add", method = RequestMethod.POST)
    public String addNode(@ModelAttribute("node") Node node, @RequestParam("currentNodeId") int id, @RequestParam("method") String method) throws Exception {

        Node parent = service.findOneById(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/node/edit", method = RequestMethod.POST)
    public String editNode(@ModelAttribute("node") Node node) throws Exception {
        Node currentNode = service.findOneById(node.getId());
        service.update(currentNode);
        return "redirect:/";
    }

    @RequestMapping(value = "/node/delete/{id}", method = RequestMethod.GET)
    public String deleteNode(@PathVariable int id) throws Exception {
        Node node = service.findOneById(id);
        service.deleteNode(node);
        return "redirect:/";

    }
}
