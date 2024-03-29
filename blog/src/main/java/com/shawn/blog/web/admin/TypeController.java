package com.shawn.blog.web.admin;


import com.shawn.blog.po.Type;
import com.shawn.blog.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

   @Autowired
   private TypesService typesService;


    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){

        model.addAttribute("page",typesService.listType(pageable));

        return "admin/types";

    }


    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typesService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){

        Type typeByName = typesService.getTypeByName(type.getName());
        if(typeByName!=null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }


        Type type1 = typesService.saveType(type);
        if(type1==null){
            //
            attributes.addFlashAttribute("message","操作失败");
        }else {
            //
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/types";


    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        Type typeByName = typesService.getTypeByName(type.getName());
        if(typeByName!=null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }


        Type type1 = typesService.updateType(id,type);
        if(type1==null){
            //
            attributes.addFlashAttribute("message","操作失败");
        }else {
            //
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typesService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }


}
