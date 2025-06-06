package jp.te4a.spring.boot.myapp10.mybootapp10;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    public BookForm save(BookForm bookForm) {
        BookBean bookBean = new BookBean();
        BeanUtils.copyProperties(bookForm, bookBean);
        bookRepository.save(bookBean);
        return bookForm;
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
    
    public List<BookForm> findAll() {
        return bookRepository.findAll().stream()
                .map(bookBean -> {
                    BookForm bookForm = new BookForm();
                    BeanUtils.copyProperties(bookBean, bookForm);
                    return bookForm;
                })
                .collect(Collectors.toList());
    }

    public BookForm findOne(Integer id) {
        // findByIdはOptional<BookBean>を返す
        java.util.Optional<BookBean> opt = bookRepository.findById(id);
        BookForm bookForm = new BookForm();
        // Optionalの値が存在する場合、プロパティをコピーする
        opt.ifPresent(bookBean -> BeanUtils.copyProperties(bookBean, bookForm));
        return bookForm;
    }
}
