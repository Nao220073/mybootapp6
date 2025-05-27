package jp.te4a.spring.boot.myapp8.mybootapp8;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model; 

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
        String create(BookForm form, Model model) {
            bookService.create(form);
            //return "redirect:/books";
            return list(model);
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
    String edit(@RequestParam Integer id, BookForm form) {
        bookService.update(form);
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