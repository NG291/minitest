package com.document.documentTest.controller;

import com.document.documentTest.model.Document;
import com.document.documentTest.service.DocumentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentServiceInterface documentService;

    @GetMapping
    public String getList(Model model) {
        List<Document> documentList = documentService.findAll();
        model.addAttribute("documentList", documentList);
        return "index";
    }

    @GetMapping("create-form")
    public String showForm(Model model) {
        model.addAttribute("document", new Document());
        return "create";
    }

    @PostMapping("save")
    public ModelAndView save(@ModelAttribute("document") Document document, RedirectAttributes redirectAttributes) {
        documentService.save(document);
        redirectAttributes.addFlashAttribute("message", "Document saved successfully");
        return new ModelAndView("redirect:/documents");
    }

    @GetMapping("{id}/update-form")
    public String editForm (Model model, @PathVariable long id) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "update";
    }

    @PostMapping("update")
    public String updateDocument(@ModelAttribute("document") Document document, RedirectAttributes redirectAttributes) {
        documentService.save(document);
        redirectAttributes.addFlashAttribute("message", "Document updated successfully");
        return "redirect:/documents";
    }

    @GetMapping("{id}/detail")
    public String detail (Model model, @PathVariable long id) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "detail";
    }

    @GetMapping("{id}/delete-form")
    public String deleteForm (@PathVariable long id, Model model) {
        Document document = documentService.findById(id);
        model.addAttribute("document", document);
        return "delete";
    }

    @PostMapping("delete")
    public String deleteDocument(@ModelAttribute("document") Document document, RedirectAttributes redirectAttributes) {
        documentService.remove(document.getId());
        redirectAttributes.addFlashAttribute("message", "Document deleted successfully");
        return "redirect:/documents";
    }
}
