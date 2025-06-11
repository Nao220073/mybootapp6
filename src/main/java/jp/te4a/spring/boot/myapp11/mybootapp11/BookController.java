package jp.te4a.spring.boot.myapp11.mybootapp11;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated; 

@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    BookService bookService;

    //ModelAttributeはフォームの初期化に使う
    @ModelAttribute
    BookForm setUpForm() {
        return new BookForm();
    }
    //書籍一覧表示
    @GetMapping
    String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }
    //書籍作成
    @PostMapping(path="create")
        String create(@Validated BookForm form, BindingResult result, Model model) {
            if(result.hasErrors()){
                return list(model);
            }
            bookService.save(form);
            return "redirect:/books";
    }
    //編集フォーム表示
    @PostMapping(path = "edit", params = "form")
    String editForm(@RequestParam Integer id, BookForm form) {
        BookForm bookForm = bookService.findOne(id);
        BeanUtils.copyProperties(bookForm, form);
        return "books/edit";
    }
    //書籍更新
    @PostMapping(path = "edit")
    String edit(@RequestParam Integer id, @Validated BookForm form, BindingResult result) {
        if(result.hasErrors()){
            return editForm(id, form);
        }
        bookService.save(form);
        return "redirect:/books";
    }
    //書籍削除
    @PostMapping(path = "delete")
    String delete(@RequestParam Integer id) {
        bookService.delete(id);
        return "redirect:/books";
    }
    //編集画面からトップに戻る
    @PostMapping(path = "edit", params = "goToTop")
    String goToTop() {
        return "redirect:/books";
    }
}